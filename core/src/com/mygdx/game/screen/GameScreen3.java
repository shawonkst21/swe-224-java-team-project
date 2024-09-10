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

public class GameScreen3 implements Screen {
    public static BitmapFont sfont;

    MyGdxGame game;
    float x, y;
    int score;
    int health;
    Texture img;
    Texture ship = new Texture("ship.png");
    static Texture enemyTexture = new Texture("alien.png");
    static Texture projectileTextureShip = new Texture("fire2.png");
    static Texture projectileTexture = new Texture("fire.png");
    static Texture projectileTextureEnemy = new Texture("bossFire.png");
    Texture healthKitTexture = new Texture("healthkit.png"); // Load the texture for the health kit
    Texture bossTexture = new Texture("222.png"); // Load the texture for the boss

    float bg_x1 = 0, bg_x2 = 1280;
    int bg_speed = 6; // Adjusted background speed
    public static float speed = 600; // Adjusted ship speed

    ArrayList<Enemy> enemies;
    static ArrayList<Projectile> projectiles;
    static ArrayList<Projectile> shipProjectiles;
    static ArrayList<Projectile> Bossprojectiles;
    ArrayList<HealthKit> healthKits = new ArrayList<>(); // List of health kits
    Boss boss;
    boolean bossActive = false;

    public GameScreen3(MyGdxGame game) {
        this.game = game;
        x = 30;
        y = MyGdxGame.HEIGHT / 2f - 100f;
        score = 0;
        health = 30; // Starting health

        // Initialize enemies
        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            float startX = MyGdxGame.WIDTH + MathUtils.random(200, 400); // Start off-screen to the right
            float startY = MathUtils.random(0, MyGdxGame.HEIGHT - 100); // Random Y position
            enemies.add(new Enemy(startX, startY, 200));
        }

        // Initialize projectiles lists
        projectiles = new ArrayList<>();
        shipProjectiles = new ArrayList<>();
        Bossprojectiles=new ArrayList<>();

        // Initialize boss
        boss = new Boss(MyGdxGame.WIDTH + 50, MathUtils.random(0, MyGdxGame.HEIGHT - 100), 50, 30);

        // Load background texture
        img = new Texture("BG.jpg");
        sfont = new BitmapFont(Gdx.files.internal("font/score.fnt")); // Initialize the font with the correct path
        sfont.getData().setScale(.8f);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Handle ship movement
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (y < MyGdxGame.HEIGHT - 115) y += speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (y > 0) y -= speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (x < MyGdxGame.WIDTH - 115) x += speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (x > 0) x -= speed * Gdx.graphics.getDeltaTime();
        }

        // Handle ship firing
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shipProjectiles.add(new Projectile(x + ship.getWidth(), y + ship.getHeight() / 2, 500));
        }

        // Update enemies
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }

        // Update enemy projectiles
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);
            if (projectile.x + projectileTextureEnemy.getWidth() < 0) {
                projectiles.remove(i);
            }
        }

        // Update ship projectiles
        for (int i = shipProjectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = shipProjectiles.get(i);
            projectile.update(delta);
            if (projectile.x > MyGdxGame.WIDTH) {
                shipProjectiles.remove(i);
            }
        }

        // Update health kits
        for (int i = healthKits.size() - 1; i >= 0; i--) {
            HealthKit healthKit = healthKits.get(i);
            healthKit.update(delta);
            if (healthKit.x + healthKitTexture.getWidth() < 0) {
                healthKits.remove(i);
            }
        }

        // Check if score is a multiple of 20 and add boss
        if (score % 2 == 0 && score != 0 && !bossActive) {
            boss.reset();
            bossActive = true;
        }

        // Update boss
        if (bossActive) {
            boss.update(delta, x, y);
            if (boss.health <= 0) {
                bossActive = false;
            }
        }
        //boss
        for (int i = Bossprojectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = Bossprojectiles.get(i);
            projectile.update(delta);
            if (projectile.x + projectileTexture.getWidth() < 0) {
                Bossprojectiles.remove(i);
            }
        }
        // Check for collisions
        checkCollisions();

        // Moving background
        bg_x1 -= bg_speed;
        bg_x2 -= bg_speed;

