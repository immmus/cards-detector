package org.detect.diapasons;

import lombok.Getter;

import java.awt.image.BufferedImage;

import static org.detect.ImageExtractor.*;
import static org.detect.ImageExtractor.getBottom;
import static org.detect.MatrixUtil.calculateMatrixValue;

@Getter
public final class RankAreasSums {
    private final double topL;
    private final double topR;
    private final double bottomL;
    private final double bottomR;

    public RankAreasSums(final BufferedImage img) {
        final BufferedImage top = getTop(img);
        final BufferedImage bottom = getBottom(img);

        this.topL = calculateMatrixValue(getLeft(top));
        this.topR = calculateMatrixValue(getRight(top));
        this.bottomL = calculateMatrixValue(getLeft(bottom));
        this.bottomR = calculateMatrixValue(getRight(bottom));
    }
}
