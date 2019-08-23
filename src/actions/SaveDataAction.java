package actions;

import main.MainFrame;
import serialize.StoredData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveDataAction extends SaveAction {
    SaveDataAction(MainFrame mainFrame) {
        super(mainFrame);
        fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Save Screenshot Stitcher Data File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SER data files", "ser");
        fileChooser.setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveData();
    }

    // TODO: Split
    private void saveData() {
        String fileName = mainFrame.getSavePanel().getDataFileNameText();

        if (mainFrame.getSavedFileName() != null &&
                (isEmptyFileName(".ser", fileName) || isInvalidFileName(".ser", fileName))) {
            fileName = mainFrame.getSavedFileName();
        } else if (isEmptyFileName(".ser", fileName)) {
            if (chooseSaveFile() != JFileChooser.APPROVE_OPTION) {
                return;
            }
            fileName = fileChooser.getSelectedFile().getName();
        } else if (isInvalidFileName(".ser", fileName)) {
            showInvalidFileExtensionWarning(".ser");
            return;
        }

        if (!fileName.endsWith(".ser")) {
            fileName = fileName + ".ser";
        }

        File outputFile = new File(fileName);
        MainFrame.LOCK.lock();
        try {
            if (canSaveFileWithoutConfirmation(outputFile)) {
                StoredData.serializeData(mainFrame, fileName);
                // TODO: Replace with apache commons baseName
                mainFrame.setSavedFileName(fileName.substring(0, fileName.length() - 4));
                mainFrame.getLastSavedFileDataTracker().updateLastSavedFileData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            MainFrame.LOCK.unlock();
        }
    }

    private boolean canSaveFileWithoutConfirmation(File outputFile) throws IOException {
        // TODO: Replace with apache commons baseName
        String outputFileName = outputFile.getName().substring(0, outputFile.getName().length() - 4);
        return !outputFile.exists() || outputFileName.equals(mainFrame.getSavedFileName()) ||
                confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION;
    }
}
