package com.cultofboobles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cultofboobles.entity.ToolTypeData;
import com.cultofboobles.utils.SoundHandler;
import com.cultofboobles.utils.day.Day;
import com.cultofboobles.utils.day.DayGenerator;
import com.cultofboobles.view.ViewStuffHandler;

import java.util.LinkedList;
import java.util.List;

public class SecondScreen implements Screen {
    private final Game agame;

    private ViewStuffHandler viewStuffHandler;

    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private Sound intro;
    private Sound music;

    public SecondScreen(Game agame) {
        this.agame = agame;
        font = new BitmapFont(Gdx.files.internal("font/Tin5-Regular.fnt"));
        font.setColor(1, 1, 1, 1);
    }

    @Override
    public void show() {
        if (endOfRun()) {
            viewStuffHandler = new ViewStuffHandler("TempleGameOver");

            intro = SoundHandler.getEnd();
            music = SoundHandler.getBubelFaded();//SoundHandler.getBetweenDayMusic();
            music.loop(0.3f);
            SoundHandler.playIntro = false;
            intro.play();


        } else {
            System.out.println("game go on");
            viewStuffHandler = new ViewStuffHandler("TempleTop");
            //SoundHandler.playIntro = false; //ToDo put this out
            if (SoundHandler.playIntro) {
                intro = SoundHandler.getIntro();
                music = SoundHandler.getBubelFaded();//SoundHandler.getBetweenDayMusic();
                music.loop(0.3f);
                SoundHandler.playIntro = false;
                intro.play();
            } else {
                music = SoundHandler.getBetweenDayMusic();
                music.loop(0.3f);
            }
        }


        spriteBatch = new SpriteBatch();

//        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage);
//        Table root = new Table();
//        root.setFillParent(true);
//        TextButton textButton = new TextButton("second screen", skin);
//
//
//        root.add(textButton).width(100).height(50).center();
//
//        stage.addActor(root);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            if (endOfRun()) {
                Main.favorDemand = 10;
                Main.currentDay = 1;
                Main.ecomonics.reset();
            } else {
                Main.currentDay += 1;
            }

            // each day + 25% from day before demand
            Main.favorDemand +=  (Main.favorDemand * 1.25f);// * Main.currentDay); //(20 * Main.currentDay);
            Main.agame.setScreen(new FirstScreen(agame, DayGenerator.getDay(Main.currentDay)));
        }

        List<ToolTypeData> uiStuffList = new LinkedList<>();

        float winWidth = Gdx.graphics.getWidth();
        float winHeight = Gdx.graphics.getHeight();
        uiStuffList.add(new ToolTypeData("Day: " + Main.currentDay, winWidth / 2 - 80, 60, 2, 2));
        uiStuffList.add(new ToolTypeData("Coin balance: " + Main.ecomonics.getMoneyCount(), winWidth / 8, 40));
        uiStuffList.add(new ToolTypeData("Favor balance: " + Main.ecomonics.getBubbleFavor() + " / " + Main.favorDemand, (winWidth / 8) * 5, 40));

        if (endOfRun()) {
            uiStuffList.add(new ToolTypeData("GAME OVER", winWidth / 2 - 130, winHeight / 2 + 40, 1.5f, 1.5f));
        }
        uiStuffList.add(new ToolTypeData("press N to start", winWidth / 2 - 110, winHeight / 2));


        spriteBatch.begin();
        viewStuffHandler.background.draw(spriteBatch);
        spriteBatch.setColor(new Color(1, 0, 0, 0));
        for (ToolTypeData uiStuff : uiStuffList) {
            font.getData().setScale(uiStuff.scaleX, uiStuff.scaleY);
            font.draw(spriteBatch, uiStuff.msg, uiStuff.x, uiStuff.y);
        }

        spriteBatch.end();


//        stage.act();
//        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize 2 " + width + " " + height);
        viewStuffHandler.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        System.out.println("hide");
        if (intro != null) {
            intro.stop();
        }
        music.stop();
    }

    @Override
    public void dispose() {
    }

    private void switchToFirstScreen() {

        Day day = DayGenerator.getDay(Main.currentDay);

        agame.setScreen(new FirstScreen(agame, day));


    }

    private boolean endOfRun() {
        return Main.ecomonics.getBubbleFavor() < Main.favorDemand;
    }

}
