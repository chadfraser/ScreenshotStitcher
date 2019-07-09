package actions;

import main.MainFrame;

import java.awt.event.ActionEvent;

public class MoveCursorRightAction extends MoveCursorAction {

    MoveCursorRightAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("right");
    }
}