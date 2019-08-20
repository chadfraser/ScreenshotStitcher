package actions;

import main.MainFrame;
import serialize.StoredData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveDataAction extends SaveAction {
    Thread t;

    SaveDataAction(MainFrame mainFrame) {
        super(mainFrame);
        t = new Thread(saveDataTask);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        t.start();
        saveData();
    }

    private void saveData() {
        String fileName = mainFrame.getSavePanel().getDataFileNameText();
        if (isInvalidFileName(".ser", fileName)) {
            return;
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

    private final Runnable saveDataTask = () -> {
        String fileName = mainFrame.getSavePanel().getDataFileNameText();
        if (isInvalidFileName(".ser", fileName)) {
            return;
        }
        if (!fileName.endsWith(".ser")) {
            fileName = fileName + ".ser";
        }

        MainFrame.LOCK.lock();
        try {
            File outputFile = new File(fileName);
            if (!outputFile.exists() || confirmFileOverwrite(outputFile.getCanonicalPath()) == JOptionPane.OK_OPTION) {
                StoredData.serializeData(mainFrame, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            MainFrame.LOCK.unlock();
        }
    };
}
