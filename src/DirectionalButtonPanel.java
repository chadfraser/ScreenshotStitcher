import javafx.scene.Group;

import javax.swing.*;
import java.awt.*;

class DirectionalButtonPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 450;

    private JPanel leftButtonPanel;
    private JPanel upDownButtonPanel;
    private JPanel rightButtonPanel;
    private JPanel editButtonPanel;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton pasteButton;
    private JButton deleteButton;

    private MapMakerWindow mapMakerWindow;

    DirectionalButtonPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
//        setBackground(Color.YELLOW);
        setFocusable(true);

        leftButtonPanel = new JPanel(new GridBagLayout());
        upDownButtonPanel = new JPanel(new GridBagLayout());
        rightButtonPanel = new JPanel(new GridBagLayout());
        editButtonPanel = new JPanel(new GridBagLayout());

        upButton = new JButton("UP");
        downButton = new JButton("DOWN");
        leftButton = new JButton("LEFT");
        rightButton = new JButton("RIGHT");
        pasteButton = new JButton("PASTE");
        deleteButton = new JButton("DELETE");

        initializePanels();
        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        c.weightx = 0.5;
        c.weighty = 0.45;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(leftButtonPanel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        add(upDownButtonPanel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = 0;
        add(rightButtonPanel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(Box.createGlue(), c);

        c.weighty = 0.1;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 2;
        add(editButtonPanel, c);
    }

    private void initializePanels() {
        int insetWidth = WIDTH / 125;
        int insetHeight = HEIGHT / 90;

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(insetHeight, insetWidth, insetHeight, insetWidth);

        initializeSidePanel(c, leftButtonPanel, leftButton);
        initializeSidePanel(c, rightButtonPanel, rightButton);
        initializeUpDownButtonPanel(c);
        initializeEditButtonPanel(c);
    }

    private void initializeSidePanel(GridBagConstraints c, JPanel sidePanel, JButton sideButton) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        sidePanel.add(Box.createGlue(), c);

        c.gridwidth = 1;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 1;
        sidePanel.add(sideButton, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        sidePanel.add(Box.createGlue(), c);
    }

    private void initializeUpDownButtonPanel(GridBagConstraints c) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        upDownButtonPanel.add(upButton, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        upDownButtonPanel.add(downButton, c);
    }

    private void initializeEditButtonPanel(GridBagConstraints c) {
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        editButtonPanel.add(pasteButton, c);

        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        editButtonPanel.add(Box.createHorizontalGlue(), c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 0;
        editButtonPanel.add(deleteButton, c);
    }
}
