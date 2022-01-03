package com.example.video_game_setupship;
import java.util.List;
public interface Groupable {
    boolean hasChildren();

    List<Groupable> getChildren();

    boolean contains(double x, double y);

    boolean isContained(double x1, double y1, double x2, double y2);

    void move(double dx, double dy);

    void rotate(double dT);

    void rotate(double dT, double otherCX, double otherCY);

    double getLeft();

    double getTop();

    double getRight();

    double getBottom();

    double getCX();

    double getCY();

    Groupable duplicate();
}
