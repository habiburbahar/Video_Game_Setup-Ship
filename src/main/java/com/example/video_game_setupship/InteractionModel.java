package com.example.video_game_setupship;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    List<ShipModelSubscriber> subscribers;

    public InteractionModel() {
        subscribers = new ArrayList<>();

    }

    public void addSubscriber(ShipModelSubscriber aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }



}
