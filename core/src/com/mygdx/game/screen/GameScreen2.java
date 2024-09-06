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
import com.mygdx.game.Sound.gameSound;
import com.mygdx.game.screen.Projectile;

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

    ArrayList<Enemy> enemies;
    static ArrayList<Projectile> projectiles;
    static ArrayList<Projectile>shipProjectiles;
    static ArrayList<Projectile> Bossprojectiles;
    ArrayList<HealthKit> healthKits = new ArrayList<>(); // List of health kits
    ArrayList<Explosion>explosions=new ArrayList<>();
    public static Boss boss;
    boolean bossActive = false;

    public GameScreen2(MyGdxGame game) {
        this.game = game;
        x = MyGdxGame.WIDTH / 2f - ship.getWidth() / 2f;
        y = 0;
        score = 0;
        health = 30; // Starting health
        shapeRenderer = new ShapeRenderer();
        gameSound.theme.setLooping(true);
        gameSound.theme.play();
        // Initialize enemies
        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            float startX = MathUtils.random(0, MyGdxGame.WIDTH - 100); // Random X position
            float startY = MyGdxGame.HEIGHT + MathUtils.random(200, 400); // Start off-screen to the top
            enemies.add(new Enemy(startX, startY, 200));
        }

        // Initialize projectiles lists
        projectiles = new ArrayList<>();
        shipProjectiles = new ArrayList<>();
        Bossprojectiles = new ArrayList<>();

        // Initialize boss
        boss = new Boss(MathUtils.random(0, MyGdxGame.WIDTH - 100), MyGdxGame.HEIGHT , 30, 30);

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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shipProjectiles.add(new Projectile(x+35, y+20, 500));
        }


        // Update enemies
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }

        // Update enemy projectiles
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);
            if (projectile.y + projectileTextureEnemy.getHeight() < 0) {
                projectiles.remove(i);
            }
        }

        // Update ship projectiles
        for (int i = shipProjectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = shipProjectiles.get(i);
            projectile.update(delta);
            if (projectile.y > MyGdxGame.HEIGHT) {
                shipProjectiles.remove(i);
            }
        }

        // Update health kits
        for (int i = healthKits.size() - 1; i >= 0; i--) {
            HealthKit healthKit = healthKits.get(i);
            healthKit.update(delta);
            if (healthKit.y + healthKitTexture.getHeight() < 0) {
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

        // Update boss projectiles
        for (int i = Bossprojectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = Bossprojectiles.get(i);
            projectile.update(delta);
            if (projectile.y + projectileTexture.getHeight() < 0) {
                Bossprojectiles.remove(i);
            }
        }
        // Update explosions
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.update(delta);
            if (explosion.remove) {
                explosionIterator.remove();
            }
        }
        // Check for collisions
        checkCollisions();

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

        // Check if score is a multiple of 10 and add health kit
        if (score % 10 == 0 && score != 0 && !healthKits.stream().anyMatch(kit -> kit.y > 0)) {
            float healthKitY = MyGdxGame.HEIGHT;
            float healthKitX = MathUtils.random(0, MyGdxGame.WIDTH - 50); // Random X position
            healthKits.add(new HealthKit(healthKitX, healthKitY, 200)); // Adjust speed as needed
        }

        // Rendering
        game.batch.begin();
        game.batch.draw(img, 0, bg_y1);
        game.batch.draw(img, 0, bg_y2);
        game.batch.draw(ship, x, y, 120, 125);

        // Draw enemies
        for (Enemy enemy : enemies) {
            game.batch.draw(enemyTexture, enemy.x, enemy.y, 150, 70);
        }

        // Draw enemy projectiles
        for (Projectile projectile : projectiles) {
            game.batch.draw(projectileTextureEnemy, projectile.x + 10, projectile.y, 40, 55);
        }

        // Draw ship projectiles
        for (Projectile projectile : shipProjectiles) {
            game.batch.draw(projectileTextureShip, projectile.x, projectile.y, 50, 70);
        }


        // Draw health kits
        for (HealthKit healthKit : healthKits) {
            game.batch.draw(healthKitTexture, healthKit.x, healthKit.y, 70, 50); // Adjust size as needed
        }

        // Draw boss
        if (bossActive) {
            game.batch.draw(bossTexture, boss.x, boss.y, 200, 180); // Adjust size as needed

        }

        // Draw boss projectiles
        for (Projectile projectile : Bossprojectiles) {
            game.batch.draw(projectileTexture, projectile.x + 10, projectile.y, 20, 20);
        }
        //explosion
        for (Explosion explosion : explosions) {
            explosion.render(game.batch);
        }
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
        if(bossActive)
        {
            float bossHealthBarWidth = 100;
            float bossHealthBarHeight = 10;
            float bossHealthPercentage = boss.health / 30f; // Assuming 30 is the max health of the boss
            float currentBossHealthBarWidth = bossHealthBarWidth * bossHealthPercentage;
            float bossHealthBarX = boss.x + 200/ 2f -50;
            float bossHealthBarY = boss.y + 150; // Position above the boss

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 0, 1); // Red color for background
            shapeRenderer.rect(bossHealthBarX, bossHealthBarY, bossHealthBarWidth, bossHealthBarHeight);
            shapeRenderer.setColor(0, 1, 0, 1); // Green color for health
            shapeRenderer.rect(bossHealthBarX, bossHealthBarY, currentBossHealthBarWidth, bossHealthBarHeight);
            shapeRenderer.end();
        }
        game.batch.begin();
        GlyphLayout scoreLayout = new GlyphLayout(sfont2, "Score: " + score);
        GlyphLayout healthLayout = new GlyphLayout(sfont, "Life:" );
        sfont2.draw(game.batch, scoreLayout, MyGdxGame.WIDTH - 250, MyGdxGame.HEIGHT - 20);
        sfont.draw(game.batch, healthLayout, 10, MyGdxGame.HEIGHT - 20);
        game.batch.end();
    }

    private void checkCollisions() {
        // Create rectangles for the ship and enemies
        Rectangle shipRect = new Rectangle(x, y, ship.getWidth() - 250, ship.getHeight() - 300);

        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            Rectangle enemyRect = new Rectangle(enemy.x, enemy.y, enemyTexture.getWidth() - 600, enemyTexture.getHeight() - 400);

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
                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, projectileTextureEnemy.getWidth() - 200, projectileTextureEnemy.getHeight() - 170);
                if (projectileRect.overlaps(shipRect)) {
                    enemyProjectileIterator.remove();
                    explosions.add(new Explosion(projectile.x, projectile.y));
                    if (!gameSound.explosion.isPlaying()){
                        gameSound.explosion.play();
                    }
                    health--;
                    if (health <= 0) {
                        this.dispose();
                        game.setScreen(new GameOver(game,score));
                        gameSound.theme.stop();
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
                health += 5;
                health=min(30,health);

                break;
            }
        }

        // Check for collision between ship projectiles and boss
        if (bossActive) {
            Rectangle bossRect = new Rectangle(boss.x, boss.y, bossTexture.getWidth(), bossTexture.getHeight()); // Adjust size as needed
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
            // Check for collision between boss projectiles and the ship
            Iterator<Projectile> bossProjectileIterator =Bossprojectiles.iterator();
            while (bossProjectileIterator.hasNext()) {
                Projectile projectile = bossProjectileIterator.next();
                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, projectileTexture.getWidth() , projectileTexture.getHeight() );
                if (projectileRect.overlaps(shipRect)) {
                    bossProjectileIterator.remove();
                    explosions.add(new Explosion(projectile.x, projectile.y));
                    if (!gameSound.explosion.isPlaying()){
                        gameSound.explosion.play();
                    }
                    health-=5;
                    if (health <= 0) {
                        this.dispose();
                        game.setScreen(new GameOver(game,score));
                        gameSound.theme.stop();
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
