package handler;

import panels.ImagePanel;

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
    public void updateImages(BufferedImage newStoredImage) {
        storedImage = newStoredImage;
        imagePanel.updateImages();
    }

    // Update the images and also add the new stored image to the end of the undo stack
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

    // Draw the passed image parameter on the x and y coordinates
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
        g.dispose();
        updateAndStoreChangedImages(newImage);
        // TODO: Focus on cursor here
    }

    public void deleteFromImage() {
        int x = imagePanel.getRectCursor().getX();
        int y = imagePanel.getRectCursor().getY();
        int cropWidth = imagePanel.getMainFrame().getCropWidth();
        int cropHeight = imagePanel.getMainFrame().getCropHeight();

        BufferedImage newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(x, y, cropWidth, cropHeight);
        g.dispose();
        updateAndStoreChangedImages(newImage);
        // TODO: Focus on cursor here
    }

    public void undo() {
        System.out.println(">>>");
        BufferedImage newImage = saveStateList.getPreviousState();
        if (newImage != null) {
            System.out.println("IMAGE");
            updateImages(newImage);
        }
    }

    public void redo() {
        BufferedImage newImage = saveStateList.getNextState();
        if (newImage != null) {
            updateImages(newImage);
        }
    }

    public BufferedImage getStoredImage() {
        return storedImage;
    }
}