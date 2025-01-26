package com.cultofboobles.entity;

import com.badlogic.gdx.graphics.Color;

public class ToolTypeData {

    public String msg;
    public float x;
    public float y;

    public float scaleX = 1;
    public float scaleY = 1;
    public Color color = new Color(0,0,0,0);

    public ToolTypeData(String msg, float x, float y) {
        this.msg = msg;
        this.x = x;
        this.y = y;
    }

    public ToolTypeData(
        String msg,
        float x,
        float y,
        float scaleX,
        float scaleY
    ) {
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public ToolTypeData(
        String msg,
        float x,
        float y,
        float scaleX,
        float scaleY,
        Color color
    ) {
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.color = color;
    }

}
