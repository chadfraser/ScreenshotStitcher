package actions;

import main.MainFrame;

import java.awt.event.ActionEvent;

public class MoveCursorLeftAction extends MoveCursorAction {

    MoveCursorLeftAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("left");
    }
}