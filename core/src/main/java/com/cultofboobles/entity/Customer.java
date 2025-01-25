package com.cultofboobles.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class Customer implements Entity {

    private final String id;
    private final String atlasPath;
    private float x;
    private float y;
    private final float sizeX;
    private final float sizeY;
    private final Rectangle hitBox;

    private final float speed = 100;

    private String targetId;

    private float targetX = 170;
    private float targetY = 270;

    public Customer(
        String id,
        String atlasPath,
        float x,
        float y,
        float sizeX,
        float sizeY,
        String targetId
    ) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.targetId = targetId;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.hitBox = new Rectangle(x - this.sizeX / 2, y - this.sizeY / 2, this.sizeX, this.sizeY);


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
    public List<Sprite> getSpriteList() {
        return List.of();
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    @Override
    public void hitObstacle(String obstacleId) {

    }

    @Override
    public void interact(String obstacleId) {
        if (targetId.equals(obstacleId)) {
            System.out.println("bed reached");
        }
    }

    @Override
    public void update() {
//
//        if (this.y < targetY) {
//            this.y += speed * Gdx.graphics.getDeltaTime();
//        } else
        if (targetY < this.y) {
            this.y -= speed * Gdx.graphics.getDeltaTime();
        } else if (this.x < targetX) {
            this.x += speed * Gdx.graphics.getDeltaTime();
        } else if (targetX < this.x) {
            this.x -= speed * Gdx.graphics.getDeltaTime();
        }

        this.hitBox.setPosition(this.x, this.y);
    }

    @Override
    public void load() {

    }

    @Override
    public void clear() {

    }
}
