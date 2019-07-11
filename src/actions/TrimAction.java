package actions;

import main.MainFrame;
import utils.RectCursor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public abstract class TrimAction extends AbstractAction {
    MainFrame mainFrame;

    TrimAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    int getOffsetAdjustment() {
        return mainFrame.getTrimPanel().getTrimOffsetsCheckBox().isSelected() ? mainFrame.getOffsets() : 0;
    }

    void adjustCursorAfterTrimming() {
        BufferedImage storedImage = mainFrame.getMainStoredImage();
        RectCursor rectCursor = mainFrame.getImagePanel().getRectCursor();

        if (rectCursor.getWidth() > storedImage.getWidth()) {
            rectCursor.setWidth(storedImage.getWidth());
        }
        if (rectCursor.getHeight() > storedImage.getHeight()) {
            rectCursor.setHeight(storedImage.getHeight());
        }
        if (rectCursor.getX() + rectCursor.getWidth() > storedImage.getWidth()) {
            rectCursor.setX(storedImage.getWidth() - rectCursor.getWidth());
        }
        if (rectCursor.getY() + rectCursor.getHeight() > storedImage.getHeight()) {
            rectCursor.setY(storedImage.getHeight() - rectCursor.getHeight());
        }
    }
}