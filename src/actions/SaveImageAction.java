package actions;

import main.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveImageAction extends SaveAction {
    SaveImageAction(MainFrame mainFrame) {
        super(mainFrame);
        fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Save Image File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG images", "png");
        fileChooser.setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveImage();
    }

    // TODO: Split
    private void saveImage() {
        String fileName = mainFrame.getSavePanel().getImageFileNameText();

        if (isEmptyFileName(".png", fileName)) {
            if (chooseSaveFile() != JFileChooser.APPROVE_OPTION) {
                return;
            }
            fileName = fileChooser.getSelectedFile().getName();
        } else if (isInvalidFileName(".png", fileName)) {
            showInvalidFileExtensionWarning(".ser");
            return;
        }

        if (!fileName.endsWith(".png")) {
            fileName = fileName + ".png";
        }

        File outputFile = new File(fileName);
        MainFrame.LOCK.lock();
        try {
            if (!outputFile.exists() || confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION) {
                ImageIO.write(mainFrame.getMainStoredImage(), "png", outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            MainFrame.LOCK.unlock();
        }
    }
}