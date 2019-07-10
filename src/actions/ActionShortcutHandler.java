package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ActionShortcutHandler extends JComponent {
    private MainFrame mainFrame;

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

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK), "undo");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                InputEvent.CTRL_DOWN_MASK), "redo");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "redo");

        getActionMap().put("moveUp", mainFrame.getActionHandler().getMoveCursorUp());
        getActionMap().put("moveDown", mainFrame.getActionHandler().getMoveCursorDown());
        getActionMap().put("moveLeft", mainFrame.getActionHandler().getMoveCursorLeft());
        getActionMap().put("moveRight", mainFrame.getActionHandler().getMoveCursorRight());
        getActionMap().put("undo", mainFrame.getActionHandler().getUndo());
        getActionMap().put("redo", mainFrame.getActionHandler().getRedo());
    }
}
