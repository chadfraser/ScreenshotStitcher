import javax.swing.*;
import java.awt.*;

public class MapMakerWindow extends JFrame {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 448;

    public MapMakerWindow() {
        setTitle("NES Map Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setFocusable(true);
        setPreferredSize(new Dimension(600, 600));

        setLayout(new BorderLayout());
        add(new UndoAndZoomPanel(), BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MapMakerWindow();
    }
}
