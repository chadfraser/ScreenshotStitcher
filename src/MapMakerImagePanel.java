import javafx.scene.layout.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MapMakerImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private int cursorX = 0;
    private int cursorY = 0;
    private BufferedImage storedImage;
    private BufferedImage displayImage;
    private BufferedImage scaledDisplayImage;

    private MapMakerWindow mapMakerWindow;

    MapMakerImagePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setBackground(Color.WHITE);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        storedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        displayImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        scaledDisplayImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        Graphics g = scaledDisplayImage.getGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, scaledDisplayImage.getWidth(), scaledDisplayImage.getHeight());
        g.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(scaledDisplayImage, 0, 0, null);
    }

    public void updateDisplayImage() {
        BufferedImage tempImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = tempImage.getGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        g.drawImage(storedImage, 0, 0, null);
        g.setColor(Color.BLACK);  // TODO: Check for lightness/darkness
        g.drawRect(cursorX, cursorY, mapMakerWindow.getCropWidth(), mapMakerWindow.getCropHeight());
        g.dispose();

        displayImage = tempImage;
    }

    public void updateScaledDisplayImage() {
        double scaleWidth = (double) mapMakerWindow.getScrollPanelWidth() / (double) storedImage.getWidth();
        double scaleHeight = (double) mapMakerWindow.getScrollPanelHeight() / (double) storedImage.getHeight();
        int scaleCursorX = (int) (cursorX * scaleWidth);
        int scaleCursorY = (int) (cursorY * scaleHeight);
        int scaleCropWidth = (int) (mapMakerWindow.getCropWidth() * scaleWidth);
        int scaleCropHeight = (int) (mapMakerWindow.getCropHeight() * scaleHeight);

        BufferedImage tempImage = new BufferedImage(mapMakerWindow.getScrollPanelWidth(),
                mapMakerWindow.getScrollPanelHeight(), BufferedImage.TYPE_INT_ARGB);
//        Image scaledInstance = displayImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        Graphics g = tempImage.getGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        g.drawImage(storedImage, 0, 0, mapMakerWindow.getScrollPanelWidth(), mapMakerWindow.getScrollPanelHeight(),
                null);
        g.setColor(Color.BLACK);
        g.fillRect(scaleCursorX, scaleCursorY, scaleCropWidth, scaleCropHeight);
        g.dispose();
        scaledDisplayImage = tempImage;
        System.out.println("SCALED DISPLAY: " + scaledDisplayImage.getWidth() + " " + scaledDisplayImage.getHeight());
        System.out.println("DISPLAY: " + displayImage.getWidth() + " " + displayImage.getHeight());
        System.out.println("CURSOR: " + cursorX + " " + cursorY);
        System.out.println("SCALE: " + scaleCursorX + " " + scaleCursorY + " " + scaleWidth + " " + scaleHeight);
        System.out.println("WID/HEI: " + mapMakerWindow.getScrollPanelHeight() + " " + mapMakerWindow.getScrollPanelWidth() +
                " " + storedImage.getWidth() + " " + storedImage.getHeight());
        System.out.println();
//        AffineTransform affineTransform = new AffineTransform();
//        float newWidth = storedImage.getWidth() / getWidth();
//        float newHeight = storedImage.getHeight() / getHeight();
//        affineTransform.scale(newWidth, newHeight);
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

}
