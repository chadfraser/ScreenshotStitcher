package actions;

import main.MainFrame;
import serialize.StoredData;

import javax.imageio.ImageIO;
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

        try {
            File outputFile = new File(fileName);
            if (!outputFile.exists() || confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION) {
                StoredData.serializeData(mainFrame, fileName);
                mainFrame.setSavedFileName(fileName.substring(0, fileName.length() - 4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
