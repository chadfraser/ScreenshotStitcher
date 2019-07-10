package actions;

import main.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenImageAction extends OpenAction {
    OpenImageAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openImage();
    }

    private void openImage() {
        String fileName = mainFrame.getSavePanel().getImageFileNameText();
        File imageFile = new File(fileName);

        if (!imageFile.exists()) {
            JFileChooser fileChooser = new JFileChooser(new File("."));
            fileChooser.setDialogTitle("Open Image File");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG images", "png");
            fileChooser.setFileFilter(filter);

            int status = fileChooser.showOpenDialog(mainFrame);
            if (status == JFileChooser.APPROVE_OPTION) {
                imageFile = fileChooser.getSelectedFile();
                mainFrame.getSavePanel().setImageFileNameText(imageFile.getName());
            } else {
                return;
            }
        }

        try {
            BufferedImage openedImage = ImageIO.read(imageFile);
            mainFrame.getImagePanel().getImageHandler().updateAndStoreChangedImages(openedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}