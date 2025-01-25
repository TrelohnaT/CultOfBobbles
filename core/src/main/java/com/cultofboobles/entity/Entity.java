package com.cultofboobles.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public interface Entity {
    String getId();

    float getX();

    float getY();


    List<Sprite> getSpriteList();

    Rectangle getHitBox();

    /**
     * If some obstacle is hit, jump to the before position
     */
    void hitObstacle(String obstacleId);

    void interact(String obstacleId);

    void update();

    void load();

    void clear();
}
