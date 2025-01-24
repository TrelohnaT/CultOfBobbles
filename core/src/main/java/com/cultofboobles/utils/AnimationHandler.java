package com.cultofboobles.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.cultofboobles.Main;

import java.util.*;

public class AnimationHandler implements Disposable {

    public final Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();

    private final TextureAtlas atlas;

    private List<String> orderList = new LinkedList<>(Arrays.asList("body", "legs","hands","head"));

    public AnimationHandler(String atlasPath, Map<String, Float> animationSpeedMap) {
        this.atlas = new TextureAtlas(atlasPath);

        animationSpeedMap.forEach((name, speed) -> {
            animationMap.put(
                name,
                new Animation<>(speed, atlas.findRegions(name))
            );
        });

    }

    public List<Sprite> getSpriteList(float x, float y) {
        Map<String, Sprite> tmpMap = new HashMap<>();
        this.animationMap.forEach((name, animation) -> {
            Sprite tmp = new Sprite(animation.getKeyFrame(Main.timeElapsed, true));
            tmp.translateX(x - tmp.getWidth() / 2);
            tmp.translateY(y - tmp.getHeight() / 2);
            tmpMap.put(name, tmp);
        });

        List<Sprite> tmpList = new LinkedList<>();
        // making sure the sprint will be layered correctly
        for(String name : this.orderList) {
            if(tmpMap.containsKey(name)) {
                tmpList.add(tmpMap.get(name));
            } else {
                System.out.println("do not know sprite by nane: " + name);
            }
        }
        return tmpList;
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
        animationMap.clear();

    }
}
