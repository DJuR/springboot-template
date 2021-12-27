package com.jlearn.auth.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片生成工具
 * @author dingjuru
 * @date 2021/12/7
 */
public class ImageUtil {


    private BufferedImage image;
    private  Graphics graphics;

    private int width;
    private int height;
    private int imageType;

    private ImageUtil(int width, int height, int imageType) {
        this.width = width;
        this.height = height;
        this.imageType = imageType;
        image = new BufferedImage(width, height, imageType);
        graphics = image.getGraphics();
    }

    public static ImageUtil getImage(int width, int height, int imageType) {
        return new ImageUtil(width, height, imageType);
    }

    /**
     * 背景色
     * @param color
     * @return
     */
    public ImageUtil backColor(Color color) {
        graphics.setColor(color);
        graphics.fillRect(0, 0, width, height);
        return this;
    }

    /**
     * 杂线
     * @return
     */
    public ImageUtil drawLine(int nums) {

       if(nums > 0) {
           Random r = new Random();
           int x1 = r.nextInt(4), y1 = 0;
           int x2 = width - r.nextInt(4), y2 = 0;

           for (int i = 0; i < nums; i++) {
               graphics.setColor(getRandomColor());
               y1 = r.nextInt(height - r.nextInt(4));
               y2 = r.nextInt(height - r.nextInt(4));
               graphics.drawLine(x1, y1, x2, y2);

           }
       }
        return this;
    }

    public ImageUtil drawPoint(float rate) {

        int area = (int) (rate * width * height);
        Random r = new Random();
        for (int i = 0; i < area; i++) {

            int x = r.nextInt(width);
            int y = r.nextInt(height);
            image.setRGB(x, y, getRandomColor().getRGB());
        }

        return this;
    }

    public ImageUtil drawString(String str) {
        int len = str.length();
        int inc = width / len;
        int x = 0;
        int y = height;
        graphics.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 30));
        for (int i = 0; i < len; i++) {
            graphics.setColor(getRandomColor());
            graphics.drawString(str.charAt(i)+"", x, y);
            x += inc;

        }

        return this;
    }


    /**
     * 随机颜色
     */

    public static Color getRandomColor() {

        return new Color((float) Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random());
    }



    /**
     * 图片输出
     *
     * @return
     */
    public BufferedImage out() {
        graphics.dispose();
        return image;
    }
}
