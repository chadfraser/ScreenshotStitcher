package panels;

import handler.ImageHandler;
import main.MainFrame;
import zoom.ZoomValue;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ImagePanel extends JPanel implements MouseInputListener, Serializable {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private int cursorX;
    private int cursorY;
    private BufferedImage displayedImage;
    private BufferedImage scaledImage;

    private MainFrame mainFrame;
    private ImageHandler imageHandler;
    private boolean shouldFollowCursor;
    private int mostRecentMouseX;
    private int mostRecentMouseY;

    public ImagePanel(MainFrame mainFrame) {
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setSize(new Dimension(WIDTH, HEIGHT));  // TODO Improve this area
        setFocusable(true);

        this.mainFrame = mainFrame;
        this.imageHandler = new ImageHandler(this);

        cursorX = (WIDTH - mainFrame.getCropWidth()) / 2;
        cursorY = (HEIGHT - mainFrame.getCropHeight()) / 2;
        shouldFollowCursor = false;

        addMouseListener(this);
        addMouseMotionListener(this);
        initializeImages();
    }

    private void initializeImages() {
        scaledImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        updateDisplayedImage();
//        Graphics2D g = displayedImage.createGraphics();
//        g.setColor(mainFrame.getBackgroundColor());
//        g.fillRect(0, 0, getWidth(), getHeight());
//        g.setColor(getContrastingColor(mainFrame.getBackgroundColor()));
//        g.drawRect(cursorX, cursorY, mainFrame.getCropWidth(), mainFrame.getCropHeight());  // TODO: Double check scale
//        g.dispose();
    }

    // Returns black if the main color is light, or white if the main color is dark
    private Color getContrastingColor(Color mainColor) {
        double luma = 0.299 * mainColor.getRed() + 0.587 * mainColor.getGreen() + 0.114 * mainColor.getBlue();
        return (luma >= 128) ? Color.BLACK : Color.WHITE;
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
        displayedImage = buildImageWithBackgroundColorAndCursor(tempImage, mainFrame.getBackgroundColor(), cursorX,
                cursorY, mainFrame.getCropWidth(), mainFrame.getCropHeight());
    }

    private void updateScaledImage() {
        double[] scaledWidthAndHeight = getScaledWidthAndHeightRatios();
        double scaledWidthRatio = scaledWidthAndHeight[0];
        double scaledHeightRatio = scaledWidthAndHeight[1];
        int imageWidth = mainFrame.getScrollPanelWidth();
        int imageHeight = mainFrame.getScrollPanelHeight();

        if (mainFrame.getZoomValue() != ZoomValue.FIT_TO_SCREEN) {
            imageWidth = (int) (imageHandler.getStoredImage().getWidth() * scaledWidthRatio);
            imageHeight = (int) (imageHandler.getStoredImage().getHeight() * scaledHeightRatio);
        }
        int scaledCursorX = (int) (cursorX * scaledWidthRatio);
        int scaledCursorY = (int) (cursorY * scaledHeightRatio);
        int scaledCropWidth = (int) (mainFrame.getCropWidth() * scaledWidthRatio);
        int scaledCropHeight = (int) (mainFrame.getCropHeight() * scaledHeightRatio);

        BufferedImage tempImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        scaledImage = buildImageWithBackgroundColorAndCursor(tempImage, mainFrame.getBackgroundColor(), scaledCursorX,
                scaledCursorY, scaledCropWidth, scaledCropHeight);
    }

    // TODO: Split into two methods?
    private BufferedImage buildImageWithBackgroundColorAndCursor(BufferedImage tempImage, Color backgroundColor, int x,
                                                                 int y, int width, int height) {
        BufferedImage storedImage = imageHandler.getStoredImage();
        Graphics2D g = tempImage.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        g.drawImage(storedImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), null);

        // Draw cursor
        g.setColor(getContrastingColor(backgroundColor));
        g.drawRect(x, y, width, height);
        g.dispose();
        return tempImage;
    }

    public double[] getScaledWidthAndHeightRatios() {
        double scaledWidth;
        double scaledHeight;
        BufferedImage storedImage = imageHandler.getStoredImage();

        if (mainFrame.getZoomValue() == ZoomValue.FIT_TO_SCREEN) {
            scaledWidth = (double) mainFrame.getScrollPanelWidth() / (double) storedImage.getWidth();
            scaledHeight = (double) mainFrame.getScrollPanelHeight() / (double) storedImage.getHeight();
        } else {
            scaledWidth = mainFrame.getZoomValue().getPercentage();
            scaledHeight = mainFrame.getZoomValue().getPercentage();
        }
        return new double[] {scaledWidth, scaledHeight};
    }

    public void increaseImageSize(int widthOfNewImage, int heightOfNewImage) {
        BufferedImage storedImage = imageHandler.getStoredImage();
        int updatedWidth = storedImage.getWidth();
        int updatedHeight = storedImage.getHeight();

        updatedWidth = (updatedWidth >= widthOfNewImage) ? updatedWidth : widthOfNewImage;
        updatedHeight = (updatedHeight >= heightOfNewImage) ? updatedHeight : heightOfNewImage;

        BufferedImage finalImage = new BufferedImage(updatedWidth, updatedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(storedImage, (updatedWidth - storedImage.getWidth()) / 2,
                (updatedHeight - storedImage.getHeight()) / 2,
                null);
        g.dispose();
        imageHandler.updateImages(finalImage);
    }

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

        horizontalScrollBar = mainFrame.getMapMakerImageScrollPane().getHorizontalScrollBar();
        horizontalValue = findCenterOfScaledCursor(cursorX, scaledWidthAndHeight[0], mainFrame.getCropWidth());
        SwingUtilities.invokeLater(() -> setScrollBarValue(horizontalScrollBar, horizontalValue));

        verticalScrollBar = mainFrame.getMapMakerImageScrollPane().getVerticalScrollBar();
        verticalValue = findCenterOfScaledCursor(cursorY, scaledWidthAndHeight[1], mainFrame.getCropHeight());
        SwingUtilities.invokeLater(() -> setScrollBarValue(verticalScrollBar, verticalValue));
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

    public void moveCursor(String direction) {
        BufferedImage storedImage = imageHandler.getStoredImage();
        BufferedImage newImage;

        int x = getCursorX();
        int y = getCursorY();
        int cropWidth = mainFrame.getCropWidth();
        int cropHeight = mainFrame.getCropHeight();
        int offsets = mainFrame.getOffsets();

        int newWidth = storedImage.getWidth();
        int newHeight = storedImage.getHeight();
        int horizontalAdjustment = 0;
        int verticalAdjustment = 0;

        if ("up".equals(direction)) {
            y -= (cropHeight + offsets);
            if (y < 0) {
                newHeight -= y;
                verticalAdjustment -= y;
                y = 0;
            }
        } else if ("down".equals(direction)) {
            y += (cropHeight + offsets);
            if ((y + cropHeight) > storedImage.getHeight()) {
                newHeight = y + cropHeight;
            }
        } else if ("left".equals(direction)) {
            x -= (cropWidth + offsets);
            if (x < 0) {
                newWidth -= x;
                horizontalAdjustment -= x;
                x = 0;
            }
        } else if ("right".equals(direction)) {
            x += (cropWidth + offsets);
            if ((x + cropWidth) > storedImage.getWidth()) {
                newWidth = x + cropWidth;
            }
        }

        newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(imageHandler.getStoredImage(), horizontalAdjustment, verticalAdjustment, null);
        g.dispose();
        imageHandler.updateImages(newImage);

        cursorX = x;
        cursorY = y;
        shouldFollowCursor = true;
    }

    public void trimImageHorizontally(int offsetAdjustment) {
        BufferedImage storedImage = imageHandler.getStoredImage();
        int cropHeight = mainFrame.getCropHeight();
        BufferedImage upperSubImage;
        BufferedImage lowerSubImage;
        BufferedImage newImage;

        if (storedImage.getHeight() - (cropHeight + 2 * offsetAdjustment) <= 0) {
            return;
        }

        newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight() - (cropHeight + offsetAdjustment),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = newImage.createGraphics();
        if (cursorY - offsetAdjustment > 0) {
            upperSubImage = storedImage.getSubimage(0, 0, storedImage.getWidth(), cursorY - offsetAdjustment);
            g.drawImage(upperSubImage, 0, 0, null);
        }
        if (cursorY + cropHeight + offsetAdjustment < storedImage.getHeight()) {
            lowerSubImage = storedImage.getSubimage(0, cursorY + cropHeight + offsetAdjustment, storedImage.getWidth(),
                    storedImage.getHeight() - (cursorY + cropHeight + offsetAdjustment));
            g.drawImage(lowerSubImage, 0, cursorY, null);
        }
        g.dispose();

        imageHandler.updateAndStoreChangedImages(newImage);
        adjustCursorAfterTrimming();
    }

    public void trimImageVertically(int offsetAdjustment) {
        BufferedImage storedImage = imageHandler.getStoredImage();
        int cropWidth = mainFrame.getCropWidth();
        BufferedImage leftSubImage;
        BufferedImage rightSubImage;
        BufferedImage newImage;

        if (storedImage.getWidth() - (cropWidth + 2 * offsetAdjustment) <= 0) {
            return;
        }

        // subtract offsetAdjustment once from the width of storedImage. If offsetAdjustment = 0, this has no effect
        // otherwise,
        newImage = new BufferedImage(storedImage.getWidth() - (cropWidth - offsetAdjustment), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);  // TODO: Look into how this works
        Graphics2D g = newImage.createGraphics();
        if (cursorX - offsetAdjustment > 0) {
            leftSubImage = storedImage.getSubimage(0, 0, cursorX - offsetAdjustment, storedImage.getHeight());
            g.drawImage(leftSubImage, 0, 0, null);
        }
        if (cursorX + cropWidth + offsetAdjustment <= storedImage.getWidth()) {
            rightSubImage = storedImage.getSubimage(cursorX + cropWidth + offsetAdjustment, 0,
                    storedImage.getWidth() - (cursorX + cropWidth + offsetAdjustment), storedImage.getHeight());
            g.drawImage(rightSubImage, cursorX, 0, null);
        }
        g.dispose();

        imageHandler.updateAndStoreChangedImages(newImage);
        adjustCursorAfterTrimming();
    }

    private void adjustCursorAfterTrimming() {
        BufferedImage storedImage = imageHandler.getStoredImage();
        int cropWidth = mainFrame.getCropWidth();
        int cropHeight = mainFrame.getCropHeight();

        if (cropWidth > storedImage.getWidth()) {
            mainFrame.setCropWidth(storedImage.getWidth());
            cropWidth = storedImage.getWidth();
        }
        if (cropHeight > storedImage.getHeight()) {
            mainFrame.setCropHeight(storedImage.getHeight());
            cropHeight = storedImage.getHeight();
        }
        if (cursorX + cropWidth > storedImage.getWidth()) {
            cursorX = storedImage.getWidth() - cropWidth;
        }
        if (cursorY + cropHeight > storedImage.getHeight()) {
            cursorY = storedImage.getHeight() - cropHeight;
        }
    }

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

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public int getMostRecentMouseX() {
        return mostRecentMouseX;
    }

    public int getMostRecentMouseY() {
        return mostRecentMouseY;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
    }

    public void setShouldFollowCursor(boolean shouldFollowCursor) {
        this.shouldFollowCursor = shouldFollowCursor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(scaledImage, 0, 0, null);
        if (shouldFollowCursor) {
            focusOnCursor();
            shouldFollowCursor = false;
        }
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
//        mainFrame.getImagePreviewPanel().setMouseOverImage(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        mainFrame.getImagePreviewPanel().setMouseOverImage(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseEntered(e);
    }
}
