package com.example.video_game_setupship;

import java.util.ArrayList;

public class ShipModel {
    ArrayList<ShipModelSubscriber> subscribers;
    public ArrayList<Groupable> groups;


    public ShipModel() {
        subscribers = new ArrayList<>();
    }

    public void addSubscriber (ShipModelSubscriber aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }



}

