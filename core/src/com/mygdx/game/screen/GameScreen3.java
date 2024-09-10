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

        // Reset background position
        if (bg_x1 + img.getWidth() <= 0) {
            bg_x1 = bg_x2 + img.getWidth();
        }
        if (bg_x2 + img.getWidth() <= 0) {
            bg_x2 = bg_x1 + img.getWidth();
        }

        // Check if score is a multiple of 10 and add health kit
        if (score % 10 == 0 && score != 0 && !healthKits.stream().anyMatch(kit -> kit.x > 0)) {
            float healthKitX = MyGdxGame.WIDTH;
            float healthKitY = MathUtils.random(0, MyGdxGame.HEIGHT - 50); // Random Y position
            healthKits.add(new HealthKit(healthKitX, healthKitY, 200)); // Adjust speed as needed
        }

        // Rendering
        game.batch.begin();
        game.batch.draw(img, bg_x1, 0);
        game.batch.draw(img, bg_x2, 0);
        game.batch.draw(ship, x, y, 115, 120);

        // Draw enemies
        for (Enemy enemy : enemies) {
            game.batch.draw(enemyTexture, enemy.x, enemy.y, 80, 70);
        }

        // Draw enemy projectiles
        for (Projectile projectile : projectiles) {
            game.batch.draw(projectileTextureEnemy, projectile.x, projectile.y + 10, 40, 55);
        }

        // Draw ship projectiles
        for (Projectile projectile : shipProjectiles) {
            game.batch.draw(projectileTextureShip, projectile.x, projectile.y + 10, 70, 50);
        }

        // Draw health kits
        for (HealthKit healthKit : healthKits) {
            game.batch.draw(healthKitTexture, healthKit.x, healthKit.y, 70, 50); // Adjust size as needed
        }

        // Draw boss
        if (bossActive) {
            game.batch.draw(bossTexture, boss.x, boss.y, 150, 150);
            // Adjust size as neede
            // System.out.println("koooo");
        }
        // Draw enemy projectiles
        for (Projectile projectile : Bossprojectiles) {
            game.batch.draw(projectileTexture, projectile.x, projectile.y + 10, 50, 30);
        }

        GlyphLayout scoreLayout = new GlyphLayout(sfont, "Score: " + score);
        GlyphLayout HealthLayout = new GlyphLayout(sfont, "Life: " + Math.ceil(health/10));
        sfont.draw(game.batch, scoreLayout, MyGdxGame.WIDTH - 250, MyGdxGame.HEIGHT - 30);
        sfont.draw(game.batch, HealthLayout, 10, MyGdxGame.HEIGHT - 30);

        game.batch.end();
    }

    private void checkCollisions() {
        // Create rectangles for the ship and enemies
        Rectangle shipRect = new Rectangle(x, y, ship.getWidth() - 50, ship.getHeight() - 50);

        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            Rectangle enemyRect = new Rectangle(enemy.x, enemy.y, enemyTexture.getWidth() - 500, enemyTexture.getHeight() - 400);

            // Check for collision between ship projectiles and enemies
            Iterator<Projectile> shipProjectileIterator = shipProjectiles.iterator();
            while (shipProjectileIterator.hasNext()) {
                Projectile projectile = shipProjectileIterator.next();
                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, projectileTextureShip.getWidth() - 100, projectileTextureShip.getHeight() - 70);
                if (projectileRect.overlaps(enemyRect)) {
                    shipProjectileIterator.remove();
                    enemy.reset(); // Reset enemy position instead of removing
                    score++;
                    break;
                }
            }

            // Check for collision between enemy projectiles and the ship
            Iterator<Projectile> enemyProjectileIterator = projectiles.iterator();
            while (enemyProjectileIterator.hasNext()) {
                Projectile projectile = enemyProjectileIterator.next();
                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, projectileTextureEnemy.getWidth() - 100, projectileTextureEnemy.getHeight() - 70);
                if (projectileRect.overlaps(shipRect)) {
                    enemyProjectileIterator.remove();
                    health--;
                    if (health <= 0) {
                        // Handle game over (e.g., restart the game or show game over screen)
                    }
                    break;
                }
            }
        }

        // Check for collision between ship and health kits
        Iterator<HealthKit> healthKitIterator = healthKits.iterator();
        while (healthKitIterator.hasNext()) {
            HealthKit healthKit = healthKitIterator.next();
            Rectangle healthKitRect = new Rectangle(healthKit.x, healthKit.y, healthKitTexture.getWidth() - 30, healthKitTexture.getHeight() - 30); // Adjust size as needed
            if (healthKitRect.overlaps(shipRect)) {
                healthKitIterator.remove();
                health += 10; // Increase health by 10
                break;
            }
        }

        // Check for collision between ship projectiles and boss
        if (bossActive) {
            Rectangle bossRect = new Rectangle(boss.x, boss.y, bossTexture.getWidth() , bossTexture.getHeight()); // Adjust size as needed
            Iterator<Projectile> shipProjectileIterator = shipProjectiles.iterator();
            while (shipProjectileIterator.hasNext()) {
                Projectile projectile = shipProjectileIterator.next();
                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, projectileTexture.getWidth() - 100, projectileTexture.getHeight() - 70);
                if (projectileRect.overlaps(bossRect)) {
                    shipProjectileIterator.remove();
                    boss.health -= 10; // Decrease boss health
                    if (boss.health <= 0) {
                        bossActive = false;
                        score += 5; // Reward player for defeating the boss
                    }
                    break;
                }
            }
        }
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