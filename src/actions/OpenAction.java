package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class OpenAction extends AbstractAction {
    MainFrame mainFrame;
    JFileChooser fileChooser;

    OpenAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}