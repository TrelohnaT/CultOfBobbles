package com.cultofboobles.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UiHandler {

    private final Skin skin;

    // ToDo some global identification of buttons would be nice

    private final Map<String, Boolean> clickedState = new HashMap<>();

    private final Map<String, TextButton> textButtonMap = new LinkedHashMap<>();

    public UiHandler() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        clickedState.put("tool1", false);
        clickedState.put("tool2", false);

        textButtonMap.put("tool1", getTextButton("tool1"));
        textButtonMap.put("tool2", getTextButton("tool2"));


    }

    public Map<String, Boolean> getClickedState() {
        return clickedState;
    }

    public Table getUi() {

        Table root = new Table();
        root.setFillParent(true);

        Table bottomHotBar = new Table();

        for(TextButton button : this.textButtonMap.values()) {
            bottomHotBar.add(button);
        }

        root.add(bottomHotBar).expandY().bottom();

        return root;

    }

    public void dispose() {
        this.skin.dispose();

    }

    private TextButton getTextButton(String name) {
        TextButton textButton = new TextButton(name, skin);

        InputListener inputListener = new InputListener() {
          @Override
          public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
              System.out.println(name + " touchDown");
              clickedState.put(name, true);
              return true;
          }

          @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
              System.out.println(name + " touchUp");
              clickedState.put(name, false);
          }

        };

        textButton.addListener(inputListener);

        textButton.getLabelCell().width(100);
        textButton.getLabelCell().height(50);
        return textButton;
    }

}
