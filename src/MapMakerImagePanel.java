import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.Serializable;

public class MapMakerImagePanel extends JPanel implements MouseInputListener, Serializable {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private int cursorX;
    private int cursorY;
    private BufferedImage storedImage;
    private BufferedImage displayImage;
    private BufferedImage scaledDisplayImage;

    private MapMakerWindow mapMakerWindow;

    MapMakerImagePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        cursorX = (WIDTH - mapMakerWindow.getCropWidth()) / 2;
        cursorY = (HEIGHT - mapMakerWindow.getCropHeight()) / 2;

        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        storedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        displayImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        scaledDisplayImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        addMouseListener(this);
        addMouseMotionListener(this);

        Graphics2D g = scaledDisplayImage.createGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, scaledDisplayImage.getWidth(), scaledDisplayImage.getHeight());
        g.setColor(getContrastingColor());
        g.drawRect(cursorX, cursorY, mapMakerWindow.getCropWidth(), mapMakerWindow.getCropHeight());
        g.dispose();
    }

    private Color getContrastingColor() {
        Color backgroundColor = mapMakerWindow.getBackgroundColor();
        double luma = 0.299 * backgroundColor.getRed() + 0.587 * backgroundColor.getGreen() +
                0.114 * backgroundColor.getBlue();
        if (luma >= 128) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    public void updateImages() {
        updateDisplayImage();
        updateScaledDisplayImage();
        repaint();
    }

    private void updateDisplayImage() {
        BufferedImage tempImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        tempImage = buildImageFromStoredImage(tempImage, cursorX, cursorY, mapMakerWindow.getCropWidth(),
                mapMakerWindow.getCropHeight());
        displayImage = tempImage;
    }

    private void updateScaledDisplayImage() {
        double[] scaledWidthAndHeight = getScaledWidthAndHeight();
        double scaleWidth = scaledWidthAndHeight[0];
        double scaleHeight = scaledWidthAndHeight[1];
        int imageWidth = mapMakerWindow.getScrollPanelWidth();
        int imageHeight = mapMakerWindow.getScrollPanelHeight();

        if (mapMakerWindow.getZoomValue() != ZoomValue.FIT_TO_SCREEN) {
            imageWidth = (int) (storedImage.getWidth() * scaleWidth);
            imageHeight = (int) (storedImage.getHeight() * scaleHeight);
        }
        int scaleCursorX = (int) (cursorX * scaleWidth);
        int scaleCursorY = (int) (cursorY * scaleHeight);
        int scaleCropWidth = (int) (mapMakerWindow.getCropWidth() * scaleWidth);
        int scaleCropHeight = (int) (mapMakerWindow.getCropHeight() * scaleHeight);

        BufferedImage tempImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        tempImage = buildImageFromStoredImage(tempImage, scaleCursorX, scaleCursorY, scaleCropWidth, scaleCropHeight);
        scaledDisplayImage = tempImage;
    }

    private BufferedImage buildImageFromStoredImage(BufferedImage tempImage, int x, int y, int width, int height) {
        Graphics2D g = tempImage.createGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        g.drawImage(storedImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), null);
        g.setColor(getContrastingColor());
        g.drawRect(x, y, width, height);
        g.dispose();
        return tempImage;
    }

    private double[] getScaledWidthAndHeight() {
        double scaleWidth;
        double scaleHeight;

        if (mapMakerWindow.getZoomValue() == ZoomValue.FIT_TO_SCREEN) {
            scaleWidth = (double) mapMakerWindow.getScrollPanelWidth() / (double) storedImage.getWidth();
            scaleHeight = (double) mapMakerWindow.getScrollPanelHeight() / (double) storedImage.getHeight();
        } else {
            scaleWidth = mapMakerWindow.getZoomValue().getPercentage();
            scaleHeight = mapMakerWindow.getZoomValue().getPercentage();
        }
        return new double[] {scaleWidth, scaleHeight};
    }

    private Dimension adjustPreviewCoordinates(int previewX, int previewY, int previewWidth, int previewHeight) {
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

    private BufferedImage adjustForUndersizedStoredImage(int previewX, int previewY, int previewWidth,
                                                         int previewHeight) {
        int xToDraw = 0;
        int yToDraw = 0;

        if (previewWidth > storedImage.getWidth()) {
            previewX = 0;
            xToDraw = (previewWidth - storedImage.getWidth()) / 2;
            previewWidth = storedImage.getWidth();
        }

        if (previewHeight > storedImage.getHeight()) {
            previewY = 0;
            yToDraw = (previewHeight - storedImage.getHeight()) / 2;
            previewHeight = storedImage.getHeight();
        }
        BufferedImage newImage = new BufferedImage((int) mapMakerWindow.getImagePreviewPanel().getSize().getWidth(),
                (int) mapMakerWindow.getImagePreviewPanel().getSize().getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        BufferedImage croppedImage = storedImage.getSubimage(previewX, previewY, previewWidth, previewHeight);
        g.drawImage(croppedImage, xToDraw, yToDraw, null);

        return newImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(scaledDisplayImage, 0, 0, null);
    }

    public BufferedImage getDisplayImage() {
        return displayImage;
    }

    public BufferedImage getStoredImage() {
        return storedImage;
    }


    public BufferedImage getScaledDisplayImage() {
        return scaledDisplayImage;
    }

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setStoredImage(BufferedImage storedImage) {
        this.storedImage = storedImage;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) {
        BufferedImage newPreviewImage;
        double[] scaledWidthAndHeight = getScaledWidthAndHeight();
        double scaledWidth = scaledWidthAndHeight[0];
        double scaledHeight = scaledWidthAndHeight[1];

        int previewX = (int) (e.getX() / scaledWidth);
        int previewY = (int) (e.getY() / scaledHeight);
        int previewWidth = (int) mapMakerWindow.getImagePreviewPanel().getSize().getWidth();
        int previewHeight = (int) mapMakerWindow.getImagePreviewPanel().getSize().getHeight();

        previewX -= previewWidth / 2;
        previewY -= previewHeight / 2;

        Dimension temp = adjustPreviewCoordinates(previewX, previewY, previewWidth, previewHeight);
        previewX = temp.width;
        previewY = temp.height;

        try {
            newPreviewImage = storedImage.getSubimage(previewX, previewY, previewWidth, previewHeight);
        } catch (RasterFormatException except) {
            newPreviewImage = adjustForUndersizedStoredImage(previewX, previewY, previewWidth, previewHeight);
        }
        mapMakerWindow.getImagePreviewPanel().updatePreviewPanel(newPreviewImage);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        int previewWidth = mapMakerWindow.getImagePreviewPanel().getPreviewImage().getWidth();
        int previewHeight = mapMakerWindow.getImagePreviewPanel().getPreviewImage().getHeight();

        BufferedImage blankPreviewImage = new BufferedImage(previewWidth, previewHeight, BufferedImage.TYPE_INT_ARGB);
        mapMakerWindow.getImagePreviewPanel().updatePreviewPanel(blankPreviewImage);
    }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseEntered(e);
    }


}
