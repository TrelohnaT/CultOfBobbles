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
import com.cultofboobles.entity.EntityFactory;
import com.cultofboobles.entity.Player;
import com.cultofboobles.obstacle.Obstacle;
import com.cultofboobles.obstacle.ObstacleFactory;
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
    private final Map<String, Obstacle> obstacleMap = new HashMap<>();

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

        prepareEntities();

        obstacleMap.put(
            "bed1",
            ObstacleFactory.makeBed(
                "bed1",
                70,
                70
            )

        );

    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Main.timeElapsed += delta;

        Entity player = entityMap.get("player");

        handleViewPort();

        drawUi();

        entityMap.forEach((id, entity) -> entity.update());


        if (!viewStuffHandler.getActualViewPort().contains(player.getHitBox())) {
            player.hitObstacle("border");
        }

        checkCollisions(player);

        drawHitBoxes();

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewStuffHandler.resize(width, height);
        //stage.getViewport().update(width, height, true);

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

    private void prepareEntities() {

        this.entityMap.put(
            "player",
            new Player(
                "player",
                "",
                Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2,
                32,
                64
            )
        );

        this.entityMap.put(
            "customer",
            EntityFactory.makeCustomer(
                "customer",
                Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() - 100)


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
        //stage.act(Gdx.graphics.getDeltaTime());
        //stage.draw();
    }

    private void drawHitBoxes() {
        sr.setProjectionMatrix(viewStuffHandler.getViewport().getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (var entry : entityMap.entrySet()) {
            Rectangle tmp = entry.getValue().getHitBox();
            sr.setColor(new Color(0, 0, 1, 0));
            sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
        }

        drawRectangle(viewStuffHandler.getActualViewPort(), new Color(0, 0, 0, 0));

        obstacleMap.forEach((key, value) -> {
            drawHitBox(value.getHitbox(HitBox.types.EnterAble));
            drawHitBox(value.getHitbox(HitBox.types.UnEnterAble));

        });


        sr.end();

    }

    private void drawHitBox(HitBox hitBox) {
        if (HitBox.types.UnEnterAble.equals(hitBox.type)) {
            drawRectangle(hitBox.rectangle, new Color(1, 0, 0, 0));
        } else if (HitBox.types.EnterAble.equals(hitBox.type)) {
            drawRectangle(hitBox.rectangle, new Color(0, 1, 0, 0));
        }

    }

    private void drawRectangle(Rectangle rectangle, Color color) {
        sr.setColor(color);
        sr.rect(
            rectangle.x,
            rectangle.y,
            rectangle.width,
            rectangle.height
        );

    }

    private void checkCollisions(Entity player) {

        obstacleMap.forEach((id, value) -> {
            if (value.getHitbox(HitBox.types.UnEnterAble).rectangle.overlaps(player.getHitBox())) {
                player.hitObstacle(value.getId());
            }
        });

        entityMap.forEach((idEntity, entity) -> {
            obstacleMap.forEach((idObstacle, obstacle) -> {
                if(entity.getHitBox().overlaps(obstacle.getHitbox(HitBox.types.UnEnterAble).rectangle)) {
                    entity.hitObstacle(idObstacle);
                } else if(entity.getHitBox().overlaps(obstacle.getHitbox(HitBox.types.EnterAble).rectangle)) {
                    entity.interact(idObstacle);
                }
            });
        });


    }

}
