import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoomPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 450;

    private JPanel buttonPanel;
    private JPanel comboBoxPanel;

    private JButton focusButton;
    private JLabel zoomComboBoxLabel;
    private JComboBox<ZoomValue> zoomComboBox;

    private MapMakerWindow mapMakerWindow;

    public ZoomPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        buttonPanel = new JPanel(new GridBagLayout());
        comboBoxPanel = new JPanel(new GridBagLayout());
        focusButton = new JButton("FOCUS ON ORIGIN");

        zoomComboBoxLabel = new JLabel("Zoom Value");
        zoomComboBoxLabel.setLabelFor(zoomComboBox);

//        String[] zoomValues = {"Fit to screen", "10%", "25%", "50%", "100%", "200%"};
//        zoomComboBox = new JComboBox<>(zoomValues);
        zoomComboBox = new JComboBox<>();
        zoomComboBox.setModel(new DefaultComboBoxModel<>(ZoomValue.values()));
        zoomComboBox.setSelectedIndex(0);
        zoomComboBox.addActionListener(this);
        focusButton.addActionListener(this);

        initializePanels();
        initializeLayout();
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
        buttonPanel.add(focusButton, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == zoomComboBox) {
            mapMakerWindow.setZoomValue((ZoomValue) zoomComboBox.getSelectedItem());
            mapMakerWindow.getMapMakerImagePanel().updateImages();
        } else if (e.getSource() == focusButton) {
            System.out.println(mapMakerWindow.getMapMakerImagePanel().getStoredImage().getWidth() + "  " +
                    mapMakerWindow.getMapMakerImagePanel().getStoredImage().getHeight());

            System.out.println(mapMakerWindow.getMapMakerImagePanel().getDisplayImage().getWidth() + "  " +
                    mapMakerWindow.getMapMakerImagePanel().getDisplayImage().getHeight());

            System.out.println(mapMakerWindow.getMapMakerImagePanel().getScaledDisplayImage().getWidth() + "  " +
                    mapMakerWindow.getMapMakerImagePanel().getScaledDisplayImage().getHeight());
            // TODO: Implement
        }
    }
}
