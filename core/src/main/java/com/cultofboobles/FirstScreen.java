package com.cultofboobles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cultofboobles.entity.*;
import com.cultofboobles.obstacle.Bed;
import com.cultofboobles.obstacle.Obstacle;
import com.cultofboobles.obstacle.ObstacleFactory;
import com.cultofboobles.ui.UiHandler;
import com.cultofboobles.utils.HitBox;
import com.cultofboobles.utils.SoundHandler;
import com.cultofboobles.utils.Utils;
import com.cultofboobles.utils.day.Day;
import com.cultofboobles.view.ViewStuffHandler;

import java.util.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private final Game agame;
    private final Day day;

    private BitmapFont font;

    private SpriteBatch spriteBatch;
    private ShapeRenderer sr;

    private final Map<String, Entity> entityMap = new HashMap<>();
    private final Map<String, Obstacle> obstacleMap = new HashMap<>();

    private ViewStuffHandler viewStuffHandler;
    private UiHandler uiHandler;
    private Stage stage;

    private float dayStart = 0;
    private int dayTimeLeft = 10;

    private List<String> doomedList = new LinkedList<>();

    private Sound music;

    public FirstScreen(Game agame, Day day) {
        this.agame = agame;
        this.day = day;

        font = new BitmapFont(Gdx.files.internal("font/Tin5-Regular.fnt"));
    }

    @Override
    public void show() {
        // Prepare your screen here.
        spriteBatch = new SpriteBatch();
        sr = new ShapeRenderer();

        viewStuffHandler = new ViewStuffHandler("TempleBottom");
        uiHandler = new UiHandler();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        EntityFactory.customerCount = 0;
        this.music = SoundHandler.getWorkDayMusic();
        music.loop(0.3f);
        stage.addActor(uiHandler.getUi());
        // to see borders of UI elements
        //stage.setDebugAll(true);

        prepareEntities();

        obstacleMap.put(
            "bed1",
            ObstacleFactory.makeBed(
                "bed1",
                170,
                350
            )

        );

        obstacleMap.put(
            "bed2",
            ObstacleFactory.makeBed(
                "bed2",
                170,
                150
            )
        );
        obstacleMap.put(
            "bed3",
            ObstacleFactory.makeBed(
                "bed3",
                270,
                150
            )
        );

        obstacleMap.put(
            "bed4",
            ObstacleFactory.makeBed(
                "bed4",
                270,
                350
            )
        );

        dayStart = Main.timeElapsed;
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Main.timeElapsed += delta;
        dayTimeLeft = (int) (day.duration - (Main.timeElapsed - dayStart));
        if (dayTimeLeft < 0) {
            Main.agame.setScreen(new SecondScreen(agame));
        }

        Entity player = entityMap.get("player");
        handleViewPort();

        drawUi();

        entityMap.forEach((id, entity) -> entity.update());


        if (!viewStuffHandler.getActualViewPort().contains(player.getHitBox())) {
            player.hitObstacle("border");
        }

        customerSpawner();

        checkCollisions((Player) player);

        drawAll();

        //drawHitBoxes();

        for (Entity entity : this.entityMap.values()) {
            if (entity.isDoomed()) {
                doomedList.add(entity.getId());
            }
        }

        for (String doomedId : doomedList) {
            entityMap.remove(doomedId);
        }
        doomedList.clear();

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize 1 " + width + " " + height);
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
        music.stop();
        System.out.println("hide");
        getBedList().forEach(b -> {
            b.setFree(true);
            b.setEmpty(true);
            b.deleteCustomer();
        });

    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        System.out.println("disposed called");
    }

    private void prepareEntities() {

        this.entityMap.put(
            "player",

            EntityFactory.makePlayer(
                viewStuffHandler.background.getWidth() / 2,
                viewStuffHandler.background.getHeight() / 2
            )

        );

    }

    private void customerSpawner() {

        if (Main.timeElapsed - EntityFactory.lastSpawnedCustomer < Utils.getRandom(2, 10)) {
            return;
        }

        // find empty bed
        Optional<Bed> emptyBed = Optional.empty();
        List<Bed> bedList = getBedList();
        for (Bed bed : bedList) {
            if (bed.isFree()) {
                emptyBed = Optional.of(bed);
                break;
            }
        }

        // if empty bed exists and new customer may be spawned
        // add empty bed as destination and mark the bed as no longer empty
        if (emptyBed.isPresent() && EntityFactory.customerCount < day.customerMaxCount) {
            EntityFactory.lastSpawnedCustomer = Main.timeElapsed;
            emptyBed.get().setFree(false);
            String customerId = "customer_" + EntityFactory.customerCount;
            this.entityMap.put(
                customerId,
                EntityFactory.makeCustomer(
                    customerId,
                    emptyBed.get().getId(),
                    viewStuffHandler.background.getWidth() / 2,
                    viewStuffHandler.background.getHeight() - 100
                )
            );

        }


    }

    private void handleViewPort() {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewStuffHandler.getViewport().getCamera().update();
        viewStuffHandler.getViewport().apply();
        spriteBatch.setProjectionMatrix(viewStuffHandler.getViewport().getCamera().combined);

//        viewStuffHandler.moveCamera(
//            (float) Gdx.graphics.getWidth() / 2 + 0.5f,
//            (float) Gdx.graphics.getHeight() / 2 + 0.5f
//        );
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

    private void drawAll() {
        spriteBatch.begin();
        viewStuffHandler.background.draw(spriteBatch);

        //entityMap.get("player").getSprite().draw(spriteBatch);


        obstacleMap.values().forEach(v -> v.getSprite().draw(spriteBatch));

        entityMap.values().forEach(v -> {
            v.getSprite().draw(spriteBatch);

            if (v.getToolType().isPresent()) {
                font.setColor(v.getToolType().get().color);
                font.getData().setScale(v.getToolType().get().scaleX, v.getToolType().get().scaleY);
                font.draw(
                    spriteBatch, v.getToolType().get().msg,
                    v.getToolType().get().x,
                    v.getToolType().get().y);
                font.setColor(new Color(1, 1, 1, 1));

            }


            if (v instanceof Customer) {
                Customer customer = (Customer) v;

                Sprite a = customer.getMoodIcon();

                if (a != null) {

                    a.draw(spriteBatch);

                }

            }


        });


        obstacleMap.values()
            .forEach(v -> {
                if (v.getOverLay().isPresent()) {
                    v.getOverLay().get().draw(spriteBatch);
                }
            });
        //.forEach(v -> v.getOverLay().ifPresent(s -> s.draw(spriteBatch)));


        float winWidth = Gdx.graphics.getWidth();
        float winHeight = Gdx.graphics.getHeight();


        List<ToolTypeData> uiStuffList = new LinkedList<>();

        uiStuffList.add(new ToolTypeData("day time left: " + dayTimeLeft, winWidth / 7, 60));
        uiStuffList.add(new ToolTypeData("Day: " + Main.currentDay, winWidth / 2 - 80, 60, 2, 2));
        //font.draw(spriteBatch, "Day: " + day.count, (winWidth / 2) - 30, 60);

        uiStuffList.add(new ToolTypeData("Favor : " + Main.ecomonics.getBubbleFavor() + " / " + Main.favorDemand, (winWidth / 7) * 4 + 40, 80));
        uiStuffList.add(new ToolTypeData("Coins : " + Main.ecomonics.getMoneyCount(), (winWidth / 7) * 4 + 40, 45));

        spriteBatch.setColor(new Color(1, 0, 0, 0));
        for (ToolTypeData uiStuff : uiStuffList) {
            font.getData().setScale(uiStuff.scaleX, uiStuff.scaleY);
            font.draw(spriteBatch, uiStuff.msg, uiStuff.x, uiStuff.y);
        }

        spriteBatch.end();
    }

    private void checkCollisions(Player player) {

        obstacleMap.forEach((id, value) -> {
            if (value.getHitbox(HitBox.types.UnEnterAble).rectangle.overlaps(player.getHitBox())) {
                player.hitObstacle(value.getId());

            }


        });

        entityMap.forEach((idEntity, entity) -> {
            obstacleMap.forEach((idObstacle, obstacle) -> {
                if (entity.getHitBox().overlaps(obstacle.getHitbox(HitBox.types.UnEnterAble).rectangle)) {
                    entity.hitObstacle(idObstacle);
                } else if (entity.getHitBox().overlaps(obstacle.getHitbox(HitBox.types.EnterAble).rectangle)) {
                    // ToDo add type check
                    //entity.interactBed((Bed) obstacle);
                    obstacle.interact(entity);
                    if (obstacle instanceof Bed && entity instanceof Player) {
                        Player a = (Player) entity;
                        Bed bed = (Bed) obstacle;

                        if (!bed.isPersonCleaned()) {
                            a.setToolType(Player.toolTypeEnum.Clean);
                        } else {
                            a.setToolType(Player.toolTypeEnum.Sacrifice);

                        }

                    }

                }
            });
        });


    }

    private List<Bed> getBedList() {
        List<Bed> tmp = new LinkedList<>();
        for (Map.Entry<String, Obstacle> entry : obstacleMap.entrySet()) {
            if (entry.getValue() instanceof Bed) {
                tmp.add((Bed) entry.getValue());
            }
        }
        return tmp;
    }

    private List<Customer> getCunstomerList() {
        List<Customer> tmp = new LinkedList<>();
        for (Map.Entry<String, Entity> entry : entityMap.entrySet()) {
            if (entry.getValue() instanceof Customer) {
                tmp.add((Customer) entry.getValue());
            }
        }
        return tmp;
    }

}
