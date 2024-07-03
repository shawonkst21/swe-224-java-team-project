package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Firstscreen implements Screen {
    MyGdxGame game;
    Texture pic,ship;
    float loadingProgress;
    ShapeRenderer shapeRenderer;
    BitmapFont font;

    public Firstscreen(MyGdxGame game) {
        this.game = game;
        loadingProgress = 0f;
        shapeRenderer = new ShapeRenderer();
        font=new BitmapFont(Gdx.files.internal("font/score.fnt"));
        font.getData().setScale(0.5f);
    }

    @Override
    public void show() {
        pic = new Texture("FirstScreen.png");
        ship = new Texture("LoadingShip.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        game.batch.begin();
        game.batch.draw(pic, 0, 0);
        game.batch.end();

       //loading bar
        if (loadingProgress < 1f) {
            loadingProgress += delta * 0.2f; // Adjust the speed of loading as needed
        }
        int percentage = (int) (loadingProgress * 100);


        // Draw the loading bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.SALMON);
        shapeRenderer.rect(0, 50, MyGdxGame.WIDTH * loadingProgress, 5);
        shapeRenderer.end();

        // Calculate ship position based on loadingProgress
        float shipX = MyGdxGame.WIDTH * loadingProgress - (ship.getWidth() / 2);
        float shipY = 50 - (ship.getHeight() / 2);

        // Draw the ship at the calculated position
        game.batch.begin();
        game.batch.draw(ship, shipX, shipY);
        font.draw(game.batch, "Loading.."+percentage + "%",MyGdxGame.WIDTH/2-100,40);
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
        pic.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
