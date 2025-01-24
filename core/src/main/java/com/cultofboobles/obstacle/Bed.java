package com.cultofboobles.obstacle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.utils.HitBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Bed implements Obstacle{

    private final String id;
    private final float x;
    private final float y;
    private final float sizeX;
    private final float sizeY;

    private final float hitboxOffset = 32;

    Map<HitBox.types, HitBox> hitBoxMap = new HashMap<>();

    public Bed(
        String id,
        float x,
        float y,
        float sizeX,
        float sizeY
    ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        hitBoxMap.put(
            HitBox.types.UnEnterAble,
            new HitBox(
                "",
                HitBox.types.UnEnterAble,
                new Rectangle(x,y,sizeX,sizeY)
            )
        );

        hitBoxMap.put(
            HitBox.types.EnterAble,
            new HitBox(
                "",
                HitBox.types.EnterAble,
                new Rectangle(
                    x-sizeX/2,
                    y-sizeY/2,
                    sizeX+(sizeX),
                    sizeY+(sizeY)
                )
            )
        );

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public HitBox getHitbox(HitBox.types type) {
        return hitBoxMap.get(type);
    }
}
