import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainImageAndSavePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 512;
    private static final int HEIGHT = 448;

    private MapMakerWindow mapMakerWindow;
    private MapMakerImagePanel imagePanel;
    private SavePanel savePanel;

    MainImageAndSavePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        imagePanel = new MapMakerImagePanel(mapMakerWindow);
        savePanel = new SavePanel(mapMakerWindow);
//        setFocusable(true);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(imagePanel);
        add(savePanel);
//        pack();
//        setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
}
