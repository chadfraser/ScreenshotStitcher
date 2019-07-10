package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class SaveAction extends AbstractAction {
    MainFrame mainFrame;

    SaveAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    boolean isInvalidFileName(String extension, String fileName) {
        if (fileName == null || fileName.equals(extension)) {
            JOptionPane.showMessageDialog(mainFrame,
                    "No filename is selected to save to.",
                    "Filename Missing Warning",
                    JOptionPane.WARNING_MESSAGE);
            return true;
        } else if (fileName.contains(".") && !fileName.endsWith(extension)) {
            JOptionPane.showMessageDialog(mainFrame,
                    "It looks like you are trying to save to a file extension other than " + extension + "\n" +
                            "This program does not support any other file extensions.",
                    "Filename Extension Warning",
                    JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    int confirmFileOverwrite(String outputFilePath) {
        return JOptionPane.showConfirmDialog(mainFrame,
                "The file " + outputFilePath + " already exists. Overwrite this file?",
                "File Overwrite Confirmation",
                JOptionPane.OK_CANCEL_OPTION);
    }
}