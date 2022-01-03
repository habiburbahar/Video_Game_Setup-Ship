package com.example.video_game_setupship;

import java.util.ArrayList;
import java.util.List;

public class ShipClipboard {
    List<Groupable> items;

    public ShipClipboard() {
        items = new ArrayList<>();
    }

    public void copy(List<Groupable> gs) {
        items.clear();
        gs.forEach(g -> items.add(g.duplicate()));
    }

    public List<Groupable> get() {
        List<Groupable> copy = new ArrayList<Groupable>();
        items.forEach(item -> copy.add(item.duplicate()));
        return copy;
    }

    public String describeClipboard() {
        if (items.size() > 1) {
            return items.size() + " items";
        } else if (items.size() == 1) {
            return "1 item";
        } else {
            return "empty";
        }
    }

}
