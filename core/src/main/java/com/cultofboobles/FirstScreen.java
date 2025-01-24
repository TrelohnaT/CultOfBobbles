package com.cultofboobles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cultofboobles.entity.Entity;
import com.cultofboobles.ui.UiHandler;
import com.cultofboobles.view.ViewStuffHandler;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    private final Game agame;

    private SpriteBatch spriteBatch;

    private ViewStuffHandler viewStuffHandler;
    private UiHandler uiHandler;
    private Stage stage;

    public FirstScreen(Game agame) {
        this.agame = agame;
    }

    @Override
    public void show() {
        // Prepare your screen here.
        spriteBatch = new SpriteBatch();
        viewStuffHandler = new ViewStuffHandler();
        uiHandler = new UiHandler();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(uiHandler.getUi());
        stage.setDebugAll(true);


    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Main.timeElapsed += delta;

        handleViewPort();

        drawUi();

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewStuffHandler.resize(width, height);
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }


    private void handleViewPort() {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewStuffHandler.getViewport().getCamera().update();
        viewStuffHandler.getViewport().apply();
        spriteBatch.setProjectionMatrix(viewStuffHandler.getViewport().getCamera().combined);

    }

    private void drawUi() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

}
