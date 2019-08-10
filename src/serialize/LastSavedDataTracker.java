package serialize;

import main.MainFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LastSavedDataTracker {
    private MainFrame mainFrame;
    private BufferedImage lastSavedStoredImage;
    private Color lastSavedBackgroundColor;
    private String lastSavedImageFileName;

    public LastSavedDataTracker(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        lastSavedStoredImage = mainFrame.getMainStoredImage();
        lastSavedBackgroundColor = mainFrame.getBackgroundColor();
        lastSavedImageFileName = mainFrame.getSavePanel().getImageFileNameText();
    }

    public boolean areUnsavedChanges() {
        BufferedImage currentStoredImage = mainFrame.getMainStoredImage();
        Color currentBackgroundColor = mainFrame.getBackgroundColor();
        String currentImageFileName = mainFrame.getSavePanel().getImageFileNameText();

        if (!currentImageFileName.equals(lastSavedImageFileName) ||
                currentBackgroundColor != lastSavedBackgroundColor) {
            return true;
        }

        return !areImagesEqual(currentStoredImage, lastSavedStoredImage);
    }

    private boolean areImagesEqual(BufferedImage imageA, BufferedImage imageB) {
        if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight()) {
            return false;
        }

        int width  = imageA.getWidth();
        int height = imageA.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (imageA.getRGB(x, y) != imageB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    public BufferedImage getLastSavedStoredImage() {
        return lastSavedStoredImage;
    }

    public Color getLastSavedBackgroundColor() {
        return lastSavedBackgroundColor;
    }

    public String getLastSavedImageFileName() {
        return lastSavedImageFileName;
    }
}
