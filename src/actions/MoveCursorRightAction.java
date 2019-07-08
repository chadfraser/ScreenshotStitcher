package actions;

import utils.RectCursor;

import java.awt.event.ActionEvent;

public class MoveCursorRightAction extends MoveCursorAction {

    public MoveCursorRightAction(RectCursor cursor) {
        super(cursor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("right");
    }
}