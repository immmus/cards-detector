package org.detect;

import java.awt.image.BufferedImage;

public class ImageExtractor {

    public static BufferedImage getTop(BufferedImage img) {
        return img.getSubimage(0, 0, img.getWidth(), img.getHeight() / 2);
    }

    public static BufferedImage getBottom(BufferedImage img) {
        return img.getSubimage(0, img.getHeight() / 2, img.getWidth(), img.getHeight() / 2);
    }

    public static BufferedImage getLeft(BufferedImage img) {
        return img.getSubimage(0, 0, img.getWidth() / 2, img.getHeight());
    }

    public static BufferedImage getRight(BufferedImage img) {
        return img.getSubimage(img.getWidth() / 2, 0, img.getWidth() / 2, img.getHeight());
    }
}
