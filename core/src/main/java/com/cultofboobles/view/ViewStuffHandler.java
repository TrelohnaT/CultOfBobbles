package com.cultofboobles.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ViewStuffHandler {

    private float viewPortX;
    private float viewPortY;

    private final OrthographicCamera camera;
    private final Vector3 cameraVector = new Vector3();
    private final FitViewport viewport;

    private Rectangle actualViewPort;

    public Sprite background;

    public ViewStuffHandler() {

        background = new Sprite(new TextureAtlas("utils/background.atlas").findRegion("TempleBG"));

        //background.setScale(1.5f);
        viewPortX = background.getWidth(); //Gdx.graphics.getWidth();
        viewPortY = background.getHeight(); //Gdx.graphics.getHeight();
        camera = new OrthographicCamera(viewPortX, viewPortY);
        //camera.zoom = 2;
        viewport = new FitViewport(viewPortX, viewPortY, camera);
        actualViewPort = new Rectangle(
            cameraVector.x,
            cameraVector.y,
            viewPortX,
            viewPortY
        );
        viewport.update((int) viewPortX, (int) viewPortY, true);
        viewport.setWorldSize((int) viewPortX, (int) viewPortY);

    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.setWorldSize(width, height);
        //viewport.setCamera(new OrthographicCamera(width, height));
        //actualViewPort = new Rectangle(0,0,width,height);
        //actualViewPort.setSize(width,height);
        //actualViewPort.setWidth(Gdx.graphics.getWidth());

        camera.zoom = viewPortX/width;

        moveCamera(
            viewPortX / 2,
            viewPortY / 2
        );

    }

    public void moveCamera(float x, float y) {
        float newX = x + 0.5f;
        float newY = y + 0.5f;

        cameraVector.x = newX;
        cameraVector.y = newY;
        //ToDo fix resize down
        //viewport.getCamera().position.lerp(cameraVector, 0.1f);
//        actualViewPort.setPosition(
//            newX - viewPortX / 2,
//            newY - viewPortY / 2
//        );
//        actualViewPort = new Rectangle(
//            0,
//            0,
//            viewPortX,
//            viewPortY
//        );

        //background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    public FitViewport getViewport() {
        return viewport;
    }

    public Rectangle getActualViewPort() {
        return actualViewPort;
    }

}
