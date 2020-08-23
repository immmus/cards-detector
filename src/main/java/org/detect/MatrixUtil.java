package org.detect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MatrixUtil {

    public static int[][] createMatrix(BufferedImage image) {
        int[][] matrix = new int[image.getHeight()][image.getWidth()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                final Color color = new Color(image.getRGB(x, y));
                matrix[y][x] = isCorrectColor(color) ? 1 : 0;
            }
        }
        return matrix;
    }

    public static double calculateMatrixValue(final BufferedImage image) {
        double sum = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final Color color = new Color(image.getRGB(x, y));
                if (isCorrectColor(color)) {
                    sum++;
                }
            }
        }

        double a = image.getHeight() * image.getWidth();
        return BigDecimal.valueOf(sum / a).setScale(4, RoundingMode.HALF_DOWN).doubleValue();
    }

    private static boolean isCorrectColor(final Color c) {
        return c.getRed() != 120 && c.getRed() != 255
                && c.getGreen() != 120 && c.getGreen() != 255
                && c.getBlue() != 120 && c.getBlue() != 255;
    }
}
