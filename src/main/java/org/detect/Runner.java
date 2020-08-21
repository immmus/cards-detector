package org.detect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Runner {
    private static final Logger log = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = null;
            while (!"q".equals(line)) {
                System.out.println("enter path or q for stop");
                line = reader.readLine();
                if (!line.isBlank() && Files.exists(Path.of(line))) {
                    final long start = System.currentTimeMillis();
                    Files.walk(Path.of(line))
                            .filter(Files::isRegularFile)
                            .map(CardDetector::of)
                            .filter(Objects::nonNull)
                            .forEach(CardDetector::getCards);
                    log.info("Time of processing file / files - {} mc", System.currentTimeMillis() - start);
                } else {
                    if (!"q".equals(line)) {
                        log.error(line + " is not exist.");
                    }
                }
            }
        }
    }
}