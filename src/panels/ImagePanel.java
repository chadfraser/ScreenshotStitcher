package panels;

import handler.ImageHandler;
import main.MainFrame;
import utils.RectCursor;
import zoom.ZoomValue;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel implements MouseInputListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private BufferedImage displayedImage;
    private BufferedImage scaledImage;

    private MainFrame mainFrame;
    private ImageHandler imageHandler;
    private RectCursor rectCursor;
//    private boolean shouldFollowCursor;
    private int mostRecentMouseX;
    private int mostRecentMouseY;

    public ImagePanel(MainFrame mainFrame) {
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // TODO: Improve this area
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setSize(new Dimension(WIDTH, HEIGHT));

        setFocusable(true);

        this.mainFrame = mainFrame;
        initializeCursor();
        initializeImages();

        this.imageHandler = new ImageHandler(this);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void initializeCursor() {
//        int cursorWidth = mainFrame.getCropWidth();
//        int cursorHeight = mainFrame.getCropHeight();
//        int cursorX = (WIDTH - cursorWidth) / 2;
//        int cursorY = (HEIGHT - cursorHeight) / 2;
//        this.rectCursor = new RectCursor(this, cursorX, cursorY, cursorWidth, cursorHeight);
//        shouldFollowCursor = false;
        this.rectCursor = new RectCursor(this);
    }

    private void initializeImages() {
        displayedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        scaledImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
    }

    public void updateImages() {
        updateDisplayedImage();
        updateScaledImage();
        revalidate();
        repaint();
    }

    private void updateDisplayedImage() {
        BufferedImage storedImage = imageHandler.getStoredImage();
        BufferedImage tempImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        displayedImage = buildNewImage(tempImage, mainFrame.getBackgroundColor(), rectCursor);
    }

    // TODO: Reexamine
    private void updateScaledImage() {
        double[] scaledWidthAndHeightRatios = getScaledWidthAndHeightRatios();
        double scaledWidthRatio = scaledWidthAndHeightRatios[0];
        double scaledHeightRatio = scaledWidthAndHeightRatios[1];
        int imageWidth = mainFrame.getScrollPanelWidth();
        int imageHeight = mainFrame.getScrollPanelHeight();

        if (mainFrame.getZoomValue() != ZoomValue.FIT_TO_SCREEN) {
            imageWidth = (int) (imageHandler.getStoredImage().getWidth() * scaledWidthRatio);
            imageHeight = (int) (imageHandler.getStoredImage().getHeight() * scaledHeightRatio);
        }
        RectCursor scaledCursor = rectCursor.makeScaledCursor(scaledWidthRatio, scaledHeightRatio);

        BufferedImage tempImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        scaledImage = buildNewImage(tempImage, mainFrame.getBackgroundColor(), scaledCursor);
    }

    private BufferedImage buildNewImage(BufferedImage tempImage, Color backgroundColor, RectCursor cursor) {
        fillImageWithBackgroundColor(tempImage, backgroundColor);
        Graphics2D g = tempImage.createGraphics();
        BufferedImage storedImage = imageHandler.getStoredImage();
        g.drawImage(storedImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), null);
        g.dispose();
//        cursor.setColor(rectCursor.getContrastingColor(backgroundColor));
        cursor.drawCursorOnImage(tempImage);
        return tempImage;
    }

    private void fillImageWithBackgroundColor(BufferedImage tempImage, Color backgroundColor) {
        Graphics2D g = tempImage.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        g.dispose();
    }

    // TODO: Move to own class
    public double[] getScaledWidthAndHeightRatios() {
        double scaledWidthRatio;
        double scaledHeightRatio;
        BufferedImage storedImage = imageHandler.getStoredImage();

        if (mainFrame.getZoomValue() == ZoomValue.FIT_TO_SCREEN) {
            scaledWidthRatio = (double) mainFrame.getScrollPanelWidth() / (double) storedImage.getWidth();
            scaledHeightRatio = (double) mainFrame.getScrollPanelHeight() / (double) storedImage.getHeight();
        } else {
            scaledWidthRatio = mainFrame.getZoomValue().getPercentage();
            scaledHeightRatio = mainFrame.getZoomValue().getPercentage();
        }
        return new double[] {scaledWidthRatio, scaledHeightRatio};
    }

    // TODO: Reexamine
    private void setScrollBarValue(JScrollBar scrollBar, int value) {
        int visibleAmount = scrollBar.getVisibleAmount();
        value = (value - visibleAmount / 2);
        scrollBar.setValue(value);
    }

    private int findCenterOfScaledCursor(int value, double scaleRatio, int cropMeasurement) {
        value = (int) (value * scaleRatio);
        int scaledCropMeasurement = (int) (cropMeasurement * scaleRatio);
        value = value + scaledCropMeasurement / 2;
        return value;
    }

    public void focusOnCursor() {
        final JScrollBar horizontalScrollBar;
        final JScrollBar verticalScrollBar;
        final int horizontalValue;
        final int verticalValue;
        double[] scaledWidthAndHeight = getScaledWidthAndHeightRatios();

        // TODO: Replace cropWidth/Height with cursor width/height
//        horizontalScrollBar = mainFrame.getMapMakerImageScrollPane().getHorizontalScrollBar();
//        horizontalValue = findCenterOfScaledCursor(rectCursor.getX(), scaledWidthAndHeight[0], mainFrame.getCropWidth());
//        SwingUtilities.invokeLater(() -> setScrollBarValue(horizontalScrollBar, horizontalValue));
//
//        verticalScrollBar = mainFrame.getMapMakerImageScrollPane().getVerticalScrollBar();
//        verticalValue = findCenterOfScaledCursor(rectCursor.getY(), scaledWidthAndHeight[1], mainFrame.getCropHeight());
//        SwingUtilities.invokeLater(() -> setScrollBarValue(verticalScrollBar, verticalValue));
    }

    public void focusOnOrigin() {
        final JScrollBar horizontalScrollBar;
        final JScrollBar verticalScrollBar;
        final int horizontalValue;
        final int verticalValue;

        horizontalScrollBar = mainFrame.getMapMakerImageScrollPane().getHorizontalScrollBar();
        horizontalValue = scaledImage.getWidth() / 2;
        SwingUtilities.invokeLater(() -> setScrollBarValue(horizontalScrollBar, horizontalValue));

        verticalScrollBar = mainFrame.getMapMakerImageScrollPane().getVerticalScrollBar();
        verticalValue = scaledImage.getHeight() / 2;
        SwingUtilities.invokeLater(() -> setScrollBarValue(verticalScrollBar, verticalValue));
    }

