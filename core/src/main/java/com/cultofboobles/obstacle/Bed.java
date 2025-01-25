package com.cultofboobles.obstacle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.entity.Customer;
import com.cultofboobles.entity.Entity;
import com.cultofboobles.entity.Player;
import com.cultofboobles.utils.HitBox;

import java.util.HashMap;
import java.util.Map;

public class Bed implements Obstacle {

    private final String id;
    private final float x;
    private final float y;

    private final Sprite sprite;

    private boolean empty = true;

    private Customer customer;

    Map<HitBox.types, HitBox> hitBoxMap = new HashMap<>();

    public Bed(
        String id,
        Sprite sprite,
        float x,
        float y
    ) {
        this.id = id;
        this.x = x;
        this.y = y;

        this.sprite = sprite;

        sprite.translateX(x);
        sprite.translateY(y);
        hitBoxMap.put(
            HitBox.types.UnEnterAble,
            new HitBox(
                "",
                HitBox.types.UnEnterAble,
                new Rectangle(x, y, sprite.getWidth(), sprite.getHeight())
            )
        );

        hitBoxMap.put(
            HitBox.types.EnterAble,
            new HitBox(
                "",
                HitBox.types.EnterAble,
                new Rectangle(
                    x - sprite.getWidth() / 2,
                    y - sprite.getHeight() / 2,
                    sprite.getWidth() * 2,
                    sprite.getHeight() * 2
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
        return sprite;
    }

    @Override
    public HitBox getHitbox(HitBox.types type) {
        return hitBoxMap.get(type);
    }

    @Override
    public void interact(Entity entity) {
        if(entity instanceof Customer) {
            if(entity.interactBed(this)) {
                System.out.println(this.id + " has " + entity.getId());
                this.customer = (Customer) entity;
            }
        }

        if(entity instanceof Player) {
            System.out.println(this.id + " player detected");


        }


    }

    public void setEmpty(boolean value) {
        this.empty = value;
    }

    public boolean isEmpty() {
        return this.empty;
    }

}
