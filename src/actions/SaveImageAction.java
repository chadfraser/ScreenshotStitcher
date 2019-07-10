package actions;

import main.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveImageAction extends SaveAction {
    SaveImageAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveImage();
    }

    private void saveImage() {
        String fileName = mainFrame.getSavePanel().getImageFileNameText();
        if (isInvalidFileName(".png", fileName)) {
            return;
        }
        if (!fileName.endsWith(".png")) {
            fileName = fileName + ".png";
        }

        try {
            File outputFile = new File(fileName);
            if (!outputFile.exists() || confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION) {
                ImageIO.write(mainFrame.getMainStoredImage(), "png", outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}