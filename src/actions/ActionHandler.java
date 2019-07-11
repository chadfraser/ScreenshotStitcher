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
    private CheckboxAction autoSaveCheckboxAction;

    private TrimHorizontallyAction trimHorizontallyAction;
    private TrimVerticallyAction trimVerticallyAction;
    private CheckboxAction trimCheckboxAction;

    /*
    Auto save
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
        autoSaveCheckboxAction = new CheckboxAction(mainFrame, mainFrame.getSavePanel().getAutoSaveCheckBox());
        trimHorizontallyAction = new TrimHorizontallyAction(mainFrame);
        trimVerticallyAction = new TrimVerticallyAction(mainFrame);
        trimCheckboxAction = new CheckboxAction(mainFrame, mainFrame.getTrimPanel().getTrimOffsetsCheckBox());
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

    public CheckboxAction getAutoSaveCheckboxAction() {
        return autoSaveCheckboxAction;
    }

    public TrimHorizontallyAction getTrimHorizontallyAction() {
        return trimHorizontallyAction;
    }

    public TrimVerticallyAction getTrimVerticallyAction() {
        return trimVerticallyAction;
    }

    public CheckboxAction getTrimCheckboxAction() {
        return trimCheckboxAction;
    }
}
