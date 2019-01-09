import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapMakerImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private int cursorX = 300;
    private int cursorY = 300;
    private BufferedImage bufferedImage;
    private BufferedImage displayImage;

    private MapMakerWindow mapMakerWindow;

    MapMakerImagePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(mapMakerWindow.getBackgroundColor());
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bufferedImage == null) {
            System.err.println("The MapMakerImagePanel buffered image has been set to null.");
            System.exit(1);
        }

        g.drawImage(bufferedImage, 0, 0, null);
        setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
    }

}
