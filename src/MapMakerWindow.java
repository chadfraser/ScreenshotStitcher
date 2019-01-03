import javax.swing.*;
import java.awt.*;

public class MapMakerWindow extends JFrame {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;

    private MapMakerImagePanel mapMakerImagePanel;
    private PreviewAndSelectionPanel previewAndSelectionPanel;
    private MainImageAndSavePanel mainImageAndSavePanel;
    private SettingsPanel settingsPanel;

    private MapMakerWindow() {
        setTitle("NES Map Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
//        setResizable(true);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setMinimumSize(new Dimension(700, 600));

        previewAndSelectionPanel = new PreviewAndSelectionPanel(this);
        mainImageAndSavePanel = new MainImageAndSavePanel(this);
        settingsPanel = new SettingsPanel(this);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        add(previewAndSelectionPanel);
        add(mainImageAndSavePanel);
        add(settingsPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MapMakerWindow();
    }

    public static int getConstantWidth() {
        return WIDTH;
    }

    public static int getConstantHeight() {
        return HEIGHT;
    }

    public MapMakerImagePanel getMapMakerImagePanel() {
        return mapMakerImagePanel;
    }
}
