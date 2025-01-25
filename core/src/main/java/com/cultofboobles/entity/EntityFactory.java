package com.cultofboobles.entity;

public class EntityFactory {


    public static Customer makeCustomer(String id, String targetId, float x, float y) {
        return new Customer(
            id,
            "",
            x,
            y,
            32,
            64,
            targetId
        );
    }

    public static Player makePlayer(float x, float y) {
        return new Player(
            "player",
            "player/player.atlas",
            x,
            y,
            32,
            64
        );
    }

}
