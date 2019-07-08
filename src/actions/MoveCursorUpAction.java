package actions;

import utils.RectCursor;

import java.awt.event.ActionEvent;

public class MoveCursorUpAction extends MoveCursorAction {

    public MoveCursorUpAction(RectCursor cursor) {
        super(cursor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("up");
    }
}