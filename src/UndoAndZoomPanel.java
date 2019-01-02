import javax.swing.*;
import java.awt.*;

public class UndoAndZoomPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 512;
    private static final int HEIGHT = 448;

    private MapMakerWindow mapMakerWindow;

    public UndoAndZoomPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

//        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JToggleButton zoomButton = new JToggleButton("ZOOM");
        JButton undoButton = new JButton("UNDO");
        JButton redoButton = new JButton("REDO");


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        add(zoomButton, c);

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridwidth = 3;
//        c.gridx = 0;
//        c.gridy = 1;
//        add(saveButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 1.0;
//        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(undoButton, c);

        c.gridx = 1;
        add(Box.createRigidArea(new Dimension(10, 10)), c);

        c.gridx = 2;
//        c.anchor = GridBagConstraints.LAST_LINE_END;
        add(redoButton, c);
    }
}

