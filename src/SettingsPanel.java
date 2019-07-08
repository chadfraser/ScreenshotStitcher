import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
//    private static final int HEIGHT = MapMakerWindow.getConstantHeight();

    private MapMakerWindow mapMakerWindow;
    private DataPanel dataPanel;
    private UndoButtonPanel undoAndZoomPanel;
    private ZoomPanel undoAndZoomPanel2;
    private SavePanel undoAndZoomPanel3;

    SettingsPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        final int HEIGHT = mapMakerWindow.getHeight();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        dataPanel = new DataPanel(mapMakerWindow);
        undoAndZoomPanel = new UndoButtonPanel(mapMakerWindow);
        undoAndZoomPanel2 = new ZoomPanel(mapMakerWindow);
        undoAndZoomPanel3 = new SavePanel(mapMakerWindow);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(undoAndZoomPanel3);
        add(Box.createVerticalGlue());
        add(dataPanel);
    }
}
