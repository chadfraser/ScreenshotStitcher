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

        setBackground(Color.WHITE);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        storedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        displayImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        scaledDisplayImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        addMouseListener(this);
        addMouseMotionListener(this);

        Graphics2D g = scaledDisplayImage.createGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, scaledDisplayImage.getWidth(), scaledDisplayImage.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(cursorX, cursorY, mapMakerWindow.getCropWidth(), mapMakerWindow.getCropHeight());
        g.dispose();
    }

    public void updateImages() {
        updateDisplayImage();
        updateScaledDisplayImage();
        repaint();
    }

    private void updateDisplayImage() {
        BufferedImage tempImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tempImage.createGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        g.drawImage(storedImage, 0, 0, null);
        g.setColor(Color.BLACK);  // TODO: Check for lightness/darkness
        g.drawRect(cursorX, cursorY, mapMakerWindow.getCropWidth(), mapMakerWindow.getCropHeight());
        g.dispose();

        displayImage = tempImage;
    }

    private void updateScaledDisplayImage() {
        double scaleWidth = (double) mapMakerWindow.getScrollPanelWidth() / (double) storedImage.getWidth();
        double scaleHeight = (double) mapMakerWindow.getScrollPanelHeight() / (double) storedImage.getHeight();
        int scaleCursorX = (int) (cursorX * scaleWidth);
        int scaleCursorY = (int) (cursorY * scaleHeight);
        int scaleCropWidth = (int) (mapMakerWindow.getCropWidth() * scaleWidth);
        int scaleCropHeight = (int) (mapMakerWindow.getCropHeight() * scaleHeight);

        BufferedImage tempImage = new BufferedImage(mapMakerWindow.getScrollPanelWidth(),
        mapMakerWindow.getScrollPanelHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tempImage.createGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        g.drawImage(storedImage, 0, 0, mapMakerWindow.getScrollPanelWidth(), mapMakerWindow.getScrollPanelHeight(),
        null);
        g.setColor(Color.BLACK);
        //            g.setColor(Color.BLACK);  // TODO: Check for lightness/darkness
        g.drawRect(scaleCursorX, scaleCursorY, scaleCropWidth, scaleCropHeight);
        g.dispose();
        scaledDisplayImage = tempImage;
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

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setDisplayImage(BufferedImage displayImage) {
        this.displayImage = displayImage;
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
        double scaleWidth = (double) storedImage.getWidth() / (double) mapMakerWindow.getScrollPanelWidth();
        double scaleHeight = (double) storedImage.getHeight() / (double) mapMakerWindow.getScrollPanelHeight();

        int previewX = (int) (e.getX() * scaleWidth);
        int previewY = (int) (e.getY() * scaleHeight);
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
