package com.example.video_game_setupship;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;

public class ShipController {
    InteractionModel iModel;
    ShipModel model;
    double prevX, prevY;
    double dX, dY;



    protected enum State {
        READY,
        DRAGGING,
        BACKGROUND,
        RUBBERBAND
    }
    protected State currentState;

    public ShipController() {
        currentState = State.READY;
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }
    public void setModel(ShipModel newModel) {
        model = newModel;
    }


    public void handlePressed(double x, double y, MouseEvent event) {

        prevX = x;
        prevY = y;
        switch (currentState) {
            case READY -> {
                // context: on a ship?
                Optional<Groupable> hit = model.detectHit(x, y);
                if (hit.isPresent()) {
                    // on ship
                    // is Control pressed?
                    if (event.isControlDown()) {
                        iModel.addSubtractSelection(hit.get());
                        currentState = State.DRAGGING;
                    } else {
                        // Control not pressed, so we do different things
                        // depending on whether the object was previously selected
                        if (iModel.isSelected(hit.get())) {
                            // item was already selected, so keep current selection
                            currentState = State.DRAGGING;
                        } else {
                            // was not selected already, so select only this ship
                            iModel.setSelection(hit.get());
                            currentState = State.DRAGGING;
                        }
                    }
                } else {
                    // on background - is Shift down?
                    if (event.isShiftDown()) {
                        // create ship
                        Ship newShip = model.createShip(x, y);
                        iModel.setSelection(newShip);
                        currentState = State.DRAGGING;
                    } else {
                        // move to background state and wait for either a move or a release
                        iModel.startRubberband(x, y);
                        // clear previous selection if Control is not pressed
                        if (!event.isControlDown()) {
                            iModel.clearSelection();
                        }
                        currentState = State.BACKGROUND;

                    }
                }
            }
        }

    }

    public void handleDragged(double x, double y, MouseEvent e) {
        dX = x - prevX;
        dY = y - prevY;
        prevX = x;
        prevY = y;
        switch (currentState) {
            case DRAGGING -> model.moveGroups(iModel.selectionSet, dX, dY);
            case BACKGROUND -> {
                currentState = State.RUBBERBAND;
                iModel.resizeRubberband(x, y);
            }
            case RUBBERBAND -> iModel.resizeRubberband(x, y);
        }

    }

    public void handleReleased(double x, double y, MouseEvent e) {
        switch (currentState) {
            case DRAGGING -> {
                currentState = State.READY;
            }
            case BACKGROUND -> {
                // we saw down and up, so clear selection
                iModel.clearSelection();
                currentState = State.READY;
            }
            case RUBBERBAND -> {
                // figure out selection
                List<Groupable> insideRubberband = model.detectHitRubberband(iModel.rband);
                iModel.addSubtractSelection(insideRubberband);

                iModel.deleteRubberband();
                currentState = State.READY;
            }
        }
    }

    public void handleRotation(double oldVal, double newVal) {
        model.rotate(iModel.selectionSet, newVal - oldVal);

    }

    public void handleKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {
            case G -> {
                System.out.println("G");
                if (iModel.selectionSet.size() > 0) {
                    Groupable newGroup = model.group(iModel.selectionSet);
                    iModel.setSelection(newGroup);
                }
            }
            case U -> {
                System.out.println("U");
                if (iModel.selectionSet.size() == 1 && iModel.selectionSet.get(0).hasChildren()) {
                    List<Groupable> items = model.ungroup(iModel.selectionSet.get(0));
                    //iModel.clearSelection();
                    iModel.setSelection(items);
                }
            }
            case C -> {
                if (keyEvent.isControlDown()) {
                    System.out.println("Copy");
                    iModel.copyToClipboard();
                }
            }
            case V -> {
                if (keyEvent.isControlDown()) {
                    System.out.println("Paste");
                    List<Groupable> pasteSet = iModel.getClipboard();
                    model.addItems(pasteSet);
                    iModel.setSelection(pasteSet);
                }
            }
            case X -> {
                if (keyEvent.isControlDown()) {
                    System.out.println("Cut");
                    iModel.copyToClipboard();
                    model.deleteGroups(iModel.selectionSet);
                    iModel.clearSelection();
                }
            }
        }

    }


}
