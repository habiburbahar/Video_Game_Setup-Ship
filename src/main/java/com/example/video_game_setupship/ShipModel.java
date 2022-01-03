package com.example.video_game_setupship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShipModel {
    public ArrayList<Groupable> groups;
    ArrayList<ShipModelSubscriber> subscribers;


    public ShipModel() {
        subscribers = new ArrayList<>();
        groups = new ArrayList<>();    }

    public Ship createShip(double x, double y) {
        Ship s = new Ship(x,y);
        groups.add(s);
        notifySubscribers();
        return s;
    }

    public Optional<Groupable> detectHit(double x, double y) {
        return groups.stream().filter(s -> s.contains(x, y)).reduce((first, second) -> second);
    }

    public void moveGroups(List<Groupable> gs, double dx, double dy) {
        gs.forEach(g -> g.move(dx, dy));
        notifySubscribers();
    }

    public void addSubscriber (ShipModelSubscriber aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public List<Groupable> detectHitRubberband(RubberBand rband) {
        return groups.stream()
                .filter(g -> g.isContained(rband.left,rband.top,rband.right,rband.bottom))
                .collect(Collectors.toList());
    }

    public void rotate(List<Groupable> selectionSet, double dAngle) {
        selectionSet.forEach(item -> item.rotate(dAngle));
        notifySubscribers();
    }

    public Groupable group(List<Groupable> selectionSet) {
        ShipGroup sg = new ShipGroup();
        selectionSet.forEach(item -> sg.addItem(item));
        selectionSet.forEach(item -> groups.remove(item));
        groups.add(sg);
        return sg;
    }

    public List<Groupable> ungroup(Groupable oldGroup) {
        ArrayList<Groupable> items = new ArrayList<>();
        if (oldGroup.hasChildren()) {
            // add the children to the model and return the children
            oldGroup.getChildren().forEach(child -> {
                groups.add(child);
                items.add(child);
            });
            groups.remove(oldGroup);
        }
        return items;
    }

    public void addItems(List<Groupable> gs) {
        gs.forEach(g -> groups.add(g));
        notifySubscribers();
    }

    public void deleteGroups(List<Groupable> gs) {
        gs.forEach(g -> deleteGroup(g));
    }

    public void deleteGroup(Groupable g) {
        if (!groups.contains(g)) {
            System.out.println("Tried to delete groupable that was not in the model");
        } else {
            groups.remove(g);
        }
        notifySubscribers();
    }


}

