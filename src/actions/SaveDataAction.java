package actions;

import main.MainFrame;
import serialize.StoredData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveDataAction extends SaveAction {
    SaveDataAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveData();
    }

    private void saveData() {
        String fileName = mainFrame.getSavePanel().getDataFileNameText();
        if (isInvalidFileName(".ser", fileName)) {
            if (mainFrame.getSavedFileName() == null) {
                return;
            }
            fileName = mainFrame.getSavedFileName();
        }
        if (!fileName.endsWith(".ser")) {
            fileName = fileName + ".ser";
        }

        File outputFile = new File(fileName);
        try {
            if (canSaveFileWithoutConfirmation(outputFile)) {
                StoredData.serializeData(mainFrame, fileName);
                // TODO: Replace with apache commons baseName
                mainFrame.setSavedFileName(fileName.substring(0, fileName.length() - 4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean canSaveFileWithoutConfirmation(File outputFile) throws IOException {
        // TODO: Replace with apache commons baseName
        String outputFileName = outputFile.getName().substring(0, outputFile.getName().length() - 4);
        return !outputFile.exists() || outputFileName.equals(mainFrame.getSavedFileName()) ||
                confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION;
    }
}
