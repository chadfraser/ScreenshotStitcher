package actions;

import main.MainFrame;

import java.awt.event.ActionEvent;

public class MoveCursorUpAction extends MoveCursorAction {

    public MoveCursorUpAction(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("up");
    }
}