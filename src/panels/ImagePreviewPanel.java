package panels;

import main.MainFrame;
import zoom.ZoomValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class ImagePreviewPanel extends JPanel {
    private static final long serialVersionUID = 1L;

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
            if (isMouseOverImage) {
                newImage = createFocusedPreviewImage();
            } else if (mainFrame.getZoomValue() == ZoomValue.FIT_TO_SCREEN) {
                newImage = createBlankPreviewImage();
            } else {
                newImage = createScaledPreviewImage();
            }
            updatePreviewPanel(newImage);
        };
        timer = new Timer(50, actionListener);
    }

    private BufferedImage createBlankPreviewImage() {
        BufferedImage blankPreviewImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = blankPreviewImage.createGraphics();
        g.setColor(mainFrame.getBackgroundColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
        return blankPreviewImage;
    }

    private BufferedImage createScaledPreviewImage() {
        BufferedImage storedImage = mainFrame.getMainStoredImage();
        BufferedImage scaledPreviewImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledPreviewImage.createGraphics();
        g.setColor(mainFrame.getBackgroundColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(storedImage, 0, 0, scaledPreviewImage.getWidth(), scaledPreviewImage.getHeight(), null);
        g.dispose();
        return scaledPreviewImage;
    }

    private BufferedImage createFocusedPreviewImage() {
        BufferedImage focusedPreviewImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        ImagePanel imagePanel = mainFrame.getImagePanel();
        double[] scaledWidthAndHeight = imagePanel.getScaledWidthAndHeightRatios();
        double scaledWidth = scaledWidthAndHeight[0];
        double scaledHeight = scaledWidthAndHeight[1];

        int previewX = 0;//(int) (imagePanel.getMostRecentMouseX() / scaledWidth);
        int previewY = 0;//(int) (imagePanel.getMostRecentMouseY() / scaledHeight);

        previewX -= getWidth() / 2;
        previewY -= getHeight() / 2;

        Dimension temp = adjustPreviewCoordinates(previewX, previewY, getWidth(), getHeight());
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

    public void updatePreviewPanel(BufferedImage newPreviewImage) {
        previewImage = newPreviewImage;
        repaint();
    }

    private Dimension adjustPreviewCoordinates(int previewX, int previewY, int previewWidth, int previewHeight) {
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

    public BufferedImage getPreviewImage() {
        return previewImage;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setMouseOverImage(boolean isMouseOverImage) {
        this.isMouseOverImage = isMouseOverImage;
    }
}
