package handler;

import panels.ImagePanel;
import utils.RectCursor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHandler {
    private ImagePanel imagePanel;
    private BufferedImage storedImage;
    private SaveStateList saveStateList;

    public ImageHandler(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;

        saveStateList = new SaveStateList();
        storedImage = new BufferedImage(imagePanel.getWidth(), imagePanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        saveStateList.add(storedImage);
    }

    // Update the stored image, displayed image, and scaled displayed image
    // To be used with 'minor' technical changes that do not warrant an undo, such as moving the cursor's position
    public void updateImages(BufferedImage newStoredImage) {
        storedImage = newStoredImage;
        imagePanel.updateImages();
    }

    // Update the images and also add the new stored image to the end of the undo stack
    // To be used with 'major' changes that do warrant an undo, such as adding or deleting a section of the stored
    // image
    public void updateAndStoreChangedImages(BufferedImage newStoredImage) {
        updateImages(newStoredImage);
        saveStateList.add(newStoredImage);
    }

    // Create a new stored image with a greater size than the current stored image, with the current stored image
    // copied into its center
    private void increaseImageSize(int widthOfNewImage, int heightOfNewImage) {
        int updatedWidth = storedImage.getWidth();
        int updatedHeight = storedImage.getHeight();

        updatedWidth = (updatedWidth >= widthOfNewImage) ? updatedWidth : widthOfNewImage;
        updatedHeight = (updatedHeight >= heightOfNewImage) ? updatedHeight : heightOfNewImage;

        BufferedImage finalImage = new BufferedImage(updatedWidth, updatedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(storedImage,
                (updatedWidth - storedImage.getWidth()) / 2,
                (updatedHeight - storedImage.getHeight()) / 2,
                null);
        g.dispose();
        updateImages(finalImage);
    }

    // Draw the passed image parameter on the rectCursor's x and y coordinates
    public void pasteToImage(BufferedImage pasteImage) {
        if (pasteImage.getWidth() > storedImage.getWidth() || pasteImage.getHeight() > storedImage.getHeight()) {
            increaseImageSize(pasteImage.getWidth(), pasteImage.getHeight());
        }

        BufferedImage newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(storedImage, 0, 0, null);
        int x = imagePanel.getRectCursor().getX();
        int y = imagePanel.getRectCursor().getY();
        g.drawImage(pasteImage, x, y, null);
        System.out.println(">>" + pasteImage.getWidth() + " " + pasteImage.getHeight());
        g.dispose();
        updateAndStoreChangedImages(newImage);
        // TODO: Focus on cursor here
    }

    // Remove the image data on the rectCursor's x and y coordinates (removing color and opacity)
    public void deleteFromImage() {
        RectCursor cursor = imagePanel.getRectCursor();

        BufferedImage newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(storedImage, 0, 0, null);
        g.setComposite(AlphaComposite.Src);
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(cursor.getX(), cursor.getY(), cursor.getWidth(), cursor.getHeight());
        g.dispose();
        updateAndStoreChangedImages(newImage);
        // TODO: Focus on cursor here
    }

    // Update the stored image to the previous image in the save state list
    // Does nothing if there is no previous image
    public void undo() {
        BufferedImage newImage = saveStateList.getPreviousState();
        if (newImage != null) {
            updateImages(newImage);
        }
        // TODO: Adjust cursor on undo
    }

    // Update the stored image to the next image in the save state list
    // Does nothing if there is no next image
    public void redo() {
        BufferedImage newImage = saveStateList.getNextState();
        if (newImage != null) {
            updateImages(newImage);
        }
        // TODO: Adjust cursor on redo
    }

    public BufferedImage getStoredImage() {
        return storedImage;
    }

    public SaveStateList getSaveStateList() {
        return saveStateList;
    }

    public void setSaveStateList(SaveStateList saveStateList) {
        this.saveStateList = saveStateList;
    }
}
