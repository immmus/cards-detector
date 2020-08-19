package org.detect;

import org.detect.diapasons.CardRankDiapasons;
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

import static org.detect.ConsoleHelper.*;
import static org.detect.ImageExtractor.getBottom;
import static org.detect.ImageExtractor.getTop;
import static org.detect.MatrixUtil.calculateMatrixValue;

final class CardDetector {
    private static final Logger log = LoggerFactory.getLogger(CardDetector.class);

    private final List<BufferedImage> cards;
    private final static int CARD_WIDTH = 56;
    private final static int CARD_HEIGHT = 78;
    private final static int CARD_DELIMITER = 16;

    public static CardDetector of(Path path) {
        System.out.print(path.getFileName() + " - ");
        try (var is = Files.newInputStream(path, StandardOpenOption.READ)) {
            final BufferedImage image = ImageIO.read(is);
            return new CardDetector(image);
        } catch (IOException e) {
            log.error("IO error", e);
        }
        return null;
    }

    public CardDetector(BufferedImage image) {
        final var detectedArea = image.getSubimage(146, 590, 346, CARD_HEIGHT);
        final var cards = new ArrayList<BufferedImage>();

        for (int x = 0; x < detectedArea.getWidth(); x += CARD_WIDTH + CARD_DELIMITER) {
            cards.add(detectedArea.getSubimage(x, 0, CARD_WIDTH, CARD_HEIGHT));
        }

        this.cards = Collections.unmodifiableList(cards);
    }

    public void getCards() {
        getCards(false);
    }

    public void getCards(boolean isPrintAdditionalInfo) {
        for (BufferedImage card : cards) {
            BufferedImage text = card.getSubimage(0, 0, 32, 26);
            BufferedImage suit = card.getSubimage(21, 43, 33, CARD_HEIGHT - 43);

            detectText(text)
                    .map(CardRankDiapasons::getShortDescription)
                    .ifPresent(System.out::print);

            if (isPrintAdditionalInfo) {
                printValues(text);
            }

            detectSuits(suit)
                    .map(SuitDiapasons::getShortDescription)
                    .ifPresent(System.out::print);

            if (isPrintAdditionalInfo) {
                printValues(suit);
            }
        }
        System.out.println();
    }

    private Optional<CardRankDiapasons> detectText(BufferedImage img) {
        final BufferedImage top = getTop(img);
        final BufferedImage bottom = getBottom(img);
        final double topSum = calculateMatrixValue(top);
        final double bottomSum = calculateMatrixValue(bottom);
        return Arrays.stream(CardRankDiapasons.values())
                .filter(p -> p.test(topSum, bottomSum))
                .findAny();
    }

    private Optional<SuitDiapasons> detectSuits(BufferedImage img) {
        final double val = calculateMatrixValue(img);
        return Arrays.stream(SuitDiapasons.values())
                .filter(p -> p.test(val))
                .findAny();
    }
}