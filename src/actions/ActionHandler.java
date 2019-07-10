package actions;

import main.MainFrame;

import javax.swing.*;

public class ActionHandler extends JComponent {
    private MainFrame mainFrame;
    private MoveCursorUpAction moveCursorUp;
    private MoveCursorDownAction moveCursorDown;
    private MoveCursorLeftAction moveCursorLeft;
    private MoveCursorRightAction moveCursorRight;

    private PasteAction pasteAction;
    private DeleteAction deleteAction;
    private UndoAction undoAction;
    private RedoAction redoAction;

    private SaveImageAction saveImageAction;
    private SaveDataAction saveDataAction;
    private OpenImageAction openImageAction;
    private OpenDataAction openDataAction;

    /*
    Open (image/data)
    Auto save
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
        undoAction = new UndoAction(mainFrame);
        redoAction = new RedoAction(mainFrame);
        pasteAction = new PasteAction(mainFrame);
        deleteAction = new DeleteAction(mainFrame);
        saveImageAction = new SaveImageAction(mainFrame);
        saveDataAction = new SaveDataAction(mainFrame);
        openImageAction = new OpenImageAction(mainFrame);
        openDataAction = new OpenDataAction(mainFrame);
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

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public SaveImageAction getSaveImageAction() {
        return saveImageAction;
    }

    public SaveDataAction getSaveDataAction() {
        return saveDataAction;
    }

    public OpenImageAction getOpenImageAction() {
        return openImageAction;
    }

    public OpenDataAction getOpenDataAction() {
        return openDataAction;
    }
}
