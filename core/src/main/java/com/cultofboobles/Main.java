package com.cultofboobles;

import com.badlogic.gdx.Game;
import com.cultofboobles.utils.AtlasHandler;
import com.cultofboobles.utils.Ecomonics;
import com.cultofboobles.utils.day.DayGenerator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public static float timeElapsed = 0;
    public static int currentDay = 0;
    public static AtlasHandler atlasHandler;

    public static Ecomonics ecomonics;

    @Override
    public void create() {
        ecomonics = new Ecomonics();
        atlasHandler  = new AtlasHandler();
        setScreen(new SecondScreen(this));
        //setScreen(new FirstScreen(this));
    }
}
