package com.cultofboobles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cultofboobles.entity.Entity;
import com.cultofboobles.entity.Player;
import com.cultofboobles.ui.UiHandler;
import com.cultofboobles.utils.HitBox;
import com.cultofboobles.view.ViewStuffHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private final Game agame;

    private SpriteBatch spriteBatch;
    private ShapeRenderer sr;

    private final Map<String, Entity> entityMap = new HashMap<>();


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
        sr = new ShapeRenderer();

        viewStuffHandler = new ViewStuffHandler();
        uiHandler = new UiHandler();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(uiHandler.getUi());
        // to see borders of UI elements
        //stage.setDebugAll(true);

        prepareEntites();

    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Main.timeElapsed += delta;

        Entity player = entityMap.get("player");

        handleViewPort();

        drawUi();

        entityMap.forEach((id, entity) -> entity.update());


        if(!viewStuffHandler.getActualViewPort().contains(player.getHitBox())) {
            player.hitObstacle();
        }

        drawHitBoxes();

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

    private void prepareEntites() {

        this.entityMap.put(
            "player",
            new Player(
                "player",
                "",
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2
            )
        );

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

    private void drawHitBoxes() {
        sr.setProjectionMatrix(viewStuffHandler.getViewport().getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (var entry : entityMap.entrySet()) {
            Rectangle tmp = entry.getValue().getHitBox();
            sr.setColor(new Color(0, 0, 1, 0));
            sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
        }

        sr.setColor(new Color(0, 0, 0, 0));
        sr.rect(
            viewStuffHandler.getActualViewPort().x,
            viewStuffHandler.getActualViewPort().y,
            viewStuffHandler.getActualViewPort().width,
            viewStuffHandler.getActualViewPort().height
        );

        sr.end();

    }

    private void checkCollisions() {

    }

}
