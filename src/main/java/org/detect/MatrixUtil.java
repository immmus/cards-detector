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
                matrix[y][x] = isCorrectColor(color.getRed(), color.getGreen(), color.getBlue())
                        ? 1 : 0;
            }
        }
        return matrix;
    }

    public static double calculateMatrixValue(BufferedImage image) {
        double sum = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                final Color color = new Color(image.getRGB(x, y));
                if (isCorrectColor(color.getRed(), color.getGreen(), color.getBlue())) {
                    sum++;
                }
            }
        }

        double a = image.getHeight() * image.getWidth();
        return BigDecimal.valueOf(sum / a).setScale(4, RoundingMode.HALF_DOWN).doubleValue();
    }

    private static boolean isCorrectColor(int red, int green, int blue) {
        return red != 120 && red != 255
                && green != 120 && green != 255
                && blue != 120 && blue != 255;
    }
}
