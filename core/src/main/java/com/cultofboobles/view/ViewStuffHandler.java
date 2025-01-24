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
    private final Vector3 cameraVector = new Vector3();
    private final ExtendViewport viewport;

    private final Rectangle actualViewPort;

    public ViewStuffHandler() {

        viewPortX = Gdx.graphics.getWidth();
        viewPortY = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(viewPortX, viewPortY);
        camera.zoom = 2;
        viewport = new ExtendViewport(viewPortX, viewPortY, camera);

        actualViewPort = new Rectangle(
            cameraVector.x,
            cameraVector.y,
            viewPortX,
            viewPortY
        );

    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.setWorldSize(width, height);
    }

    public void moveCamera(float x, float y) {
        float newX = x + 0.5f;
        float newY = y + 0.5f;

        cameraVector.x = newX;
        cameraVector.y = newY;
        viewport.getCamera().position.lerp(cameraVector, 0.1f);
        actualViewPort.setPosition(
            newX - viewPortX / 2,
            newY - viewPortY / 2
        );
    }

    public ExtendViewport getViewport() {
        return viewport;
    }

    public Rectangle getActualViewPort() {
        return actualViewPort;
    }

}
