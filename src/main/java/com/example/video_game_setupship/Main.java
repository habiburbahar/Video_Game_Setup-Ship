package com.example.video_game_setupship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ShipView view = new ShipView();
        ShipController controller = new ShipController();
        ShipModel model = new ShipModel();
        InteractionModel iModel = new InteractionModel();
        view.setModel(model);
        view.setController(controller);
        view.setInteractionModel(iModel);
        controller.setModel(model);
        controller.setInteractionModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);
        StackPane root = new StackPane(view);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(controller::handleKeyPressed);

    }

    public static void main(String[] args) {
        launch();
    }
}