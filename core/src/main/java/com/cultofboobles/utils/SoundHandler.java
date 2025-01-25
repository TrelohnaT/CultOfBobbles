package com.cultofboobles.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundHandler {

    public static boolean playIntro = true;

    public static Sound getIntro() {
        return Gdx.audio.newSound(Gdx.files.internal("sound/Bubel lines/bubelintro.wav"));
    }

    public static Sound getBark() {
        int random = Utils.getRandom(0, 3) + 1;
        return Gdx.audio.newSound(Gdx.files.internal("sound/Bubel lines/Bark" + random + ".wav"));
    }

    public static Sound getEnd() {
        return Gdx.audio.newSound(Gdx.files.internal("sound/Bubel lines/bubelend.wav"));
    }

}
