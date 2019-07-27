package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ActionShortcutHandler extends JComponent {
    private MainFrame mainFrame;

    /*
    Auto save
    Trim (hor/ver)
    Include offsets in trim
    Zoom
    Focus (origin/cursor)
    Manually place cursor
    Manually set crop
    */

    public ActionShortcutHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeActionMap();
    }

    private void initializeActionMap() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                InputEvent.SHIFT_DOWN_MASK), "moveUp");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                InputEvent.SHIFT_DOWN_MASK), "moveDown");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,
                InputEvent.SHIFT_DOWN_MASK), "moveLeft");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,
                InputEvent.SHIFT_DOWN_MASK), "moveRight");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                InputEvent.CTRL_DOWN_MASK), "toggleCursor");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                InputEvent.CTRL_DOWN_MASK), "paste");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                InputEvent.CTRL_DOWN_MASK), "delete");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK), "undo");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                InputEvent.CTRL_DOWN_MASK), "redo");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "redo");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK), "saveImage");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "saveData");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_DOWN_MASK), "openImage");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "openData");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH,
                InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK), "autoSaveCheckbox");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_OPEN_BRACKET,
                InputEvent.CTRL_DOWN_MASK), "trimHorizontally");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_CLOSE_BRACKET,
                InputEvent.CTRL_DOWN_MASK), "trimVertically");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH,
                InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK), "trimCheckbox");

        getActionMap().put("moveUp", mainFrame.getActionHandler().getMoveCursorUp());
        getActionMap().put("moveDown", mainFrame.getActionHandler().getMoveCursorDown());
        getActionMap().put("moveLeft", mainFrame.getActionHandler().getMoveCursorLeft());
        getActionMap().put("moveRight", mainFrame.getActionHandler().getMoveCursorRight());
        getActionMap().put("toggleCursor", mainFrame.getActionHandler().getToggleCursorAction());
        getActionMap().put("paste", mainFrame.getActionHandler().getPasteAction());
        getActionMap().put("delete", mainFrame.getActionHandler().getDeleteAction());
        getActionMap().put("undo", mainFrame.getActionHandler().getUndoAction());
        getActionMap().put("redo", mainFrame.getActionHandler().getRedoAction());
        getActionMap().put("saveImage", mainFrame.getActionHandler().getSaveImageAction());
        getActionMap().put("saveData", mainFrame.getActionHandler().getSaveDataAction());
        getActionMap().put("openImage", mainFrame.getActionHandler().getOpenImageAction());
        getActionMap().put("openData", mainFrame.getActionHandler().getOpenDataAction());
        getActionMap().put("autoSaveCheckbox", mainFrame.getActionHandler().getAutoSaveCheckboxAction());
        getActionMap().put("trimHorizontally", mainFrame.getActionHandler().getTrimHorizontallyAction());
        getActionMap().put("trimVertically", mainFrame.getActionHandler().getTrimVerticallyAction());
        getActionMap().put("trimCheckbox", mainFrame.getActionHandler().getTrimCheckboxAction());
    }
}
