import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MapMakerWindow extends JFrame {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 600;

    private MapMakerImagePanel mapMakerImagePanel;
    private ButtonPanelAndPreviewPanelPanel buttonPanelAndPreviewPanelPanel;
    private ImagePanelAndSavePanelPanel imagePanelAndSavePanelPanel;
    private UndoAndZoomPanel undoAndZoomPanel;

    private MapMakerWindow() {
        setTitle("NES Map Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
//        setResizable(true);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setMinimumSize(new Dimension(700, 600));

        buttonPanelAndPreviewPanelPanel = new ButtonPanelAndPreviewPanelPanel(this);
        imagePanelAndSavePanelPanel = new ImagePanelAndSavePanelPanel(this);
        undoAndZoomPanel = new UndoAndZoomPanel(this);

//        setLayout(new GridBagLayout());
        add(undoAndZoomPanel, BorderLayout.EAST);
        add(imagePanelAndSavePanelPanel, BorderLayout.CENTER);
        add(buttonPanelAndPreviewPanelPanel, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MapMakerWindow();
    }

    public MapMakerImagePanel getMapMakerImagePanel() {
        return mapMakerImagePanel;
    }
}
