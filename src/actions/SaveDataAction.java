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
        new Thread(saveDataTask);
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
