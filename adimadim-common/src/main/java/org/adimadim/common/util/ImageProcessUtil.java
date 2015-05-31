/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.common.util;

import com.mortennobel.imagescaling.MultiStepRescaleOp;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author adem sengul
 */
public class ImageProcessUtil {

    public static BufferedImage loadImage(String path) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        return bufferedImage;
    }

    public static BufferedImage resizeByHeight(BufferedImage bufferedImage, int newHeight) throws Exception {
        float scale = (float) newHeight / bufferedImage.getHeight();
        int newWidth = (int) (scale * bufferedImage.getWidth());
        return scale(bufferedImage, newWidth, newHeight);
    }

    public static BufferedImage resizeByWidth(BufferedImage bufferedImage, int newWidth) throws Exception {
        float scale = (float) newWidth / bufferedImage.getWidth();
        int newHeight = (int) (bufferedImage.getHeight() * scale);
        return scale(bufferedImage, newWidth, newHeight);
    }

    public static void writeImage(BufferedImage bufferedImage, String fileName, String format) throws Exception {
        ImageIO.write(bufferedImage, format, new File(fileName));
    }

    public static BufferedImage scale(BufferedImage bufferedImage, int width, int height) throws Exception {
        MultiStepRescaleOp multiOp = new MultiStepRescaleOp(width, height);
        BufferedImage bi = multiOp.filter(bufferedImage, null);
        return bi;
    }

    public static BufferedImage crop(BufferedImage bi, int x, int y, int width, int height) {
        return bi.getSubimage(x, y, width, height);
    }

    public static BufferedImage merge(BufferedImage foreground, BufferedImage background) {
        BufferedImage bi = background;
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(foreground, 1, 1, null);
        g2d.dispose();
        return bi;
    }
    

}
