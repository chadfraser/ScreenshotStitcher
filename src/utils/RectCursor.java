package utils;

import panels.ImagePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RectCursor {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private ImagePanel imagePanel;

    public RectCursor(ImagePanel imagePanel, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.BLACK;

        this.imagePanel = imagePanel;
    }

    public void drawCursorOnImage(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setColor(color);
        g.drawRect(x, y, width, height);
        g.dispose();
//        return tempImage;
    }

    // Returns black if the main color is light, or white if the main color is dark
    public Color getContrastingColor(Color mainColor) {
        double luma = 0.299 * mainColor.getRed() + 0.587 * mainColor.getGreen() + 0.114 * mainColor.getBlue();
        return (luma >= 128) ? Color.BLACK : Color.WHITE;
    }

    public RectCursor makeScaledCursor(double scaledWidthFactor, double scaledHeightFactor) {
        int scaledX = (int) (x * scaledWidthFactor);
        int scaledY = (int) (y * scaledHeightFactor);
        int scaledWidth = (int) (width * scaledWidthFactor);
        int scaledHeight = (int) (height * scaledHeightFactor);
        return new RectCursor(imagePanel, scaledX, scaledY, scaledWidth, scaledHeight);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public void setImagePanel(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }
}