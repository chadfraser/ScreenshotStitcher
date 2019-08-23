package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public abstract class SaveAction extends AbstractAction {
    MainFrame mainFrame;
    JFileChooser fileChooser;

    SaveAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    boolean isEmptyFileName(String extension, String fileName) {
        return fileName == null || fileName.equals(extension);
    }

    boolean isInvalidFileName(String extension, String fileName) {
        if (fileName.contains(".") && !fileName.endsWith(extension)) {
            return true;
        }
        return false;
    }

    void showEmptyFileNameWarning() {
        JOptionPane.showMessageDialog(mainFrame,
                "No filename is selected to save to.",
                "Filename Missing Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    void showInvalidFileExtensionWarning(String extension) {
        JOptionPane.showMessageDialog(mainFrame,
                "It looks like you are trying to save to a file extension other than " + extension + "\n" +
                        "This program does not support any other file extensions.",
                "Filename Extension Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    int chooseSaveFile() {
        return fileChooser.showSaveDialog(mainFrame);
    }

    int confirmFileOverwrite(String outputFilePath) {
        return JOptionPane.showConfirmDialog(mainFrame,
                "The file " + outputFilePath + " already exists. Overwrite this file?",
                "File Overwrite Confirmation",
                JOptionPane.OK_CANCEL_OPTION);
    }
}