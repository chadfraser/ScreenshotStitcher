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

    private JButton focusOriginButton;
    private JButton focusCursorButton;
    private JLabel zoomComboBoxLabel;
    private JComboBox<ZoomValue> zoomComboBox;

    private MapMakerWindow mapMakerWindow;

    public ZoomPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

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
        zoomComboBox.setSelectedIndex(0);
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

    private void setScrollBarValue(JScrollBar scrollBar, int value) {
        int minimum = scrollBar.getMinimum();
        int visibleAmount = scrollBar.getVisibleAmount();
        value = (value - visibleAmount / 2);
        if (value > minimum) {
            scrollBar.setValue(value);
        } else {
            scrollBar.setValue(minimum);
        }
    }

    private int findCenterOfScaledCursor(int value, double scaleRatio, int cropMeasurement) {
        value = (int) (value * scaleRatio);
        int scaledCropMeasurement = (int) (cropMeasurement * scaleRatio);
        value = (value + scaledCropMeasurement / 2);
        return value;
    }

    public void focusOnCursor() {
        JScrollBar scrollBar;
        int value;
        double[] scaledWidthAndHeight = mapMakerWindow.getMapMakerImagePanel().getScaledWidthAndHeight();

        scrollBar = mapMakerWindow.getMapMakerImageScrollPane().getHorizontalScrollBar();
        value = mapMakerWindow.getMapMakerImagePanel().getCursorX();
        value = findCenterOfScaledCursor(value, scaledWidthAndHeight[0], mapMakerWindow.getCropWidth());
        setScrollBarValue(scrollBar, value);

        scrollBar = mapMakerWindow.getMapMakerImageScrollPane().getVerticalScrollBar();
        value = mapMakerWindow.getMapMakerImagePanel().getCursorY();
        value = findCenterOfScaledCursor(value, scaledWidthAndHeight[1], mapMakerWindow.getCropHeight());
        setScrollBarValue(scrollBar, value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == zoomComboBox) {
            mapMakerWindow.setZoomValue((ZoomValue) zoomComboBox.getSelectedItem());
            mapMakerWindow.getMapMakerImagePanel().updateImages();
            mapMakerWindow.getMapMakerImagePanel().focusOnCursor();
        } else if (e.getSource() == focusOriginButton) {
            JScrollBar scrollBar;
            int value;

            scrollBar = mapMakerWindow.getMapMakerImageScrollPane().getHorizontalScrollBar();
            value = mapMakerWindow.getMapMakerImagePanel().getScaledDisplayImage().getWidth() / 2;
            setScrollBarValue(scrollBar, value);

            scrollBar = mapMakerWindow.getMapMakerImageScrollPane().getVerticalScrollBar();
            value = mapMakerWindow.getMapMakerImagePanel().getScaledDisplayImage().getHeight() / 2;
            setScrollBarValue(scrollBar, value);
        } else if (e.getSource() == focusCursorButton) {
            mapMakerWindow.getMapMakerImagePanel().focusOnCursor();
        }
    }
}
