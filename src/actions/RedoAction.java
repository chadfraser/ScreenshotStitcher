package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {
    private MainFrame mainFrame;

    RedoAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.getImagePanel().getImageHandler().redo();
    }
}