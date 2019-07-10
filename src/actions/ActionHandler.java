package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ActionHandler extends JComponent {
    private MainFrame mainFrame;
    private MoveCursorUpAction moveCursorUp;
    private MoveCursorDownAction moveCursorDown;
    private MoveCursorLeftAction moveCursorLeft;
    private MoveCursorRightAction moveCursorRight;

    private PasteAction pasteAction;
    private DeleteAction deleteAction;
    private UndoAction undo;
    private RedoAction redo;

    /*
    Save (image/data)
    Open (image/data)
    Auto save
    Paste
    Delete
    Trim (hor/ver)
    Include offsets in trim
    Zoom
    Focus (origin/cursor)
    Manually place cursor
    Manually set crop
    */

    public ActionHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeActions();
    }

    private void initializeActions() {
        initializeCursorActions();
        undo = new UndoAction(mainFrame);
        redo = new RedoAction(mainFrame);
        pasteAction = new PasteAction(mainFrame);
        deleteAction = new DeleteAction(mainFrame);
    }

    private void initializeCursorActions() {
        moveCursorUp = new MoveCursorUpAction(mainFrame);
        moveCursorDown = new MoveCursorDownAction(mainFrame);
        moveCursorLeft = new MoveCursorLeftAction(mainFrame);
        moveCursorRight = new MoveCursorRightAction(mainFrame);
    }

    public MoveCursorUpAction getMoveCursorUp() {
        return moveCursorUp;
    }

    public MoveCursorDownAction getMoveCursorDown() {
        return moveCursorDown;
    }

    public MoveCursorLeftAction getMoveCursorLeft() {
        return moveCursorLeft;
    }

    public MoveCursorRightAction getMoveCursorRight() {
        return moveCursorRight;
    }

    public PasteAction getPasteAction() {
        return pasteAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public UndoAction getUndo() {
        return undo;
    }

    public RedoAction getRedo() {
        return redo;
    }
}
