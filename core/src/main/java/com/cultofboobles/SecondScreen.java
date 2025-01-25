package com.cultofboobles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cultofboobles.utils.day.Day;
import com.cultofboobles.utils.day.DayGenerator;

public class SecondScreen implements Screen {
    private final Game agame;

    Stage stage;
    Skin skin;


    public SecondScreen(Game agame) {
        this.agame = agame;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        TextButton textButton = new TextButton("second screen", skin);


        root.add(textButton).width(100).height(50).center();

        stage.addActor(root);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            Main.currentDay +=1;
            agame.setScreen(new FirstScreen(agame, DayGenerator.getDay(Main.currentDay)));
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
