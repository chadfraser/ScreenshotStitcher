package actions;

import main.MainFrame;
import serialize.StoredData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class OpenDataAction extends OpenAction {
    OpenDataAction(MainFrame mainFrame) {
        super(mainFrame);
        fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Open Screenshot Stitcher Data File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SER data files", "ser");
        fileChooser.setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openData();
    }

    private void openData() {
        String fileName = mainFrame.getSavePanel().getDataFileNameText();
        File dataFile = new File(fileName);

        if (!dataFile.exists()) {

            int status = fileChooser.showOpenDialog(mainFrame);
            if (status == JFileChooser.APPROVE_OPTION) {
                dataFile = fileChooser.getSelectedFile();
                mainFrame.getSavePanel().setDataFileNameText(dataFile.getName());
            } else {
                return;
            }
        }

        if (confirmDataOverwrite() == JOptionPane.OK_OPTION) {
            StoredData.deserializeData(mainFrame, dataFile);
        }
    }

    private int confirmDataOverwrite() {
        return JOptionPane.showConfirmDialog(mainFrame,
            "You are trying to open up stored data. This will permanently delete any unsaved progress in the " +
                    "current session! Are you sure you wish to do this?\n" +
                    "You may wish to cancel this and save your current image first, so you can reopen it later.",
            "Data Overwrite Confirmation",
            JOptionPane.OK_CANCEL_OPTION);
    }
}