package com.cultofboobles.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.Main;
import com.cultofboobles.obstacle.Bed;
import com.cultofboobles.utils.Utils;

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

    private float beforeBedX = 0;
    private float beforeBedY = 0;

    private float timeEnteringBed = 0;

    private visitStates state = visitStates.Entry;

    private happinessStates mood = happinessStates.Neutral;

    private boolean doomed = false;


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
    public void interactBed(Bed bed) {

        if(state.equals(visitStates.Leaving)) {
            return;
        }

        if (targetId.equals(bed.getId())) {
            System.out.println("bed reached: " + bed.getId());
            this.state = visitStates.InBed;

            this.timeEnteringBed = Main.timeElapsed;

            this.beforeBedX = this.x;
            this.beforeBedY = this.y;

            this.x = bed.getX();
            this.y = bed.getY();

        }
    }

    @Override
    public void update() {
        if (state.equals(visitStates.Entry)) {
            if (270 < this.y) {
                this.y -= speed * Gdx.graphics.getDeltaTime();
            } else if (170 < this.x) {
                this.x -= speed * Gdx.graphics.getDeltaTime();
            }
        } else if (state.equals(visitStates.InBed)) {
            if(Main.timeElapsed - this.timeEnteringBed > Utils.getRandom(3, 5)) {
                state = visitStates.Leaving;
                this.x = this.beforeBedX + 5;
                this.y = this.beforeBedY;
            }


        } else if (state.equals(visitStates.Leaving)) {
            if (this.x <= 500) {
                this.x += speed * Gdx.graphics.getDeltaTime();
            } else if (0 < this.y) {
                this.y += speed * Gdx.graphics.getDeltaTime();
            }
        }
        this.hitBox.setPosition(this.x, this.y);

        if (this.y > 480) {
            this.doomed = true;
        }

    }

    @Override
    public boolean isDoomed() {
        return this.doomed;
    }

    @Override
    public void load() {

    }

    @Override
    public void clear() {

    }

    public enum visitStates {
        Entry,
        InBed,
        Leaving
    }

    public enum happinessStates {
        Happy,
        Neutral,
        UnHappy
    }

}
