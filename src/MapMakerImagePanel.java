import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapMakerImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

//    private int x = 300;
//    private int y = 300;
    private int cursorX = 300;
    private int cursorY = 300;
    private BufferedImage bufferedImage;

    private MapMakerWindow mapMakerWindow;

    MapMakerImagePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(Color.BLACK);
        g.drawRect(cursorX, cursorY, mapMakerWindow.getCropWidth(), mapMakerWindow.getCropHeight());
        System.out.println(cursorX + " " + cursorY + " " + mapMakerWindow.getCropWidth() + " " + mapMakerWindow.getCropHeight());
        g.dispose();
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
