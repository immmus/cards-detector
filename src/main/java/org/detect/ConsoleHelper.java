package org.detect;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.detect.ImageExtractor.*;
import static org.detect.MatrixUtil.calculateMatrixValue;
import static org.detect.MatrixUtil.createMatrix;

/**
 * К алгоритму не относится. Только для выводя доп. информации.
 * */
public class ConsoleHelper {

    public static String getMatrixString(int[][] matrix) {
        return Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining("", "[", "]")))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static String additionalValue(final BufferedImage img) {
        final BufferedImage up = getTop(img);
        final BufferedImage down = getBottom(img);

        final double upLeft = calculateMatrixValue(getLeft(up));
        final double upRight = calculateMatrixValue(getRight(up));
        final double downLeft = calculateMatrixValue(getLeft(down));
        final double downRight = calculateMatrixValue(getRight(down));

        final var sb = new StringBuilder();
        if(upLeft < 0.8 && upRight < 0.8) {
            final String matrixString = getMatrixString(createMatrix(img));

            final var n = System.lineSeparator();
            sb.append(matrixString).append(n);
            sb.append("Размер матрицы: " ).append(n);
            sb.append("Вся матрица - ").append(calculateMatrixValue(img)).append(n);
            sb.append("Верхняя часть - ").append(calculateMatrixValue(up)).append(n);
            sb.append("Нижняя часть - ").append(calculateMatrixValue(down)).append(n);
            sb.append("Верхняя левая часть - ").append(upLeft).append(n);
            sb.append("Верхняя правая часть - ").append(upRight).append(n);
            sb.append("Нижняя левая часть - ").append(downLeft).append(n);
            sb.append("Нижняя правая часть - ").append(downRight).append(n);
        }
        return sb.toString();
    }
}