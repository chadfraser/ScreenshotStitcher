import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SavePanel extends JPanel {
    private MapMakerWindow mapMakerWindow;

    private JTextField fileNameField;
    private JLabel fileNameLabel;
    private JButton saveButton;

    public SavePanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setBackground(Color.ORANGE);
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        fileNameField = new JTextField(40);
        fileNameField.setText(".png");
        fileNameLabel = new JLabel("FILE NAME");
        fileNameLabel.setLabelFor(fileNameField);
        saveButton = new JButton("SAVE");

        JFileChooser fileChooser = new JFileChooser();
//        int status = fileChooser.showSaveDialog(this);
//        if (status == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
//            fileNameField.setText(selectedFile.getName());
//            try {
//                String fileName = selectedFile.getCanonicalPath();
//                if (!fileName.endsWith(".png")) {
//                    selectedFile = new File(fileName + ".png");
//                }
//                ImageIO.write(mapMakerWindow.getMapMakerImagePanel().getBufferedImage(), "png", selectedFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
     }
}

