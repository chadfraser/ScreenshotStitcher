package actions;

import utils.RectCursor;

import java.awt.event.ActionEvent;

public class MoveCursorLeftAction extends MoveCursorAction {

    public MoveCursorLeftAction(RectCursor cursor) {
        super(cursor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCursor("left");
    }
}