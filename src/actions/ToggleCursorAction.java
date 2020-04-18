package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ToggleCursorAction extends AbstractAction {
    private MainFrame mainFrame;

    ToggleCursorAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.getImagePanel().getRectCursor().toggleDisplayCursor();
    }
}
