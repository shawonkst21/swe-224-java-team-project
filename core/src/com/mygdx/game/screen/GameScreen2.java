
package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;


import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.min;

public class GameScreen2 implements Screen {
    public static BitmapFont sfont,sfont2;
    private ShapeRenderer shapeRenderer;
    private final float maxHealth = 30;  // Maximum health of the ship


    MyGdxGame game;
    float x, y;
    int score;
    int health;
    Texture img;
    Texture ship = new Texture("screen2/Yellow.png");
    static Texture enemyTexture = new Texture("Screen2/Enemy2.png");
    static Texture projectileTextureShip = new Texture("Screen2/Fire2.png");
    static Texture projectileTexture = new Texture("hi.png");
    static Texture projectileTextureEnemy = new Texture("Screen2/alienFire.png");
    Texture healthKitTexture = new Texture("healthkit.png"); // Load the texture for the health kit
    public static Texture bossTexture = new Texture("Screen2/Boss.png"); // Load the texture for the boss

    float bg_y1 = 0, bg_y2;
    int bg_speed = 4; // Adjusted background speed
    public static float speed = 600; // Adjusted ship speed


    boolean bossActive = false;

    public GameScreen2(MyGdxGame game) {
        this.game = game;
        x = MyGdxGame.WIDTH / 2f - ship.getWidth() / 2f;
        y = 0;
        score = 0;
        health = 30; // Starting health
        shapeRenderer = new ShapeRenderer();




        // Load background texture
        img = new Texture("bg2.jpg");
        bg_y2 = img.getHeight(); // Initialize bg_y2 to the height of the image
        sfont = new BitmapFont(Gdx.files.internal("font/score.fnt")); // Initialize the font with the correct path
        sfont.getData().setScale(.55f);
        sfont2 = new BitmapFont(Gdx.files.internal("font/score.fnt")); // Initialize the font with the correct path
        sfont2.getData().setScale(.8f);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Handle ship movement
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (x < MyGdxGame.WIDTH - 115) x += speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (x > 0) x -= speed * Gdx.graphics.getDeltaTime();
        }

        // Handle ship firing


        // Moving background
        bg_y1 -= bg_speed;
        bg_y2 -= bg_speed;

        // Reset background position
        if (bg_y1 + img.getHeight() <= 0) {
            bg_y1 = bg_y2 + img.getHeight();
        }
        if (bg_y2 + img.getHeight() <= 0) {
            bg_y2 = bg_y1 + img.getHeight();
        }


        // Rendering
        game.batch.begin();
        game.batch.draw(img, 0, bg_y1);
        game.batch.draw(img, 0, bg_y2);
        game.batch.draw(ship, x, y, 120, 125);

      game.batch.end();
        // Draw the health bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float healthBarWidth = 180;
        float healthBarHeight = 10;
        float healthPercentage = health / maxHealth;
        float currentHealthBarWidth = healthBarWidth * healthPercentage;
        float healthBarX = x + ship.getWidth() / 2f - healthBarWidth / 2f;
        float healthBarY = y + ship.getHeight() + 10;
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        shapeRenderer.rect(97, 690, healthBarWidth, healthBarHeight);
        shapeRenderer.setColor(0, 1, 0, 1); // Green color
        shapeRenderer.rect(97, 690, currentHealthBarWidth, healthBarHeight);
        shapeRenderer.end();

        game.batch.begin();
        GlyphLayout scoreLayout = new GlyphLayout(sfont2, "Score: " + score);
        GlyphLayout healthLayout = new GlyphLayout(sfont, "Life:" );
        sfont2.draw(game.batch, scoreLayout, MyGdxGame.WIDTH - 250, MyGdxGame.HEIGHT - 20);
        sfont.draw(game.batch, healthLayout, 10, MyGdxGame.HEIGHT - 20);
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
        ship.dispose();
        enemyTexture.dispose();
        projectileTextureShip.dispose();
        healthKitTexture.dispose(); // Dispose health kit texture
        bossTexture.dispose(); // Dispose boss texture
    }
}
