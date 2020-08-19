package org.detect;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static org.detect.ImageExtractor.*;
import static org.detect.MatrixUtil.calculateMatrixValue;
import static org.detect.MatrixUtil.createMatrix;

/**
 * К алгоритму не относится. Только для выводя доп. информации.
 * */
public class ConsoleHelper {

    public static void printMatrix(int[][] matrix) {
        Arrays.stream(matrix)
                .forEach(
                        (row) -> {
                            System.out.print("[");
                            Arrays.stream(row).forEach(System.out::print);
                            System.out.println("]");
                        });
        System.out.println();
        System.out.println();
    }

    public static void printValues(BufferedImage img) {
        final BufferedImage up = getTop(img);
        final BufferedImage down = getBottom(img);

        final double upLeft = calculateMatrixValue(getLeft(up));
        final double upRight = calculateMatrixValue(getRight(up));
        final double downLeft = calculateMatrixValue(getLeft(down));
        final double downRight = calculateMatrixValue(getRight(down));

        if(upLeft < 0.8 && upRight < 0.8) {
            printMatrix(createMatrix(img));

            final var n = System.lineSeparator();
            System.out.println("Размер матрицы: \n" +
                    "Вся матрица - " + calculateMatrixValue(img) + n + n +
                    "Верхняя часть - " + calculateMatrixValue(up) + n +
                    "Нижняя часть - " + calculateMatrixValue(down) + n + n +
                    "Верхняя левая часть - " + upLeft + n +
                    "Верхняя правая часть - " + upRight + n +
                    "Нижняя левая часть - " + downLeft + n +
                    "Нижняя правая часть - " + downRight + n
            );
        }
    }
}