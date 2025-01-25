package com.cultofboobles.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.obstacle.Bed;

import java.util.List;

public interface Entity {
    String getId();

    float getX();

    float getY();


    List<Sprite> getSpriteList();

    Sprite getSprite();

    Rectangle getHitBox();

    /**
     * If some obstacle is hit, jump to the before position
     */
    void hitObstacle(String obstacleId);

    void interactBed(Bed bed);

    void update();

    boolean isDoomed();

    void load();

    void clear();
}
