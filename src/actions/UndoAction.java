package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {
    private MainFrame mainFrame;

    UndoAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.getImagePanel().getImageHandler().undo();
    }
}