import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;

public class SettingsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = MapMakerWindow.getConstantHeight();

    private MapMakerWindow mapMakerWindow;
    private DataPanel dataPanel;
    private UndoAndZoomPanel undoAndZoomPanel;

    SettingsPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        dataPanel = new DataPanel(mapMakerWindow);
        undoAndZoomPanel = new UndoAndZoomPanel(mapMakerWindow);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(dataPanel);
        add(Box.createVerticalGlue());
        add(undoAndZoomPanel);
    }

    public static int getConstantWidth() {
        return WIDTH;
    }

    public static int getConstantHeight() {
        return HEIGHT;
    }
}
