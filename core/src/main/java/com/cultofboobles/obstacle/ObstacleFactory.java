package com.cultofboobles.obstacle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ObstacleFactory {

    public static Bed makeBed(String id, float x, float y) {
        TextureAtlas tmp = new TextureAtlas(
            "obstacles/obstacles.atlas");
        return new Bed(
            id,
            new Sprite(
                tmp.findRegion("MassageTable")
            ),
            x,
            y
        );
    }


}