//    public void trimImageHorizontally(int offsetAdjustment) {
//        BufferedImage storedImage = imageHandler.getStoredImage();
//        int cropHeight = mainFrame.getCropHeight();
//        BufferedImage upperSubImage;
//        BufferedImage lowerSubImage;
//        BufferedImage newImage;
//
//        if (storedImage.getHeight() - (cropHeight + 2 * offsetAdjustment) <= 0) {
//            return;
//        }
//
//        newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight() - (cropHeight + offsetAdjustment),
//                BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g = newImage.createGraphics();
//        if (rectCursor.getY() - offsetAdjustment > 0) {
//            upperSubImage = storedImage.getSubimage(0, 0, storedImage.getWidth(), rectCursor.getY() - offsetAdjustment);
//            g.drawImage(upperSubImage, 0, 0, null);
//        }
//        if (rectCursor.getY() + cropHeight + offsetAdjustment < storedImage.getHeight()) {
//            lowerSubImage = storedImage.getSubimage(0, rectCursor.getY() + cropHeight + offsetAdjustment, storedImage.getWidth(),
//                    storedImage.getHeight() - (rectCursor.getY() + cropHeight + offsetAdjustment));
//            g.drawImage(lowerSubImage, 0, rectCursor.getY(), null);
//        }
//        g.dispose();
//
//        imageHandler.updateAndStoreChangedImages(newImage);
//        adjustCursorAfterTrimming();
//    }
//
//    public void trimImageVertically(int offsetAdjustment) {
//        BufferedImage storedImage = imageHandler.getStoredImage();
//        int cropWidth = mainFrame.getCropWidth();
//        BufferedImage leftSubImage;
//        BufferedImage rightSubImage;
//        BufferedImage newImage;
//
//        if (storedImage.getWidth() - (cropWidth + 2 * offsetAdjustment) <= 0) {
//            return;
//        }
//
//        // subtract offsetAdjustment once from the width of storedImage. If offsetAdjustment = 0, this has no effect
//        // otherwise,
//        newImage = new BufferedImage(storedImage.getWidth() - (cropWidth - offsetAdjustment), storedImage.getHeight(),
//                BufferedImage.TYPE_INT_ARGB);  // TODO: Look into how this works
//        Graphics2D g = newImage.createGraphics();
//        if (rectCursor.getX() - offsetAdjustment > 0) {
//            leftSubImage = storedImage.getSubimage(0, 0, rectCursor.getX() - offsetAdjustment, storedImage.getHeight());
//            g.drawImage(leftSubImage, 0, 0, null);
//        }
//        if (rectCursor.getX() + cropWidth + offsetAdjustment <= storedImage.getWidth()) {
//            rightSubImage = storedImage.getSubimage(rectCursor.getX() + cropWidth + offsetAdjustment, 0,
//                    storedImage.getWidth() - (rectCursor.getX() + cropWidth + offsetAdjustment), storedImage.getHeight());
//            g.drawImage(rightSubImage, rectCursor.getX(), 0, null);
//        }
//        g.dispose();
//
//        imageHandler.updateAndStoreChangedImages(newImage);
//        adjustCursorAfterTrimming();
//    }
//
//    private void adjustCursorAfterTrimming() {
//        BufferedImage storedImage = imageHandler.getStoredImage();
//        int cropWidth = mainFrame.getCropWidth();
//        int cropHeight = mainFrame.getCropHeight();
//
//        if (cropWidth > storedImage.getWidth()) {
//            mainFrame.setCropWidth(storedImage.getWidth());
//            cropWidth = storedImage.getWidth();
//        }
//        if (cropHeight > storedImage.getHeight()) {
//            mainFrame.setCropHeight(storedImage.getHeight());
//            cropHeight = storedImage.getHeight();
//        }
//        if (rectCursor.getX() + cropWidth > storedImage.getWidth()) {
//            rectCursor.setX(storedImage.getWidth() - cropWidth);
//        }
//        if (rectCursor.getY() + cropHeight > storedImage.getHeight()) {
//            rectCursor.setY(storedImage.getHeight() - cropHeight);
//        }
//    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public ImageHandler getImageHandler() {
        return imageHandler;
    }

    public BufferedImage getDisplayedImage() {
        return displayedImage;
    }

    public BufferedImage getScaledImage() {
        return scaledImage;
    }

    public RectCursor getRectCursor() {
        return rectCursor;
    }

    public void setRectCursor(RectCursor rectCursor) {
        this.rectCursor = rectCursor;
    }

    public int getMostRecentMouseX() {
        return mostRecentMouseX;
    }

    public int getMostRecentMouseY() {
        return mostRecentMouseY;
    }
//
//    public void setShouldFollowCursor(boolean shouldFollowCursor) {
//        this.shouldFollowCursor = shouldFollowCursor;
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(scaledImage, 0, 0, null);
//        if (shouldFollowCursor) {
//            focusOnCursor();
//            shouldFollowCursor = false;
//        }
    }

    @Override
    public final Dimension getPreferredSize() {
//        int width = (int) prefSize.getWidth();
//        int height = (int) prefSize.getHeight();
//        if (mainFrame.getZoomValue() == ZoomValue.FIT_TO_SCREEN) {
//            return new Dimension(mainFrame.getScrollPanelWidth(), mainFrame.getScrollPanelHeight());
//        }
        int newWidth = (scaledImage.getWidth() > WIDTH ? scaledImage.getWidth() : WIDTH);
        int newHeight = (scaledImage.getHeight() > HEIGHT ? scaledImage.getHeight() : HEIGHT);
        return new Dimension(newWidth, newHeight);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) {
        mostRecentMouseX = e.getX();
        mostRecentMouseY = e.getY();
        mainFrame.getImagePreviewPanel().setMouseOverImage(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mainFrame.getImagePreviewPanel().setMouseOverImage(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseEntered(e);
    }
}
