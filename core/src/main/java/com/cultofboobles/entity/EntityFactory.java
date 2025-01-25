package com.cultofboobles.entity;

import com.badlogic.gdx.Gdx;

public class EntityFactory {


    public static Customer makeCustomer(String id, float x, float y) {
         return new Customer(
            id,
            "",
            x,
            y,
            32,
            64
        );
    }

}
