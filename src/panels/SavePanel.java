package panels;

import main.MainFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SavePanel extends JPanel implements ActionListener {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 300;

    private JLabel imageFileNameLabel;
    private JTextField imageFileNameField;
    private JButton saveImageButton;
    private JButton openImageButton;
    private JLabel dataFileNameLabel;
    private JTextField dataFileNameField;
    private JButton saveDataButton;
    private JButton openDataButton;
    private JCheckBox autoSaveCheckBox;

    private JPanel saveImagePanel;
    private JPanel saveDataPanel;
    private JPanel checkBoxPanel;

    private String autoSaveFileName;

    private MainFrame mainFrame;

    public SavePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setBackground(Color.RED);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        initializeFieldsAndLabels();
        initializeButtons();
        initializePanels();
        initializeLayout();
        dataFileNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                if (isInvalidFileName(".ser", dataFileNameField.getText())) {
//                    autoSaveCheckBox.setEnabled(false);
//                } else {
//                    autoSaveCheckBox.setEnabled(true);
//                }
            }
        });
    }

    private void initializeFieldsAndLabels() {
        imageFileNameField = new JTextField();
        imageFileNameField.setText(".png");
        imageFileNameLabel= new JLabel("IMAGE FILE NAME");
        imageFileNameLabel.setLabelFor(imageFileNameField);

        dataFileNameField = new JTextField();
        dataFileNameField.setText(".ser");
        dataFileNameLabel = new JLabel("DATA FILE NAME");
        dataFileNameLabel.setLabelFor(dataFileNameField);

        autoSaveCheckBox = new JCheckBox("Autosave on");
    }

    private void initializeButtons() {
        saveImageButton = new JButton("SAVE");
        openImageButton = new JButton("OPEN");
        saveDataButton = new JButton("SAVE");
        openDataButton = new JButton("OPEN");

        saveImageButton.addActionListener(this);
        openImageButton.addActionListener(this);
        saveDataButton.addActionListener(this);
        openDataButton.addActionListener(this);
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.15;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(saveImagePanel, c);

        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(Box.createGlue(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.15;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(saveDataPanel, c);

        c.weightx = 0.5;
        c.weighty = 0.1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(checkBoxPanel, c);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(Box.createGlue(), c);
    }

    private void initializePanels() {
        JComponent[] components;

        saveImagePanel = new JPanel(new GridBagLayout());
        saveDataPanel = new JPanel(new GridBagLayout());
        checkBoxPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        components = new JComponent[] {imageFileNameLabel, imageFileNameField, saveImageButton, openImageButton};
        initializeFilePanel(c, saveImagePanel, components);

        components = new JComponent[] {dataFileNameLabel, dataFileNameField, saveDataButton, openDataButton};
        initializeFilePanel(c, saveDataPanel, components);
        initializeCheckBoxPanel(c);
    }

    private void initializeFilePanel(GridBagConstraints c, JPanel filePanel, JComponent[] components) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        filePanel.add(components[0], c);

        c.weightx = 0.9;
        c.weighty = 0.5;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        filePanel.add(components[1], c);

        c.weightx = 0.1;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 4;
        c.gridy = 1;
        filePanel.add(components[2], c);

        c.weightx = 0.9;
        c.weighty = 0.5;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        filePanel.add(Box.createGlue(), c);

        c.weightx = 0.1;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 4;
        c.gridy = 2;
        filePanel.add(components[3], c);
    }

    private void initializeCheckBoxPanel(GridBagConstraints c) {
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        checkBoxPanel.add(autoSaveCheckBox, c);
        autoSaveCheckBox.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveImageButton) {
            mainFrame.getActionHandler().getSaveImageAction().actionPerformed(e);
        } else if (e.getSource() == saveDataButton) {
            mainFrame.getActionHandler().getSaveDataAction().actionPerformed(e);
        } else if (e.getSource() == openImageButton) {
            mainFrame.getActionHandler().getOpenImageAction().actionPerformed(e);
        } else if (e.getSource() == openDataButton) {
            mainFrame.getActionHandler().getOpenDataAction().actionPerformed(e);
        }
    }

    public String getImageFileNameText() {
        return imageFileNameField.getText();
    }

    public String getDataFileNameText() {
        return dataFileNameField.getText();
    }

    public void setImageFileNameText(String text) {
        imageFileNameField.setText(text);
    }

    public void setDataFileNameText(String text) {
        dataFileNameField.setText(text);
    }

    public JCheckBox getAutoSaveCheckBox() {
        return autoSaveCheckBox;
    }
}

