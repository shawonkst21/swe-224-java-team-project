package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.mygdx.game.MyGdxGame;
public class DesktopLauncher {
	public static void main (String[] arg) {

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("SPACE SHOOTER");
		config.setWindowedMode(MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
		config.setResizable(false);
		new Lwjgl3Application(new MyGdxGame(), config);

	}
}