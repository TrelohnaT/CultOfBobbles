package com.cultofboobles.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.utils.AnimationHandler;
import com.cultofboobles.utils.BodyAnimationParts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Entity {


    private final String idle_animation = "idle_animation";
    private final String frontWalk = "front_walk";
    private final String backWalk = "back_walk";
    private final String leftWalk = "left_walk";
    private final String rightWalk = "right_walk";

    private final AnimationHandler idleAnimation;
    private final AnimationHandler frontWalkAnimation;
    private final AnimationHandler backWalkAnimation;
    private final AnimationHandler leftWalkAnimation;
    private final AnimationHandler rightWalkAnimation;

    private String id = "";
    private float x = 0; // not pixels but tiles
    private float y = 0; // not pixels but tiles
    private float previousX = 0;
    private float previousY = 0;

    private float sizeX = 0;
    private float sizeY = 0;

    private final float speed = 250f;


    private final String atlasPath;
    private String currentAnimation = idle_animation;

    private final Rectangle hitBox;

    public Player(
        String id,
        String atlasPath,
        float x,
        float y
    ) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.sizeX = 128;//tmp.getWidth(); //ToDo this might make some issues
        this.sizeY = 128;//tmp.getHeight();
        this.hitBox = new Rectangle(x - this.sizeX / 2, y - this.sizeY / 2, this.sizeX, this.sizeY);

        // ToDo make this prettier and make the order of frames to be configurable, idle head should be on top

        Map<String, Float> idleSpeed = new HashMap<>();
        idleSpeed.put(BodyAnimationParts.body, 0.5f);
        idleSpeed.put(BodyAnimationParts.hands, 0.5f);
        idleSpeed.put(BodyAnimationParts.legs, 1f);
        idleSpeed.put(BodyAnimationParts.head, 0.5f);
        idleAnimation = new AnimationHandler("playerAnimations/idle/idle.atlas", idleSpeed);

        Map<String, Float> frontAnimationSpeed = new HashMap<>();
        frontAnimationSpeed.put(BodyAnimationParts.body, 1 / 15f);
        frontAnimationSpeed.put(BodyAnimationParts.head, 0.5f);
        frontAnimationSpeed.put(BodyAnimationParts.hands, 1 / 15f);
        frontAnimationSpeed.put(BodyAnimationParts.legs, 1 / 15f);
        frontWalkAnimation = new AnimationHandler("playerAnimations/front/walk_front.atlas", frontAnimationSpeed);

        Map<String, Float> backAnimationSpeed = new HashMap<>();
        backAnimationSpeed.put(BodyAnimationParts.body, 1 / 15f);
        backAnimationSpeed.put(BodyAnimationParts.head, 0.5f);
        backAnimationSpeed.put(BodyAnimationParts.hands, 1 / 15f);
        backAnimationSpeed.put(BodyAnimationParts.legs, 1 / 15f);
        backWalkAnimation = new AnimationHandler("playerAnimations/walk_back/walk_back.atlas", backAnimationSpeed);

        Map<String, Float> leftAnimationSpeed = new HashMap<>();
        leftAnimationSpeed.put(BodyAnimationParts.body, 1 / 15f);
        leftAnimationSpeed.put(BodyAnimationParts.head, 0.5f);
        leftAnimationSpeed.put(BodyAnimationParts.hands, 1 / 15f);
        leftAnimationSpeed.put(BodyAnimationParts.legs, 1 / 15f);
        leftWalkAnimation = new AnimationHandler("playerAnimations/walk_left/walk_left.atlas", leftAnimationSpeed);

        Map<String, Float> rightAnimationSpeed = new HashMap<>();
        rightAnimationSpeed.put(BodyAnimationParts.body, 1 / 15f);
        rightAnimationSpeed.put(BodyAnimationParts.head, 0.5f);
        rightAnimationSpeed.put(BodyAnimationParts.hands, 1 / 15f);
        rightAnimationSpeed.put(BodyAnimationParts.legs, 1 / 15f);
        rightWalkAnimation = new AnimationHandler("playerAnimations/walk_right/walk_right.atlas", rightAnimationSpeed);
    }

    public void update() {

        this.previousX = x;
        this.previousY = y;

        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean idle = true;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.y += this.speed * deltaTime;
            currentAnimation = backWalk;
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.y += this.speed * deltaTime * (-1);
            currentAnimation = frontWalk;
            idle = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.x += this.speed * deltaTime * (-1);
            currentAnimation = leftWalk;
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += this.speed * deltaTime;
            currentAnimation = rightWalk;
            idle = false;
        }

        // if no movement, switch to idle animation
        if (idle) {
            currentAnimation = idle_animation;
        } else {
            // move hitBox
            this.hitBox.setPosition(this.x - this.sizeX / 2, this.y - this.sizeY / 2);
        }
    }

    @Override
    public List<Sprite> getSpriteList() {
        if (currentAnimation.equals(idle_animation)) {
            return idleAnimation.getSpriteList(this.x, this.y);
        } else if (currentAnimation.equals(frontWalk)) {
            return frontWalkAnimation.getSpriteList(this.x, this.y);
        } else if (currentAnimation.equals(backWalk)) {
            return backWalkAnimation.getSpriteList(this.x, this.y);
        } else if (currentAnimation.equals(leftWalk)) {
            return leftWalkAnimation.getSpriteList(this.x, this.y);
        } else if (currentAnimation.equals(rightWalk)) {
            return rightWalkAnimation.getSpriteList(this.x, this.y);
        }
        return List.of();
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    @Override
    public void hitObstacle() {
        this.x = previousX;
        this.y = previousY;
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
    }
}
