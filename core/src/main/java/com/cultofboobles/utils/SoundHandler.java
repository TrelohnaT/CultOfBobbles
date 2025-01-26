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

    public static Sound getBetweenDayMusic() {
        Sound tmp = Gdx.audio.newSound(Gdx.files.internal("sound/music/Between day music.wav"));
        return tmp;
    }

    public static Sound getWorkDayMusic() {
        return Gdx.audio.newSound(Gdx.files.internal("sound/music/Dayshift music.wav"));
    }

    public static Sound getBubelFaded() {
        return Gdx.audio.newSound(Gdx.files.internal("sound/music/Bubel music faded.wav"));
    }

    public static Sound getSacrificePop() {
        return Gdx.audio.newSound(Gdx.files.internal("sound/effects/Sacrifice bubble pop.wav"));
    }

    public static Sound getCleaning() {
        return Gdx.audio.newSound(Gdx.files.internal("sound/effects/cleaning sound - Stereo Out.mp3"));
    }

}
