import javax.swing.*;
import java.awt.*;

public class UndoButtonPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 450;

    private JPanel buttonPanel;
    private JPanel radioLabelPanel;
    private JPanel radioPanel;

    private JButton undoButton;
    private JButton redoButton;
//    private JLabel autoSaveLabel;
//    private ButtonGroup autoSaveButtonGroup;
    private JLabel previewOptionLabel;
    private ButtonGroup previewOptionButtonGroup;
    private JRadioButton previewUndoOption;
    private JRadioButton previewRedoOption;

    private MapMakerWindow mapMakerWindow;

    UndoButtonPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        buttonPanel = new JPanel(new GridBagLayout());
        radioLabelPanel = new JPanel(new GridBagLayout());
        radioPanel = new JPanel(new GridBagLayout());

        undoButton = new JButton("UNDO");
        redoButton = new JButton("REDO");
//        previewOptionLabel = new JLabel("Action to preview in the image preview panel:");
        previewOptionLabel = new JLabel("<html>Action to preview in the image preview panel:</html>");
        previewUndoOption = new JRadioButton("Preview undo option");
        previewRedoOption = new JRadioButton("Preview redo option");

        previewOptionLabel.setLabelFor(previewUndoOption);
        previewOptionButtonGroup = new ButtonGroup();
        previewOptionButtonGroup.add(previewUndoOption);
        previewOptionButtonGroup.add(previewRedoOption);

//        autoSaveLabel = new JLabel("Automatically store your progress as you work");
//        autoSaveButtonGroup = new ButtonGroup();
//
//        JRadioButton autoSaveOnOption = new JRadioButton("Turn autosave on");
//        JRadioButton autoSaveOffOption = new JRadioButton("Turn autosave off");

        initializePanels();
        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(buttonPanel, c);

//        c.weightx = 0.5;
//        c.weighty = 0.05;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.gridx = 0;
//        c.gridy = 1;
//        add(Box.createGlue(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(radioLabelPanel, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(radioPanel, c);

        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 4;
        add(Box.createGlue(), c);
    }

    private void initializePanels() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        initializeButtonPanel(c);
        initializeRadioLabelPanel(c);
        initializeRadioPanel(c);
    }

    private void initializeButtonPanel(GridBagConstraints c) {
        int insetWidth = WIDTH / 25;
        int insetHeight = HEIGHT / 45;

        c.insets = new Insets(insetHeight, insetWidth, insetHeight, insetWidth);

        c.weightx = 0.5;
        c.weighty = 0.45;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonPanel.add(undoButton, c);

//        c.weighty = 0.1;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.gridx = 0;
//        c.gridy = 1;
//        buttonPanel.add(Box.createGlue(), c);

        c.weighty = 0.45;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        buttonPanel.add(redoButton, c);
    }

    private void initializeRadioLabelPanel(GridBagConstraints c) {
        c.insets = new Insets(0, 0, 5, 0);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        radioLabelPanel.add(previewOptionLabel, c);
    }

    private void initializeRadioPanel(GridBagConstraints c) {
        c.fill = GridBagConstraints.NONE;

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        radioPanel.add(previewUndoOption, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        radioPanel.add(previewRedoOption, c);
    }
}
