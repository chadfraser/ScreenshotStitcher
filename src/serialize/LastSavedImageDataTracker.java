package serialize;

import main.MainFrame;
import utils.ImageComparer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LastSavedImageDataTracker {
    private MainFrame mainFrame;
    private BufferedImage lastSavedStoredImage;
    private Color lastSavedBackgroundColor;
    private String lastSavedImageFileName;

    public LastSavedImageDataTracker(MainFrame mainFrame) {
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

        return !ImageComparer.areImagesEqual(currentStoredImage, lastSavedStoredImage);
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
