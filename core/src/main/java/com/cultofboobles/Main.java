package com.cultofboobles;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public static float timeElapsed = 0;

    @Override
    public void create() {
        setScreen(new FirstScreen(this));
    }
}
