package utils;

import org.w3c.dom.css.Rect;
import panels.ImagePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class RectCursor implements Serializable {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean displayCursor = true;
    private transient ImagePanel imagePanel;

    public RectCursor(ImagePanel imagePanel, int x, int y, int width, int height) {
        displayCursor = true;
        this.imagePanel = imagePanel;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public RectCursor(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;

        this.width = 400;
        this.height = 400;
        this.x = (imagePanel.getWidth() - width) / 2;
        this.y = (imagePanel.getHeight() - height) / 2;
    }

    public void drawCursorOnImage(BufferedImage image) {
//        Graphics2D g = image.createGraphics();
//        g.setColor(color);
//        g.drawRect(x, y, width, height);
//        g.dispose();
        if (!displayCursor) {
            return;
        }
        drawContrastingCursorOnImage(image);
    }

    // TODO: Fix out of bounds error when extending screen down or right
    // TODO: Fix the error with values being mis-rounded depending on the zoom value of the window
    // TODO: Fix the cursor becoming misaligned when scrolling upwards
    private void drawContrastingCursorOnImage(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        for (int currentX = x; currentX <= x + width; currentX++) {
            drawContrastingPixelsAtPosition(image, g, currentX, y);
            drawContrastingPixelsAtPosition(image, g, currentX, y + height);
        }
        for (int currentY = y + 1; currentY <= y + height - 1; currentY++) {
            drawContrastingPixelsAtPosition(image, g, x, currentY);
            drawContrastingPixelsAtPosition(image, g, x + width, currentY);
        }
    }

    private void drawContrastingPixelsAtPosition(BufferedImage image, Graphics2D g, int currentX, int currentY) {
        try {
            Color backgroundPixelColor = new Color(image.getRGB(currentX, currentY));
            g.setColor(getContrastingColor(backgroundPixelColor));
            g.drawLine(currentX, currentY, currentX, currentY);
        }
        catch (IndexOutOfBoundsException ignore) { }
    }

    // Returns black if the main color is light, or white if the main color is dark
    private Color getContrastingColor(Color mainColor) {
        double luma = 0.299 * mainColor.getRed() + 0.587 * mainColor.getGreen() + 0.114 * mainColor.getBlue();
        return (luma >= 128) ? Color.BLACK : Color.WHITE;
    }

    public RectCursor makeScaledCursor(double scaledWidthFactor, double scaledHeightFactor) {
        int scaledX = (int) (x * scaledWidthFactor);
        int scaledY = (int) (y * scaledHeightFactor);
        int scaledWidth = (int) (width * scaledWidthFactor);
        int scaledHeight = (int) (height * scaledHeightFactor);
        RectCursor scaledCursor = new RectCursor(imagePanel, scaledX, scaledY, scaledWidth, scaledHeight);
        scaledCursor.displayCursor = displayCursor;
        return scaledCursor;
    }

    public void shiftCursor(String direction, int offsets) {
        displayCursor = true;
        switch (direction) {
            case "up":
                y -= (height + offsets);
                break;
            case "down":
                y += (height + offsets);
                break;
            case "left":
                x -= (width + offsets);
                break;
            case "right":
                x += (width + offsets);
                break;
        }
//        shouldFollowCursor = true;
    }

    public void makeXAndYNonNegative() {
        x = Math.max(0, x);
        y = Math.max(0, y);
    }

    public void toggleDisplayCursor() {
        displayCursor = !displayCursor;
        imagePanel.updateImages();
    }

    public RectCursor getClone() {
        return new RectCursor(imagePanel, x, y, width, height);
    }

    public static boolean haveEqualMeasurements(RectCursor cursorA, RectCursor cursorB) {
        return cursorA.getX() == cursorB.getX() &&
                cursorA.getY() == cursorB.getY() &&
                cursorA.getWidth() == cursorB.getWidth() &&
                cursorA.getHeight() == cursorB.getHeight();
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

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public void setImagePanel(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }
}