package actions;

import main.MainFrame;
import utils.RectCursor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class TrimVerticallyAction extends TrimAction {
    TrimVerticallyAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int offsetAdjustment = getOffsetAdjustment();
        trimImageVertically(offsetAdjustment);
    }

    public void trimImageVertically(int offsetAdjustment) {
        BufferedImage storedImage = mainFrame.getImagePanel().getImageHandler().getStoredImage();
        RectCursor rectCursor = mainFrame.getImagePanel().getRectCursor();

        // If the stored image's width is smaller than the cursor's width plus the offsets, we cannot trim it
        if (storedImage.getWidth() - (rectCursor.getWidth() + 2 * offsetAdjustment) <= 0) {
            return;
        }

        // subtract offsetAdjustment once from the width of storedImage. If offsetAdjustment = 0, this has no effect
        // otherwise,
        BufferedImage newImage = new BufferedImage(storedImage.getWidth() - (rectCursor.getWidth() - offsetAdjustment),
                storedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = newImage.createGraphics();

        // If there is any part of the image to the left of the trimmed area, draw that part to the new image
        int leftBoundOfTrim = rectCursor.getX() - offsetAdjustment;
        if (leftBoundOfTrim > 0) {
            BufferedImage leftSubImage = storedImage.getSubimage(0, 0, leftBoundOfTrim, storedImage.getHeight());
            g.drawImage(leftSubImage, 0, 0, null);
        }

        // If there is any part of the image to the right of the trimmed area, draw that part to the new image
        int rightBoundOfTrim = rectCursor.getX() + rectCursor.getWidth() + offsetAdjustment;
        if (rightBoundOfTrim < storedImage.getWidth()) {
            BufferedImage rightSubImage = storedImage.getSubimage(rightBoundOfTrim, 0,
                    storedImage.getWidth() - rightBoundOfTrim, storedImage.getHeight());
            g.drawImage(rightSubImage, rectCursor.getX(), 0, null);
        }
        g.dispose();

        mainFrame.getImagePanel().getImageHandler().updateAndStoreChangedImages(newImage);
        adjustCursorAfterTrimming();
    }
}