package com.example.video_game_setupship;

import java.util.ArrayList;
import java.util.List;

public class ShipGroup implements Groupable {
    ArrayList<Groupable> items;
    double left, top, right, bottom;
    double centreX, centreY;

    public ShipGroup() {
        items = new ArrayList<>();
    }

    public boolean hasChildren() {
        return true;
    }

    public boolean contains(double x, double y) {
        return items.stream().anyMatch(g -> g.contains(x, y));
    }

    @Override
    public boolean isContained(double x1, double y1, double x2, double y2) {
        return items.stream().allMatch(g -> g.isContained(x1, y1, x2, y2));
    }

    public void rotate(double dT) {
        items.forEach(item -> item.rotate(dT, centreX, centreY));
        recalculateBounds();
    }

    public void rotate(double dT, double otherCX, double otherCY) {
        items.forEach(item -> item.rotate(dT, otherCX, otherCY));
        recalculateBounds();
    }

    protected void recalculateBounds() {
        left = items.stream().mapToDouble(Groupable::getLeft).min().getAsDouble();
        right = items.stream().mapToDouble(Groupable::getRight).max().getAsDouble();
        top = items.stream().mapToDouble(Groupable::getTop).min().getAsDouble();
        bottom = items.stream().mapToDouble(Groupable::getBottom).max().getAsDouble();
    }

    protected void recalculateCentre() {
        centreX = (left + right) / 2;
        centreY = (top + bottom) / 2;
    }

    public void move(double dx, double dy) {
        items.forEach(g -> g.move(dx, dy));
        recalculateBounds();
        recalculateCentre();
    }

    @Override
    public double getLeft() {
        return left;
    }

    @Override
    public double getTop() {
        return top;
    }

    @Override
    public double getRight() {
        return right;
    }

    @Override
    public double getBottom() {
        return bottom;
    }

    @Override
    public double getCX() {
        return centreX;
    }

    @Override
    public double getCY() {
        return centreY;
    }

    public List<Groupable> getChildren() {
        return items;
    }

    public void addItem(Groupable xs) {
        items.add(xs);
        recalculateBounds();
        recalculateCentre();
    }

    public Groupable duplicate() {
        ShipGroup dupe = new ShipGroup();
        items.forEach(item -> dupe.addItem(item.duplicate()));
        dupe.recalculateBounds();
        dupe.recalculateCentre();
        return dupe;
    }
}
