package com.cultofboobles.utils;

import com.badlogic.gdx.math.Rectangle;

public class HitBox {

    public String id;
    public types type;
    public Rectangle rectangle;

    public HitBox(String id, types type, Rectangle rectangle) {
        this.id = id;
        this.type = type;
        this.rectangle = rectangle;

    }

    public enum types {
        UnEnterAble,
        EnterAble
    }
}
