package panels;

import main.MainFrame;
import zoom.ZoomValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoomPanel extends JPanel implements ActionListener {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 450;

    private JPanel buttonPanel;
    private JPanel comboBoxPanel;

    private JButton focusOriginButton;
    private JButton focusCursorButton;
    private JLabel zoomComboBoxLabel;
    private JComboBox<ZoomValue> zoomComboBox;

    private MainFrame mainFrame;

    public ZoomPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        initializeButtons();
        initializeComboBox();
        initializePanels();
        initializeLayout();
    }

    private void initializeButtons() {
        focusOriginButton = new JButton("FOCUS ON ORIGIN");
        focusCursorButton = new JButton("FOCUS ON CURSOR");
        focusOriginButton.addActionListener(this);
        focusCursorButton.addActionListener(this);
    }

    private void initializeComboBox() {
        zoomComboBoxLabel = new JLabel("Zoom Value");
        zoomComboBoxLabel.setLabelFor(zoomComboBox);

        zoomComboBox = new JComboBox<>();
        zoomComboBox.setModel(new DefaultComboBoxModel<>(ZoomValue.values()));
        zoomComboBox.setSelectedIndex(5);
        zoomComboBox.addActionListener(this);
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.25;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(buttonPanel, c);

        c.anchor = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.weighty = 0.25;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(comboBoxPanel, c);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(Box.createGlue(), c);
    }

    private void initializePanels() {
        buttonPanel = new JPanel(new GridBagLayout());
        comboBoxPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 10, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        initializeButtonPanel(c);
        initializeComboBoxPanel(c);
    }

    private void initializeComboBoxPanel(GridBagConstraints c) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        comboBoxPanel.add(zoomComboBoxLabel, c);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        comboBoxPanel.add(zoomComboBox, c);
    }

    private void initializeButtonPanel(GridBagConstraints c) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonPanel.add(focusOriginButton, c);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)), c);  // TODO: Make this line adjustable

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        buttonPanel.add(focusCursorButton, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == zoomComboBox) {
            mainFrame.setZoomValue((ZoomValue) zoomComboBox.getSelectedItem());
            mainFrame.getImagePanel().updateImages();
            mainFrame.getImagePanel().focusOnCursor();
        } else if (e.getSource() == focusOriginButton) {
            mainFrame.getImagePanel().focusOnOrigin();
        } else if (e.getSource() == focusCursorButton) {
            mainFrame.getImagePanel().focusOnCursor();
        }
    }

    public JComboBox<ZoomValue> getZoomComboBox() {
        return zoomComboBox;
    }
}
