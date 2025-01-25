package com.cultofboobles.obstacle;

public class ObstacleFactory {

    public static Bed makeBed(String id, float x, float y) {
        return new Bed(
            id,
            x,
            y,
            64,
            128
        );
    }


}
