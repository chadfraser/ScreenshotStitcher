package actions;

import utils.RectCursor;

import java.awt.event.ActionEvent;

public class MoveCursorDownAction extends MoveCursorAction {

    public MoveCursorDownAction(RectCursor cursor) {
        super(cursor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("down");
    }
}