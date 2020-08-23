package org.detect;

import org.detect.diapasons.CardRankDiapasons;
import org.detect.diapasons.Diapasons;
import org.detect.diapasons.SuitDiapasons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.Callable;

import static org.detect.CardDetector.isPrintAdditionalInfo;
import static org.detect.ConsoleHelper.additionalValue;
import static org.detect.ImageExtractor.getBottom;
import static org.detect.ImageExtractor.getTop;
import static org.detect.MatrixUtil.calculateMatrixValue;

final class CardDetectorTask implements Callable<String> {
    private static final Logger log = LoggerFactory.getLogger(CardDetectorTask.class);

    private final Path imagePath;
    private final static int CARD_WIDTH = 56;
    private final static int CARD_HEIGHT = 78;
    private final static int CARD_DELIMITER = 16;


    public CardDetectorTask(Path path) {
        this.imagePath = path;
    }

    @Override
    public String call() {
        try (var is = Files.newInputStream(imagePath, StandardOpenOption.READ)) {
            final BufferedImage image = ImageIO.read(is);
            final var detectedArea = image.getSubimage(146, 590, 346, CARD_HEIGHT);
            final var cards = new ArrayList<BufferedImage>();

            for (int x = 0; x < detectedArea.getWidth(); x += CARD_WIDTH + CARD_DELIMITER) {
                cards.add(detectedArea.getSubimage(x, 0, CARD_WIDTH, CARD_HEIGHT));
            }

            return getCards(Collections.unmodifiableList(cards));
        } catch (IOException e) {
            log.error("IO error", e);
            return "Not detected due to IO error.";
        }
    }

    public String getCards(List<BufferedImage> cards) {
        final var result = new StringBuilder(imagePath.getFileName() + " - ");
        for (BufferedImage card : cards) {
            BufferedImage text = card.getSubimage(0, 0, 32, 26);
            BufferedImage suit = card.getSubimage(21, 43, 33, CARD_HEIGHT - 43);

            final var textDetect = detectText(text);
            final var n = System.lineSeparator();
            if (isPrintAdditionalInfo.get()) {
                result.append(textDetect.map(Diapasons::getDescription).orElse(""));
                result.append(n).append(additionalValue(text));
            } else {
                result.append(textDetect.map(Diapasons::getShortDescription).orElse(""));
            }

            final var suitDetect = detectSuits(suit);
            if (isPrintAdditionalInfo.get()) {
                result.append(suitDetect.map(Diapasons::getDescription).orElse(""));
                result.append(n).append(additionalValue(suit));
            } else {
                result.append(suitDetect.map(Diapasons::getShortDescription).orElse(""));
            }
        }
        return result.toString();
    }

    private Optional<Diapasons> detectText(BufferedImage img) {
        final BufferedImage top = getTop(img);
        final BufferedImage bottom = getBottom(img);
        final double topSum = calculateMatrixValue(top);
        final double bottomSum = calculateMatrixValue(bottom);
        return Arrays.stream(CardRankDiapasons.values())
                .filter(p -> p.test(topSum, bottomSum))
                .map(d -> (Diapasons) d)
                .findAny();
    }

    private Optional<Diapasons> detectSuits(BufferedImage img) {
        final double val = calculateMatrixValue(img);
        return Arrays.stream(SuitDiapasons.values())
                .filter(p -> p.test(val))
                .map(d ->  (Diapasons) d)
                .findAny();
    }
}