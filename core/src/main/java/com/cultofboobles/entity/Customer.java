package com.cultofboobles.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.Main;
import com.cultofboobles.obstacle.Bed;
import com.cultofboobles.utils.AtlasHandler;
import com.cultofboobles.utils.Ecomonics;
import com.cultofboobles.utils.Utils;

import java.util.List;
import java.util.Optional;

public class Customer implements Entity {

    private final String id;
    private float x;
    private float y;
    private final float sizeX;
    private final float sizeY;
    private final Rectangle hitBox;

    private final float speed = 100;

    private String targetId;
    private Bed bed;

    private float beforeBedX = 0;
    private float beforeBedY = 0;

    private float timeEnteringBed = 0;

    private visitStates state = visitStates.Entry;

    private happinessStates mood = happinessStates.Neutral;

    private boolean doomed = false;

    private float cleaningProgress = 0;

    public Customer(
        String id,
        float x,
        float y,
        float sizeX,
        float sizeY,
        String targetId
    ) {
        this.id = id;
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
        Sprite tmp;
        if (state.equals(visitStates.Leaving)) {
            tmp = new Sprite(AtlasHandler.customer.findRegion("CustomerGinger_WalkBack"));;
        } else if (state.equals(visitStates.InBed)) {
            tmp = new Sprite(AtlasHandler.customer.findRegion("CustomerGinger_Lying"));;
        } else {
            tmp = new Sprite(AtlasHandler.customer.findRegion("CustomerGinger_WalkFront"));;
        }

        tmp.translateX(this.x);// - tmp.getWidth() / 2);
        tmp.translateY(this.y);// - tmp.getHeight() / 2);

        return tmp;

    }

    public Sprite getMoodIcon() {
        Sprite tmp;
        if(mood.equals(happinessStates.Happy)) {
            tmp = new Sprite(AtlasHandler.customer.findRegion("SmileyFace"));
            tmp.translateX(this.x + 8);
            tmp.translateY(this.y  + 55);

        } else if(mood.equals(happinessStates.UnHappy)) {
            tmp = new Sprite(AtlasHandler.customer.findRegion("FrownFace"));
            tmp.translateX(this.x + 8);
            tmp.translateY(this.y  + 55);

        } else {
            tmp = null;
        }

        return tmp;
    }

    @Override
    public Optional<ToolTypeData> getToolType() {
        return Optional.empty();
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    @Override
    public void hitObstacle(String obstacleId) {

    }

    @Override
    public boolean interactBed(Bed bed) {
        if (state.equals(visitStates.Leaving)) {
            return false;
        }

        if (targetId.equals(bed.getId())) {
            this.bed = bed;
            this.state = visitStates.InBed;
            this.bed.setEmpty(false);
            this.timeEnteringBed = Main.timeElapsed;

            this.beforeBedX = this.x;
            this.beforeBedY = this.y;

            this.x = bed.getX();
            this.y = bed.getY();
            return true;
        }

        return false;
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
            if (Main.timeElapsed - this.timeEnteringBed > Utils.getRandom(5, 15)) {
                state = visitStates.Leaving;
                this.bed.setEmpty(true);
                this.bed.setFree(true);
                this.x = this.beforeBedX + 5;
                this.y = this.beforeBedY;

                if(amICleaned()) {
                    this.mood = happinessStates.Happy;
                        Main.ecomonics.addToMoney(10);
                        Main.ecomonics.addSoap(-5);

                    System.out.println(this.id + " is happy");
                } else {
                    this.mood = happinessStates.UnHappy;
                    System.out.println(this.id + " is unhappy");
                }

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

    public boolean increaseCleanProgress() {

        if((this.cleaningProgress + 0.5f) < 100) {
            this.cleaningProgress += 0.5f;
        }
        //return Optional.of(new Sprite(AtlasHandler.obstacle.findRegions("Bubbles").get((int) this.cleaningProgress / 10)));
        return amICleaned();

    }

    public Sprite getOverlayBasedOnCleanProgress() {
        return new Sprite(AtlasHandler.obstacle.findRegions("Bubbles").get((int) this.cleaningProgress / 10));
    }

    private boolean amICleaned() {
        return this.cleaningProgress >= 90;
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
