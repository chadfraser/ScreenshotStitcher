package actions;

import main.MainFrame;

import java.awt.event.ActionEvent;

public class MoveCursorDownAction extends MoveCursorAction {

    public MoveCursorDownAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("down");
    }
}