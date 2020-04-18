package actions;

import main.MainFrame;
import utils.RectCursor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class TrimHorizontallyAction extends TrimAction {
    TrimHorizontallyAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int offsetAdjustment = getOffsetAdjustment();
        trimImageHorizontally(offsetAdjustment);
    }

    public void trimImageHorizontally(int offsetAdjustment) {
        BufferedImage storedImage = mainFrame.getImagePanel().getImageHandler().getStoredImage();
        RectCursor rectCursor = mainFrame.getImagePanel().getRectCursor();

        // If the stored image's height is smaller than the cursor's height plus the offsets, we cannot trim it
        if (storedImage.getHeight() - (rectCursor.getHeight() + 2 * offsetAdjustment) <= 0) {
            return;
        }

        BufferedImage newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight() -
                (rectCursor.getHeight() + offsetAdjustment), BufferedImage.TYPE_INT_ARGB);
                // double check why this is only one offsetAdjustment

        Graphics2D g = newImage.createGraphics();

        // If there is any part of the image above the trimmed area, draw that part to the new image
        int upperBoundOfTrim = rectCursor.getY() - offsetAdjustment;
        if (upperBoundOfTrim > 0) {
            BufferedImage upperSubImage = storedImage.getSubimage(0, 0, storedImage.getWidth(), upperBoundOfTrim);
            g.drawImage(upperSubImage, 0, 0, null);
        }
        // If there is any part of the image below the trimmed area, draw that part to the new image
        int lowerBoundOfTrim = rectCursor.getY() + rectCursor.getHeight() + offsetAdjustment;
        if (lowerBoundOfTrim < storedImage.getHeight()) {
            BufferedImage lowerSubImage = storedImage.getSubimage(0, lowerBoundOfTrim, storedImage.getWidth(),
                    storedImage.getHeight() - lowerBoundOfTrim);
            g.drawImage(lowerSubImage, 0, rectCursor.getY(), null);
            // should it be drawn to Y - offset?
        }
        g.dispose();

        mainFrame.getImagePanel().getImageHandler().updateAndStoreChangedImages(newImage);
        adjustCursorAfterTrimming();
    }
}