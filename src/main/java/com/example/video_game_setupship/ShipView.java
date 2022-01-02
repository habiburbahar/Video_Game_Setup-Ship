package com.example.video_game_setupship;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ShipView  extends StackPane {

    Canvas myCanvas;
    GraphicsContext gc;
    Slider rotateSlider;

    public ShipView(){
        myCanvas = new Canvas(1000,700);
        gc = myCanvas.getGraphicsContext2D();
        this.setStyle("-fx-background-color: black");
        rotateSlider = new Slider(-180,180,0);
        VBox root = new VBox(rotateSlider,myCanvas);
        this.getChildren().add(root);
    }






}
