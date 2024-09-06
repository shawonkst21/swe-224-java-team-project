package com.mygdx.game.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class gameSound {
    public static Music buttonclick= Gdx.audio.newMusic(Gdx.files.internal("Music/click.mp3"));
    public static Music explosion  = Gdx.audio.newMusic(Gdx.files.internal("Music/explosion.mp3"));
    public static Music theme  = Gdx.audio.newMusic(Gdx.files.internal("Music/theme.mp3"));

}
