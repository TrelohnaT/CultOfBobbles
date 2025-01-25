package com.cultofboobles.obstacle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cultofboobles.Main;

public class ObstacleFactory {

    public static Bed makeBed(String id, float x, float y) {
        return new Bed(
            id,
            new Sprite(
                Main.atlasHandler.obstacle.findRegion("MassageTable")
            ),
            x,
            y
        );
    }


}
