package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    MyGdxGame game;
    Texture img;
    float bg_x1 = 0, bg_x2 = 1280;
    int bg_speed = 6; // Adjusted background speed
    public static float speed = 600; // Adjusted ship speed

    public GameScreen(MyGdxGame game) {
        this.game = game;
        img = new Texture("background.jpg");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        // Moving background
        bg_x1 -= bg_speed;
        bg_x2 -= bg_speed;

        // Reset background position
        if (bg_x1 + img.getWidth() <= 0) {
            bg_x1 = bg_x2 + img.getWidth();
        }
        if (bg_x2 + img.getWidth() <= 0) {
            bg_x2 = bg_x1 + img.getWidth();
        }


        // Rendering background
        game.batch.begin();
        game.batch.draw(img, bg_x1, 0);
        game.batch.draw(img, bg_x2, 0);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
        img.dispose();
    }
}
