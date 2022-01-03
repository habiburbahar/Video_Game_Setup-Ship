package com.example.video_game_setupship;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    List<Groupable> selectionSet;
    List<ShipModelSubscriber> subscribers;
    RubberBand rband;
    ShipClipboard board;
    public InteractionModel() {
        selectionSet = new ArrayList<>();
        subscribers = new ArrayList<>();
        rband = null;
        board = new ShipClipboard();
    }

    public void clearSelection() {
        selectionSet.clear();
        notifySubscribers();
    }

    public void setSelection(List<Groupable> set) {
        selectionSet = set;
        notifySubscribers();
    }

    public void setSelection(Groupable newSelection) {
        selectionSet.clear();
        addSubtractSelection(newSelection);
        notifySubscribers();
    }

    public boolean isSelected(Groupable g) {
        return selectionSet.contains(g);
    }
    public void addSubscriber(ShipModelSubscriber aSub) {
        subscribers.add(aSub);
    }
    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public void startRubberband(double x, double y) {
        rband = new RubberBand(x, y);
    }
    public boolean hasRubberband() {
        return (rband != null);
    }

    public void deleteRubberband() {
        rband = null;
        notifySubscribers();
    }

    public void resizeRubberband(double x, double y) {
        rband.resize(x, y);
        notifySubscribers();
    }

    public void addSubtractSelection(List<Groupable> set) {
        set.forEach(g -> addSubtractSelection(g));
        notifySubscribers();
    }

    public void addSubtractSelection(Groupable g) {
        if (selectionSet.contains(g)) {
            selectionSet.remove(g);
        } else {
            selectionSet.add(g);
        }
        notifySubscribers();
    }

    public void copyToClipboard() {
        board.copy(selectionSet);
        board.describeClipboard();
        notifySubscribers();
    }

    public List<Groupable> getClipboard() {
        return board.get();
    }

    public String describeClipboard() {
        return board.describeClipboard();
    }


}
