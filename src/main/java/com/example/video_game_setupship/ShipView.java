package com.example.video_game_setupship;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ShipView  extends StackPane implements ShipModelSubscriber{

    Canvas myCanvas;
    GraphicsContext gc;
    Slider rotateSlider;
    ShipModel model;
    InteractionModel iModel;



    public ShipView(){
        myCanvas = new Canvas(1000,700);
        gc = myCanvas.getGraphicsContext2D();
        this.setStyle("-fx-background-color: black");
        rotateSlider = new Slider(-180,180,0);
        VBox root = new VBox(rotateSlider,myCanvas);
        this.getChildren().add(root);
    }
    public void setModel(ShipModel newModel) {
        model = newModel;
    }
    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
    }
    public void setController(ShipController controller) {
        myCanvas.setOnMousePressed(e -> controller.handlePressed(e.getX(), e.getY(), e));
        myCanvas.setOnMouseDragged(e -> controller.handleDragged(e.getX(), e.getY(), e));
        myCanvas.setOnMouseReleased(e -> controller.handleReleased(e.getX(), e.getY(), e));
        rotateSlider.valueProperty().addListener((observable, oldVal, newVal) ->
                controller.handleRotation(oldVal.doubleValue(),newVal.doubleValue()));

    }
    public void draw() {
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        if (iModel.hasRubberband()) {
            gc.setStroke(Color.WHITE);
            gc.setFill(Color.DARKGOLDENROD);
            gc.fillRect(iModel.rband.left,iModel.rband.top,iModel.rband.width,iModel.rband.height);
            gc.strokeRect(iModel.rband.left,iModel.rband.top,iModel.rband.width,iModel.rband.height);
        }
        model.groups.forEach(group -> {
            if (iModel.isSelected(group)) {
                // bounding box if composite
                if (group.hasChildren()) {
                    gc.setStroke(Color.WHITE);
                    gc.strokeRect(group.getLeft(),group.getTop(),group.getRight()-group.getLeft(),
                            group.getBottom()-group.getTop());
                    gc.strokeOval(group.getCX()-5,group.getCY()-5,10,10);
                }
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.CORAL);
            } else {
                gc.setStroke(Color.YELLOW);
                gc.setFill(Color.CORAL);
            }
            drawGroup(group);
        });
    }

    public void drawGroup(Groupable group) {
        if (group.hasChildren()) {
            group.getChildren().forEach(child -> drawGroup(child));
        } else {
            drawShip((Ship)group);
        }
    }

    public void drawShip(Ship s) {
        gc.fillPolygon(s.displayXs, s.displayYs, s.displayXs.length);
        gc.strokePolygon(s.displayXs, s.displayYs, s.displayXs.length);
    }

    @Override
    public void modelChanged() {
        draw();
    }
}
