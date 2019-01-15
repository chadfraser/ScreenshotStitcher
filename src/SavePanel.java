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
    private static final int HEIGHT = 450;

    private JLabel fileNameLabel;
    private JTextField fileNameField;
    private JButton saveImageButton;
    private JButton saveDataButton;
    private JButton openImageButton;
    private JButton openDataButton;
    private JLabel autoSaveLabel;
    private ButtonGroup autoSaveButtonGroup;
    private JRadioButton autoSaveOnOption;
    private JRadioButton autoSaveOffOption;

    private JPanel fileNamePanel;
    private JPanel radioButtonPanel;

    private MapMakerWindow mapMakerWindow;

    public SavePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setBackground(Color.RED);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        fileNameField = new JTextField();
        fileNameField.setText(".png");
        fileNameLabel = new JLabel("FILE NAME");
        fileNameLabel.setLabelFor(fileNameField);
        saveImageButton = new JButton("SAVE");
        openImageButton = new JButton("OPEN");

        saveImageButton.addActionListener(this);
        openImageButton.addActionListener(this);

        autoSaveOnOption = new JRadioButton("Turn autosave on");
        autoSaveOffOption = new JRadioButton("Turn autosave off");

        autoSaveLabel = new JLabel("<html>Automatically store your progress as you work</html>");
        autoSaveButtonGroup = new ButtonGroup();
        autoSaveButtonGroup.add(autoSaveOnOption);
        autoSaveButtonGroup.add(autoSaveOffOption);

        fileNamePanel = new JPanel(new GridBagLayout());
        radioButtonPanel = new JPanel(new GridBagLayout());

        initializePanels();
        initializeLayout();
    }


    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(fileNamePanel, c);

        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(radioButtonPanel, c);

        c.weightx = 0.5;
        c.weighty = 0.6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(Box.createGlue(), c);
    }

    private void initializePanels() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        initializeFileNamePanel(c);
        initializeRadioButtonPanel(c);

    }

    private void saveImage() {
        String fileName = fileNameField.getText();
        if (!checkFileNameFormat(".png")) {
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
        String fileName = fileNameField.getText();
        if (!checkFileNameFormat(".ser")) {
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

    private boolean checkFileNameFormat(String extension) {
        String fileName = fileNameField.getText();
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
            fileNameField.setText(selectedFile.getName());
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
            fileNameField.setText(selectedFile.getName());
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

    private void initializeFileNamePanel(GridBagConstraints c) {
         c.weightx = 0.5;
         c.weighty = 0.5;
         c.gridwidth = 1;
         c.gridheight = 1;
         c.gridx = 0;
         c.gridy = 0;
         fileNamePanel.add(fileNameLabel, c);

         c.weightx = 0.9;
         c.weighty = 0.5;
         c.gridwidth = 4;
         c.gridheight = 1;
         c.gridx = 0;
         c.gridy = 1;
         fileNamePanel.add(fileNameField, c);

         c.weightx = 0.1;
         c.weighty = 0.5;
         c.gridwidth = 1;
         c.gridheight = 1;
         c.gridx = 4;
         c.gridy = 1;
         fileNamePanel.add(saveImageButton, c);

         c.weightx = 0.9;
         c.weighty = 0.5;
         c.gridwidth = 4;
         c.gridheight = 1;
         c.gridx = 0;
         c.gridy = 2;
         fileNamePanel.add(Box.createGlue(), c);

         c.weightx = 0.1;
         c.weighty = 0.5;
         c.gridwidth = 1;
         c.gridheight = 1;
         c.gridx = 4;
         c.gridy = 2;
         fileNamePanel.add(openImageButton, c);
     }

    private void initializeRadioButtonPanel(GridBagConstraints c) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        radioButtonPanel.add(autoSaveLabel, c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        radioButtonPanel.add(autoSaveOnOption, c);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        radioButtonPanel.add(autoSaveOffOption, c);
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
