package com.example.video_game_setupship;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ShipController {
    InteractionModel iModel;
    ShipModel model;

    public void handleKeyPressed(KeyEvent keyEvent) {

    }

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

    public void handlePressed(double x, double y, MouseEvent e) {

    }

    public void handleDragged(double x, double y, MouseEvent e) {

    }

    public void handleReleased(double x, double y, MouseEvent e) {
    }

    public void handleRotation(double oldVal, double newVal) {
    }

}
