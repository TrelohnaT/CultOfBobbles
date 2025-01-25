package com.cultofboobles.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.Main;

import java.util.List;

public class Player implements Entity {


    private String id = "";
    private float x = 0; // not pixels but tiles
    private float y = 0; // not pixels but tiles
    private float previousX = 0;
    private float previousY = 0;

    private float sizeX = 0;
    private float sizeY = 0;

    private final float speed = 250f;


    private final String atlasPath;
    private final TextureAtlas atlas;

    private final Rectangle hitBox;

    private final Animation<TextureRegion> idle;

    private boolean isFacingLeft = false;


    public Player(
        String id,
        String atlasPath,
        float x,
        float y,
        float sizeX,
        float sizeY
    ) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.hitBox = new Rectangle(x - this.sizeX / 2, y - this.sizeY / 2, this.sizeX, this.sizeY);
        this.atlas = new TextureAtlas(atlasPath);

        this.idle = new Animation<>(1, atlas.findRegions("MainCharacter_IdleFront"));
    }

    public void update() {

        this.previousX = x;
        this.previousY = y;

        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean idle = true;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.y += this.speed * deltaTime;
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.y += this.speed * deltaTime * (-1);
            idle = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.x += this.speed * deltaTime * (-1);
            idle = false;
            isFacingLeft = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += this.speed * deltaTime;
            idle = false;
            isFacingLeft = false;
        }

        // if no movement, switch to idle animation
        if (idle) {
        } else {
            // move hitBox
            this.hitBox.setPosition(this.x - this.sizeX / 2, this.y - this.sizeY / 2);
        }
    }

    @Override
    public List<Sprite> getSpriteList() {

        return List.of();
    }

    @Override
    public Sprite getSprite() {
        Sprite tmp = new Sprite(idle.getKeyFrame(Main.timeElapsed, true));

        if(!isFacingLeft) {
            tmp.flip(true, false);
        }

        tmp.translateX(x - tmp.getWidth() / 2);
        tmp.translateY(y - tmp.getHeight() / 2);
        return tmp;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    @Override
    public void hitObstacle(String obstacleId) {
        this.x = previousX;
        this.y = previousY;
    }

    @Override
    public void interact(String obstacleId) {

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
    public void load() {
        //this.atlas = new TextureAtlas(atlasPath);

    }

    @Override
    public void clear() {
        this.atlas.dispose();
    }
}
