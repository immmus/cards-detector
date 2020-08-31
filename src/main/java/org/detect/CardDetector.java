package org.detect;

import org.detect.commands.CommandsChecker;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.detect.ConsoleHelper.isPrintAdditionalInfo;
import static org.detect.commands.CommandsBuilder.command;

public class CardDetector {
    private static final Logger log = LoggerFactory.getLogger(Runner.class);
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService = Executors.newFixedThreadPool(CPU_COUNT);
    private final CommandsChecker commandsChecker;

    public CardDetector() {
        this.commandsChecker = new CommandsChecker()
                .commands(() -> {
                    command("detect", this::detect);
                    command("stop", a -> close());
                    //TODO подумать над реализацией опций к командам
                    command("einfo", a -> isPrintAdditionalInfo.set(!isPrintAdditionalInfo.get()));
                });
    }

    public CardDetector(CommandsChecker commandsChecker) {
        this.commandsChecker = commandsChecker;
    }

    public void run() {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            for (String line; !executorService.isTerminated(); ) {
                log.info("enter 'detect + path' or stop for terminate. For additional info enter 'einfo'");
                line = reader.readLine();
                commandsChecker.checkCommand(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void detect(@NotNull final String path) {
        if (!path.isBlank() && Files.exists(Path.of(path))) {
            try {
                final long start = System.currentTimeMillis();
                final List<CardDetectorTask> tasks = Files.walk(Path.of(path))
                        .filter(Files::isRegularFile)
                        .map(CardDetectorTask::new)
                        .collect(Collectors.toList());

                final List<Future<String>> futures = executorService.invokeAll(tasks);
                for (Future<String> future : futures) {
                    System.out.println(future.get());
                }
                log.info("Time of processing {} file / files - {} mc",
                        futures.size(), System.currentTimeMillis() - start);
            } catch (IOException | InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
                close();
            }
        } else {
            log.error(path + " path is not exist.");
        }
    }

    public void close() {
        executorService.shutdown();
        try {
            if (executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            executorService.shutdownNow();
        }
    }
}
