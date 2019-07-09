package actions;

import main.MainFrame;
import panels.ImagePanel;
import utils.RectCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class MoveCursorAction extends AbstractAction {
    private MainFrame mainFrame;

    public MoveCursorAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void moveCursor(String direction) {
        ImagePanel imagePanel = mainFrame.getImagePanel();
        RectCursor cursor = imagePanel.getRectCursor();
        BufferedImage storedImage = imagePanel.getImageHandler().getStoredImage();

        cursor.shiftCursor(direction, imagePanel.getMainFrame().getOffsets());
        BufferedImage newImage = getResizedImage(storedImage, cursor);
        cursor.makeXAndYNonNegative();
        imagePanel.getImageHandler().updateImages(newImage);
    }

    private BufferedImage getResizedImage(BufferedImage storedImage, RectCursor cursor) {
        int imageWidth = storedImage.getWidth();
        int imageHeight = storedImage.getHeight();

        // If the cursor would extend outside the left edge of the image (i.e., x < 0), imageWidth is increased by -x
        // and x is set to 0
        // horizontalAdjustment is then set to -x, shifting the graphics on the image x units right to compensate
        imageWidth = Math.max(imageWidth, imageWidth - cursor.getX());

        // If the cursor would extend outside the right edge of the image (i.e., x + width > imageWidth), imageWidth
        // is set to x + width so its right edge is aligned with the cursor's right edge
        imageWidth = Math.max(imageWidth, cursor.getX() + cursor.getWidth());

        // y is treated analogously
        imageHeight = Math.max(imageHeight, Math.max(cursor.getY() + cursor.getHeight(), imageHeight - cursor.getY()));

        int horizontalAdjustment = cursor.getX() > 0 ? 0 : -cursor.getX();
        int verticalAdjustment = cursor.getY() > 0 ? 0 : -cursor.getY();

        BufferedImage newImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(storedImage, horizontalAdjustment, verticalAdjustment, null);
        g.dispose();
        return newImage;
    }
}