package com.cultofboobles.obstacle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.entity.Entity;
import com.cultofboobles.utils.HitBox;

import java.util.List;
import java.util.Optional;

public interface Obstacle {

    String getId();

    float getX();

    float getY();

    // texture of obstacle
    Sprite getSprite();

    // get hitbox by a type
    HitBox getHitbox(HitBox.types type);

    void interact(Entity entity);


}
