package handler;

import panels.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

public class ImageHandler {
    private static final int RECENT_CHANGE_LIST_MAX_SIZE = 10;
    private ImagePanel imagePanel;

    private BufferedImage storedImage;
    private int currentImageIndex;

    private List<BufferedImage> listOfRecentImageChanges;

    public ImageHandler(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
        this.currentImageIndex = 0;

        listOfRecentImageChanges = new ArrayList<>() {
//            @Override
//            public boolean add(Object e) {
//                if (this.size() >= RECENT_CHANGES_MAX_SIZE) {
//                    this.remove(0);
//                }
//                return super.add(e);
//            }
//
//            public boolean addAndAdjust(Object e, int currentImageIndex) {
//                ArrayList<Object> newList = (ArrayList<Object>) this.subList(0, currentImageIndex + 1);
//                this = newList;
//                if (this.size() >= RECENT_CHANGES_MAX_SIZE) {
//                    this.remove(0);
//                }
//                return super.add(e);
//            }
        };
        storedImage = new BufferedImage(imagePanel.getWidth(), imagePanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        addToArrayList(storedImage);
    }

    private void addToArrayList(BufferedImage image) {
        // First, delete all elements from the array list past the currentImageIndex
        // This way, we prevent 'redos' that revert the state of the image
        try {
            listOfRecentImageChanges = listOfRecentImageChanges.subList(0, currentImageIndex);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        // If the array list has more elements than MAX_SIZE, remove the first element from the array list to keep
        // it under the size constraint
        // This ensures that the past (n <= 10) 'undos' are always saved
        if (listOfRecentImageChanges.size() >= RECENT_CHANGE_LIST_MAX_SIZE) {
            listOfRecentImageChanges.remove(0);
            currentImageIndex--;
        }
        listOfRecentImageChanges.add(image);
        currentImageIndex++;
    }

    // Update the stored image, displayed image, and scaled displayed image
    public void updateImages(BufferedImage newStoredImage) {
        storedImage = newStoredImage;
        imagePanel.updateImages();
    }

    // Update the images and also add the new stored image to the end of the undo stack
    public void updateAndStoreChangedImages(BufferedImage newStoredImage) {
        updateImages(newStoredImage);
        addToArrayList(newStoredImage);
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
        int x = imagePanel.getRectCursor().getX();
        int y = imagePanel.getRectCursor().getY();

        if (pasteImage.getWidth() > storedImage.getWidth() || pasteImage.getHeight() > storedImage.getHeight()) {
            increaseImageSize(pasteImage.getWidth(), pasteImage.getHeight());
        }

        BufferedImage newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(storedImage, 0, 0, null);
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
        if (currentImageIndex <= 0 || currentImageIndex > listOfRecentImageChanges.size() - 1) {
            return;
        }
        currentImageIndex--;
        storedImage = getImageFromUndoQueue();
        updateImages(getImageFromUndoQueue());
    }

    public void redo() {
        if (currentImageIndex < 0 || currentImageIndex >= listOfRecentImageChanges.size() - 1) {
            return;
        }
        currentImageIndex++;
        updateImages(getImageFromUndoQueue());
    }

    private BufferedImage getImageFromUndoQueue() {
        return listOfRecentImageChanges.get(currentImageIndex);
    }

    public BufferedImage getStoredImage() {
        return storedImage;
    }
}
