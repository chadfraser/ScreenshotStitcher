package panels;

import main.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class DataPanel extends JPanel implements MouseListener, PropertyChangeListener {
    private int baseWidth = 512;
    private int baseHeight = 384;
    private int baseOffset = 4;

    private JLabel cropWidthLabel;
    private JLabel cropHeightLabel;
    private JLabel cropXLabel;
    private JLabel cropYLabel;
    private JLabel offsetLabel;
    private JLabel backgroundColorLabel;

    private JFormattedTextField cropXField;
    private JFormattedTextField cropYField;
    private JFormattedTextField cropWidthField;
    private JFormattedTextField cropHeightField;
    private JFormattedTextField offsetField;
    private JFormattedTextField maxDataField;
    private JColorChooser backgroundColorChooser;

    private NumberFormat widthFormat;
    private NumberFormat heightFormat;
    private NumberFormat xFormat;
    private NumberFormat yFormat;
    private NumberFormat offsetFormat;

    private JPanel leftOptionsPanel;
    private JPanel rightOptionsPanel;
    private JPanel backgroundColorChooserPanel;
    private MainFrame mainFrame;

    public DataPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

//        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//        setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//        setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//        setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        backgroundColorChooser = new JColorChooser();
        backgroundColorChooserPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(20, 20);
            }
        };
        backgroundColorChooserPanel.setBackground(mainFrame.getBackgroundColor());

        initializeFormats();
        initializeLabels();
        initializeFields();
        initializePanels();
        initializeLayout();

        setBackground(Color.ORANGE);
    }

    private void initializeLayout() {
        initializeLeftOptionsPanel();
        initializeRightOptionsPanel();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(leftOptionsPanel);
        add(Box.createRigidArea(new Dimension(100, 100)));
        add(rightOptionsPanel);
    }

    private void initializePanels() {
        leftOptionsPanel = new JPanel();
        rightOptionsPanel = new JPanel();
    }

    private void initializeLeftOptionsPanel() {
        leftOptionsPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        int insetHeight = 10;

        c.insets = new Insets(insetHeight, 0, 0, 0);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        leftOptionsPanel.add(cropWidthLabel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        leftOptionsPanel.add(cropXLabel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 4;
        leftOptionsPanel.add(offsetLabel, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        leftOptionsPanel.add(cropWidthField, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        leftOptionsPanel.add(cropXField, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 5;
        leftOptionsPanel.add(offsetField, c);
    }

    private void initializeRightOptionsPanel() {
        rightOptionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        int insetHeight = 10;

        c.insets = new Insets(insetHeight, 0, 0, 0);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        rightOptionsPanel.add(cropHeightLabel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        rightOptionsPanel.add(cropYLabel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 4;
        rightOptionsPanel.add(backgroundColorLabel, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        rightOptionsPanel.add(cropHeightField, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        rightOptionsPanel.add(cropYField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 5;
        rightOptionsPanel.add(backgroundColorChooserPanel, c);
    }

    private void initializeFormats() {
        widthFormat = NumberFormat.getIntegerInstance();
        heightFormat = NumberFormat.getIntegerInstance();
        xFormat = NumberFormat.getIntegerInstance();
        yFormat = NumberFormat.getIntegerInstance();
        offsetFormat = NumberFormat.getIntegerInstance();
    }

    private void initializeLabels() {
        Border blackLineBorder = BorderFactory.createLineBorder(Color.BLACK);
        backgroundColorChooserPanel.setBorder(blackLineBorder);
        backgroundColorChooserPanel.addMouseListener(this);

        cropWidthLabel = new JLabel("CROP WIDTH");
        cropWidthLabel.setLabelFor(cropWidthField);
        cropHeightLabel = new JLabel("CROP HEIGHT");
        cropHeightLabel.setLabelFor(cropHeightField);
        cropXLabel = new JLabel("CROP X-VALUE");
        cropXLabel.setLabelFor(cropXLabel);
        cropYLabel = new JLabel("CROP Y-VALUE");
        cropYLabel.setLabelFor(cropYField);
        offsetLabel = new JLabel("OFFSETS");
        offsetLabel.setLabelFor(offsetField);
        backgroundColorLabel = new JLabel("BACKGROUND");
        backgroundColorLabel.setLabelFor(backgroundColorChooserPanel);
    }

    private void initializeFields() {
        cropWidthField = new JFormattedTextField(widthFormat);
        cropWidthField.setColumns(4);
        cropWidthField.setValue(baseWidth);
        cropWidthField.addPropertyChangeListener("value", this);
        cropWidthField.setHorizontalAlignment(SwingConstants.RIGHT);

        cropHeightField = new JFormattedTextField(heightFormat);
        cropHeightField.setColumns(4);
        cropHeightField.setValue(baseHeight);
        cropHeightField.addPropertyChangeListener("value", this);
        cropHeightField.setHorizontalAlignment(SwingConstants.RIGHT);

        cropXField = new JFormattedTextField(xFormat);
        cropXField.setColumns(4);
        cropXField.setValue(baseWidth);
        cropXField.addPropertyChangeListener("value", this);
        cropXField.setHorizontalAlignment(SwingConstants.RIGHT);

        cropYField = new JFormattedTextField(yFormat);
        cropYField.setColumns(4);
        cropYField.setValue(baseHeight);
        cropYField.addPropertyChangeListener("value", this);
        cropYField.setHorizontalAlignment(SwingConstants.RIGHT);

        offsetField = new JFormattedTextField(offsetFormat);
        offsetField.setColumns(2);
        offsetField.setValue(baseOffset);
        offsetField.addPropertyChangeListener("value", this);
        offsetField.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if (source == cropWidthField) {
            mainFrame.setCropWidth((int) (long) cropWidthField.getValue());
        } else if (source == cropHeightField) {
            mainFrame.setCropHeight((int) (long) cropHeightField.getValue());
        } else if (source == cropXField) {
            mainFrame.setCropX((int) (long) cropXField.getValue());
        } else if (source == cropYField) {
            mainFrame.setCropY((int) (long) cropYField.getValue());
        } else if (source == offsetField) {
            mainFrame.setOffsets((int) (long) offsetField.getValue());
        }
        mainFrame.getImagePanel().updateImages();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == backgroundColorChooserPanel) {
            Color newBackgroundColor = JColorChooser.showDialog(null, "Choose a new background color",
                    mainFrame.getBackgroundColor());
            mainFrame.setBackgroundColor(newBackgroundColor);
            backgroundColorChooserPanel.setBackground(mainFrame.getBackgroundColor());
            mainFrame.getImagePanel().updateImages();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
