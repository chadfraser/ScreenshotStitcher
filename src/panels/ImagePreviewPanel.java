package panels;

import handler.ImageState;
import main.MainFrame;
import zoom.ZoomValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class ImagePreviewPanel extends JPanel {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;
    private boolean isMouseOverImage = false;

    private BufferedImage previewImage;
    private MainFrame mainFrame;
    private Timer timer;

    public ImagePreviewPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
        setFocusable(true);

        previewImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        ActionListener actionListener = e -> {
            BufferedImage newImage;
            BufferedImage previewUndoRedoImage = getScaledPreviewUndoRedoImage();
            if (isMouseOverImage) {
                newImage = createFocusedPreviewImage();
            } else if (previewUndoRedoImage != null) {
                newImage = previewUndoRedoImage;
            } else if (mainFrame.getZoomValue() == ZoomValue.FIT_TO_SCREEN) {
                newImage = createBlankPreviewImage();
            } else {
                newImage = createScaledPreviewImage(mainFrame.getMainStoredImage());
            }
            updatePreviewPanel(newImage);
        };
        timer = new Timer(50, actionListener);
    }

    // Return a scaled version of the stored image that would occur if the undo button were pressed (assuming the
    // preview undo option is set) or of the image that would occur if the redo button were pressed (assuming the
    // preview redo option is set)
    // Return null if the UNDO tab is not selected in the main frame
    private BufferedImage getScaledPreviewUndoRedoImage() {
        if (!"UNDO".equals(mainFrame.getSelectedTabTitle())) {
            return null;
        }

        ImageState previous = mainFrame.getImagePanel().getImageHandler().getSaveStateList().pollPreviousState();
        if (mainFrame.getUndoButtonPanel().getPreviewUndoOption() && previous != null) {
            return createScaledPreviewImage(previous.getImage());
        }

        ImageState next = mainFrame.getImagePanel().getImageHandler().getSaveStateList().pollNextState();
        if (mainFrame.getUndoButtonPanel().getPreviewRedoOption() && next != null) {
            return createScaledPreviewImage(next.getImage());
        }
        return null;
    }

    private BufferedImage createBlankPreviewImage() {
        BufferedImage blankPreviewImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        return createScaledPreviewImage(blankPreviewImage);
    }

    private BufferedImage createScaledPreviewImage(BufferedImage imageToScale) {
        BufferedImage scaledPreviewImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledPreviewImage.createGraphics();
        g.setColor(mainFrame.getBackgroundColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(imageToScale, 0, 0, scaledPreviewImage.getWidth(), scaledPreviewImage.getHeight(), null);
        g.dispose();
        return scaledPreviewImage;
    }

    private BufferedImage createFocusedPreviewImage() {
        BufferedImage focusedPreviewImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        ImagePanel imagePanel = mainFrame.getImagePanel();
        double[] scaledWidthAndHeight = imagePanel.getScaledWidthAndHeightRatios();
        double scaledWidth = scaledWidthAndHeight[0];
        double scaledHeight = scaledWidthAndHeight[1];

        int previewX = (int) (imagePanel.getMostRecentMouseX() / scaledWidth);
        int previewY = (int) (imagePanel.getMostRecentMouseY() / scaledHeight);

        previewX -= getWidth() / 2;
        previewY -= getHeight() / 2;

        Dimension temp = adjustPreviewCoordinatesToFitImageBounds(previewX, previewY, getWidth(), getHeight());
        previewX = temp.width;
        previewY = temp.height;

        try {
            BufferedImage croppedImage = mainFrame.getMainStoredImage().getSubimage(previewX, previewY,
                    getWidth(), getHeight());
            Graphics2D g = focusedPreviewImage.createGraphics();
            g.setColor(mainFrame.getBackgroundColor());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(croppedImage, 0, 0, getWidth(), getHeight(), null);
            g.dispose();
        } catch (RasterFormatException except) {
            focusedPreviewImage = adjustForUndersizedStoredImage(previewX, previewY);
        }
        return focusedPreviewImage;
    }

    private void updatePreviewPanel(BufferedImage newPreviewImage) {
        previewImage = newPreviewImage;
        repaint();
    }

    // If any edge of the preview image is outside of the bounds of the stored image, shift the preview image's
    // coordinates to compensate for that
    private Dimension adjustPreviewCoordinatesToFitImageBounds(int previewX, int previewY, int previewWidth,
                                                               int previewHeight) {
        BufferedImage storedImage = mainFrame.getMainStoredImage();
        if (previewX < 0) {
            previewX = 0;
        } else if (previewX + previewWidth > storedImage.getWidth()) {
            previewX = storedImage.getWidth() - previewWidth;
        }

        if (previewY < 0) {
            previewY = 0;
        } else if (previewY + previewHeight > storedImage.getHeight()) {
            previewY = storedImage.getHeight() - previewHeight;
        }

        return new Dimension(previewX, previewY);
    }

    // If either dimension of the stored image is smaller than the dimensions of the preview image, shift the
    // coordinates of where the stored image is drawn to the preview image so it is centered
    private BufferedImage adjustForUndersizedStoredImage(int previewX, int previewY) {
        BufferedImage storedImage = mainFrame.getMainStoredImage();
        int xToDraw = 0;
        int yToDraw = 0;
        int width = getWidth();
        int height = getHeight();

        if (width > storedImage.getWidth()) {
            previewX = 0;
            xToDraw = (width - storedImage.getWidth()) / 2;
            width = storedImage.getWidth();
        }

        if (height > storedImage.getHeight()) {
            previewY = 0;
            yToDraw = (height - storedImage.getHeight()) / 2;
            height = storedImage.getHeight();
        }
        BufferedImage croppedImage = storedImage.getSubimage(previewX, previewY, width, height);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(mainFrame.getBackgroundColor());
        g.fillRect(0, 0, width, height);
        g.drawImage(croppedImage, xToDraw, yToDraw, null);

        return newImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(previewImage, 0, 0, null);
    }

    @Override
    public final Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        Dimension prefSize;
        Component component = getParent();

        if (component == null) {
            prefSize = new Dimension((int) dimension.getWidth(), (int) dimension.getHeight());
        } else if (component.getWidth() > dimension.getWidth() && component.getHeight() > dimension.getHeight()) {
            prefSize = component.getSize();
        } else {
            prefSize = dimension;
        }
        int width = (int) prefSize.getWidth();
        int height = (int) prefSize.getHeight();
        int sideLength = (width > height ? height : width);
        return new Dimension(sideLength, sideLength);
    }

    @Override
    public final Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Timer getTimer() {
        return timer;
    }

    void setMouseOverImage(boolean isMouseOverImage) {
        this.isMouseOverImage = isMouseOverImage;
    }
}
