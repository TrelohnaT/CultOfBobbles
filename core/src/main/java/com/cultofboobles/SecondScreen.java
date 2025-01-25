package com.cultofboobles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private Stage stage;
    private Skin skin;


    public SecondScreen(Game agame) {
        this.agame = agame;
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
    }

    @Override
    public void show() {
        if(SoundHandler.playIntro) {
            SoundHandler.getIntro().play();
        }
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        spriteBatch = new SpriteBatch();
        viewStuffHandler = new ViewStuffHandler("TempleTop");

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
            Main.currentDay += 1;
            agame.setScreen(new FirstScreen(agame, DayGenerator.getDay(Main.currentDay)));
        }

        List<ToolTypeData> uiStuffList = new LinkedList<>();



        uiStuffList.add(new ToolTypeData("Day: " + Main.currentDay, 400, 500, 3, 3));
        uiStuffList.add(new ToolTypeData("Coin balance: " + Main.ecomonics.getMoneyCount(), 200, 450, 2,2));

        uiStuffList.add(new ToolTypeData("Coin Soap: " + Main.ecomonics.getSoap() + " buy more Soap by pressing: Q", 200, 400, 2,2));
        uiStuffList.add(new ToolTypeData("shoping is not working, fix it", 200, 350, 2,2));

        uiStuffList.add(new ToolTypeData("Start play by pressing: N", 100, 100, 2,2));


        spriteBatch.begin();
        viewStuffHandler.background.draw(spriteBatch);
        spriteBatch.setColor(new Color(1, 0, 0, 0));
        for(ToolTypeData uiStuff : uiStuffList) {
            font.getData().setScale(uiStuff.scaleX,uiStuff.scaleY);
            font.draw(spriteBatch, uiStuff.msg, uiStuff.x, uiStuff.y);
        }

        spriteBatch.end();


//        stage.act();
//        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewStuffHandler.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
    }

    private void switchToFirstScreen() {

        Day day = DayGenerator.getDay(Main.currentDay);

        agame.setScreen(new FirstScreen(agame, day));


    }

}
