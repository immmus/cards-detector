package org.detect;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MatrixUtil {

    public static int[][] createMatrix(BufferedImage image) {
        int[][] matrix = new int[image.getHeight()][image.getWidth()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                matrix[y][x] = image.getRGB(x, y) != -1 ? 1 : 0;
            }
        }
        return matrix;
    }

    public static double calculateMatrixValue(BufferedImage image) {
        double sum = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                sum += image.getRGB(x, y) != -1 ? 1 : 0;
            }
        }

        double a = image.getHeight() * image.getWidth();
        return BigDecimal.valueOf(sum / a).setScale(4, RoundingMode.HALF_DOWN).doubleValue();
    }
}
