import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private JCheckBox autoSaveCheckbox;

    private JPanel saveImagePanel;
    private JPanel saveDataPanel;
    private JPanel radioButtonPanel;

    private MapMakerWindow mapMakerWindow;

    public SavePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setBackground(Color.RED);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        imageFileNameField = new JTextField();
        imageFileNameField.setText(".png");
        imageFileNameLabel= new JLabel("IMAGE FILE NAME");
        imageFileNameLabel.setLabelFor(imageFileNameField);
        saveImageButton = new JButton("SAVE");
        openImageButton = new JButton("OPEN");

        dataFileNameField = new JTextField();
        dataFileNameField.setText(".ser");
        dataFileNameLabel = new JLabel("DATA FILE NAME");
        dataFileNameLabel.setLabelFor(dataFileNameField);
        saveDataButton = new JButton("SAVE");
        openDataButton = new JButton("OPEN");

        saveImageButton.addActionListener(this);
        openImageButton.addActionListener(this);
        saveDataButton.addActionListener(this);
        openDataButton.addActionListener(this);

        autoSaveCheckbox = new JCheckBox("Turn autosave on");

        saveImagePanel = new JPanel(new GridBagLayout());
        saveDataPanel = new JPanel(new GridBagLayout());
        radioButtonPanel = new JPanel(new GridBagLayout());

        initializePanels();
        initializeLayout();
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
        add(radioButtonPanel, c);

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
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        components = new JComponent[] {imageFileNameLabel, imageFileNameField, saveImageButton, openImageButton};
        initializeFilePanel(c, saveImagePanel, components);

        components = new JComponent[] {dataFileNameLabel, dataFileNameField, saveDataButton, openDataButton};
        initializeFilePanel(c, saveDataPanel, components);
        initializeCheckBoxPanel(c);
    }

    private void saveImage() {
        String fileName = imageFileNameField.getText();
        if (!isFileNameValid(".png", fileName)) {
            return;
        }
        fileName = fileName.replace(".png", "");

        try {
            File outputFile = new File(fileName);
            if (!outputFile.exists() || confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION) {
                ImageIO.write(mapMakerWindow.getMapMakerImagePanel().getStoredImage(), "png", outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        String fileName = dataFileNameField.getText();
        if (!isFileNameValid(".ser", fileName)) {
            return;
        }
        fileName = fileName.replace(".ser", "");

        try {
            File outputFile = new File(fileName);
            if (!outputFile.exists() || confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION) {
                StoredData.serializeData(mapMakerWindow, fileName + ".ser");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isFileNameValid(String extension, String fileName) {
        if (fileName == null || fileName.equals(extension)) {
            JOptionPane.showMessageDialog(mapMakerWindow,
                    "No filename is selected to save to.",
                    "Filename Missing Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (fileName.contains(".") && !fileName.endsWith(extension)) {
                JOptionPane.showMessageDialog(mapMakerWindow,
                        "It looks like you are trying to save to a file extension other than " + extension + "\n" +
                                "This program does not support any other file extensions.",
                        "Filename Extension Warning",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        return true;
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Image File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG images", "png");
        fileChooser.setFileFilter(filter);

        int status = fileChooser.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imageFileNameField.setText(selectedFile.getName());
            try {
                BufferedImage openedImage = ImageIO.read(selectedFile);
                mapMakerWindow.getMapMakerImagePanel().setStoredImage(openedImage);
                mapMakerWindow.getMapMakerImagePanel().updateImages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open MapMaker File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SER images", "ser");
        fileChooser.setFileFilter(filter);

        int status = fileChooser.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION && confirmDataOverwrite() == JOptionPane.OK_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            dataFileNameField.setText(selectedFile.getName());
            StoredData.deserializeData(mapMakerWindow, selectedFile);
        }
    }

    private int confirmFileOverwrite(String outputFilePath) {
        return JOptionPane.showConfirmDialog(mapMakerWindow,
                "The file " + outputFilePath + " already exists. Overwrite this file?",
                "File Overwrite Confirmation",
                JOptionPane.OK_CANCEL_OPTION);
    }

    private int confirmDataOverwrite() {
        return JOptionPane.showConfirmDialog(mapMakerWindow,
                "You are trying to open up stored data. This will permanently delete any unsaved progress in the " +
                        "current session! Are you sure you wish to do this?\n" +
                "You may wish to save your current image first, so you can reopen it later.",
                "Data Overwrite Confirmation",
                JOptionPane.OK_CANCEL_OPTION);
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
        radioButtonPanel.add(autoSaveCheckbox, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveImageButton) {
            saveImage();
        } else if (e.getSource() == saveDataButton) {
            saveData();
        } else if (e.getSource() == openImageButton) {
            openImage();
        } else if (e.getSource() == openDataButton) {
            openData();
        }
    }
}
