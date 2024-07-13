package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.*;


public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {

		super.render();
	}


}
