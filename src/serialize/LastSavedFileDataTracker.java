package serialize;

import handler.SaveStateList;
import main.MainFrame;
import utils.RectCursor;

public class LastSavedFileDataTracker {
    private MainFrame mainFrame;
    private RectCursor lastSavedRectCursor;
    private SaveStateList lastSavedSaveStateList;
    private String lastSavedDataFileName;
    private int lastSavedCropX;
    private int lastSavedCropY;

    public LastSavedFileDataTracker(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        updateLastSavedFileData();
    }

    public void updateLastSavedFileData() {
        lastSavedRectCursor = mainFrame.getImagePanel().getRectCursor();
        lastSavedSaveStateList = mainFrame.getImagePanel().getImageHandler().getSaveStateList();
        lastSavedDataFileName = mainFrame.getSavePanel().getDataFileNameText();
        lastSavedCropX = mainFrame.getCropX();
        lastSavedCropY = mainFrame.getCropY();
    }

    public boolean areUnsavedChanges() {
        RectCursor currentRectCursor = mainFrame.getImagePanel().getRectCursor();
        SaveStateList currentSavedSaveStateList = mainFrame.getImagePanel().getImageHandler().getSaveStateList();
        String currentDataFileName = mainFrame.getSavePanel().getDataFileNameText();
        int currentCropX = mainFrame.getCropX();
        int currentCropY = mainFrame.getCropY();

        if (!currentDataFileName.equals(lastSavedDataFileName) || currentCropX != lastSavedCropX ||
                currentCropY != lastSavedCropY) {
            return true;
        }
        return RectCursor.haveEqualMeasurements(currentRectCursor, lastSavedRectCursor) &&
                SaveStateList.containSameImageStates(currentSavedSaveStateList, lastSavedSaveStateList);
    }

    public RectCursor getLastSavedRectCursor() {
        return lastSavedRectCursor;
    }

    public SaveStateList getLastSavedSaveStateList() {
        return lastSavedSaveStateList;
    }

    public String getLastSavedDataFileName() {
        return lastSavedDataFileName;
    }

    public int getLastSavedCropX() {
        return lastSavedCropX;
    }

    public int getLastSavedCropY() {
        return lastSavedCropY;
    }
}
