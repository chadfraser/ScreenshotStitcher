package handler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveStateList extends ArrayList<ImageState> implements Serializable {
    private static final int MAX_CAPACITY = 10;
    private List<ImageState> imageStates;
    private int currentStateIndex;

    SaveStateList() {
        imageStates = new ArrayList<>();
        currentStateIndex = -1;
    }

    // Returns the ImageState in the image state list one index behind the currentStateIndex, or null if no such state
    // exists
    // Also decrements currentStateIndex to represent that the returned state is the new 'current state'
    ImageState getPreviousState() {
        if (currentStateIndex <= 0 || currentStateIndex >= imageStates.size()) {
            return null;
        }
        currentStateIndex--;
        return imageStates.get(currentStateIndex);
    }


    // Returns the ImageState in the image state list one index ahead of the currentStateIndex, or null if no such
    // state exists
    // Also increments currentStateIndex to represent that the returned state is the new 'current state
    ImageState getNextState() {
        if (currentStateIndex < 0 || currentStateIndex >= imageStates.size() - 1) {
            return null;
        }
        currentStateIndex++;
        return imageStates.get(currentStateIndex);
    }

    // Returns the ImageState in the image state list one index behind the currentStateIndex, or null if no such state
    // exists
    // Does not alter the currentStateIndex, as the returned state is *not* the new 'current state'
    public ImageState pollPreviousState() {
        if (currentStateIndex <= 0 || currentStateIndex >= imageStates.size()) {
            return null;
        }
        return imageStates.get(currentStateIndex - 1);
    }


    // Returns the ImageState in the image state list one index ahead of the currentStateIndex, or null if no such
    // state exists
    // Does not alter the currentStateIndex, as the returned state is *not* the new 'current state'
    public ImageState pollNextState() {
        if (currentStateIndex < 0 || currentStateIndex >= imageStates.size() - 1) {
            return null;
        }
        return imageStates.get(currentStateIndex + 1);
    }

    @Override
    public boolean add(ImageState imageState) {
        // First, delete all elements from the list past the currentImageIndex
        // This way, we prevent discard future save states that would revert the state of the image
        if (currentStateIndex < imageStates.size() - 1 && currentStateIndex >= 0) {
            imageStates = imageStates.subList(0, currentStateIndex + 1);
        }

        // If the list has more elements than MAX_CAPACITY, remove the first element from the list to keep it under the
        // size constraint
        // This ensures that at most the past (n <= 10) states are always stored
        if (imageStates.size() >= MAX_CAPACITY) {
            imageStates.remove(0);
        }
        boolean result = imageStates.add(imageState);
        currentStateIndex = imageStates.size() - 1;
        return result;
    }
}
