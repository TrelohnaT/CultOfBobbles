package com.cultofboobles.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.cultofboobles.entity.Customer;
import com.cultofboobles.entity.Entity;
import com.cultofboobles.entity.Player;
import com.cultofboobles.utils.AtlasHandler;
import com.cultofboobles.utils.HitBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Bed implements Obstacle {

    private final String id;
    private final float x;
    private final float y;

    private final Sprite sprite;

    private boolean empty = true;
    private boolean free = true;

    private Customer customer;

    private Optional<Sprite> showOverlay = Optional.empty();

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
        if (entity instanceof Customer) {
            if (entity.interactBed(this)) {
                System.out.println(this.id + " has " + entity.getId());
                this.customer = (Customer) entity;
            }
        }

        if (entity instanceof Player) {

            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                if (!this.empty) {
                    if (customer.increaseCleanProgress()) {
                        System.out.println("customer cleaned");
                    } else {
                        System.out.println("customer cleaning");
                    }
                } else {
                    System.out.println("wait for customer");
                }
            }
        }
    }

    @Override
    public Optional<Sprite> getOverLay() {
        if(this.empty) {
            return Optional.of(new Sprite(AtlasHandler.obstacle.findRegion("Empty")));
        }

        if (this.customer != null) {
            Sprite tmp = customer.getOverlayBasedOnCleanProgress();
            tmp.translate(x, y);
            return Optional.of(tmp);
        }


        return Optional.empty();
    }

    public void setFree(boolean value) {
        this.free = value;
    }

    public boolean isFree() {
        return this.free;
    }

    public void setEmpty(boolean value) {
        this.empty = value;
    }

    public boolean isEmpty() {
        return this.empty;
    }

}
