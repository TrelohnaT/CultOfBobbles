package com.cultofboobles.entity;

public class ToolTypeData {

    public String msg;
    public float x;
    public float y;

    public float scaleX = 1;
    public float scaleY = 1;

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

}
