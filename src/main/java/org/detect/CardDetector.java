package org.detect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CardDetector {
    static AtomicBoolean isPrintAdditionalInfo = new AtomicBoolean(false);
    private static final Logger log = LoggerFactory.getLogger(Runner.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    public void run() {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            for (String line; !executorService.isTerminated(); ) {
                log.info("enter path or q for stop. For additional info enter 'einfo'");
                line = reader.readLine();
                processing(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void processing(final String line) {
        if ("q".equals(line)) {
            close();
        } else if ("einfo".equals(line)) {
            isPrintAdditionalInfo.set(true);
            log.info("Additional information enable. For disable enter 'dinfo'");
        } else if ("dinfo".equals(line)) {
            isPrintAdditionalInfo.set(false);
            log.info("Additional information disable.");
        } else {
            try {
                detect(line);
            } catch (IOException | InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
                close();
            }
        }
    }

    private void detect(String path) throws IOException, InterruptedException, ExecutionException {
        if (!path.isBlank() && Files.exists(Path.of(path))) {
            final long start = System.currentTimeMillis();
            final List<CardDetectorTask> tasks = Files.walk(Path.of(path))
                    .filter(Files::isRegularFile)
                    .map(CardDetectorTask::new)
                    .collect(Collectors.toList());

            final List<Future<String>> futures = executorService.invokeAll(tasks);
            int filesCount = 0;
            for (Future<String> future : futures) {
                System.out.println(future.get());
                filesCount++;
            }
            log.info("Time of processing {} file / files - {} mc",
                    filesCount, System.currentTimeMillis() - start);
        } else {
            log.error(path + " is not exist.");
        }
    }

    private void close() {
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