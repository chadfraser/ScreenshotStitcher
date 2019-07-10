package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteAction extends AbstractAction {
    private MainFrame mainFrame;

    DeleteAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.getImagePanel().getImageHandler().deleteFromImage();
    }
}