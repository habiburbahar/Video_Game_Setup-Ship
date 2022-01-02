module com.example.video_game_setupship {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.video_game_setupship to javafx.fxml;
    exports com.example.video_game_setupship;
}