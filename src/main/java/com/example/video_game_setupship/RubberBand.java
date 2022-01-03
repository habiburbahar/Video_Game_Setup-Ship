package com.example.video_game_setupship;

public class RubberBand {
    double left, top, right, bottom;
    double startX, startY;
    double width, height;

    public RubberBand(double x1, double y1) {
        left = x1;
        top = y1;
        startX = x1;
        startY = y1;
    }

    public void resize(double x2, double y2) {
        left = Math.min(x2, startX);
        right = Math.max(x2, startX);
        top = Math.min(y2, startY);
        bottom = Math.max(y2, startY);
        width = right - left;
        height = bottom - top;
    }
}
