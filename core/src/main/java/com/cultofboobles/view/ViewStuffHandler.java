package com.cultofboobles.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class ViewStuffHandler {

    private float viewPortX = 900;
    private float viewPortY = 700;

    private final OrthographicCamera camera;
    private final ExtendViewport viewport;

    private Rectangle actualViewPort;

    public ViewStuffHandler() {

        viewPortX = Gdx.graphics.getWidth();
        viewPortY = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(viewPortX, viewPortY);
        //camera.zoom = 2;
        viewport = new ExtendViewport(viewPortX, viewPortY, camera);

        actualViewPort = new Rectangle(
            0,
            0,
            viewPortX,
            viewPortY
        );

    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.setWorldSize(width, height);
        //actualViewPort = new Rectangle(0,0,width,height);
        //actualViewPort.setSize(width,height);
        actualViewPort.setWidth(Gdx.graphics.getWidth());

    }


    public ExtendViewport getViewport() {
        return viewport;
    }

    public Rectangle getActualViewPort() {
        return actualViewPort;
    }

}
