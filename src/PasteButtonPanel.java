import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PasteButtonPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 150;

    private MapMakerWindow mapMakerWindow;

    PasteButtonPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.RED);
        setFocusable(true);

        JButton pasteRoomButton = new JButton("PASTE ROOM");
//        pasteRoomButton.getInsets(new Insets(0, 0, 0, 0));

        setLayout(new BorderLayout());
        add(pasteRoomButton, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    }
}
