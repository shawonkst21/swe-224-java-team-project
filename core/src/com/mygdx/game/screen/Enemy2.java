package com.mygdx.game.screen;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.screen.GameScreen3.enemyTexture;
import static com.mygdx.game.screen.GameScreen3.projectiles;

public class Enemy2 {
    float x, y;
    float speed;
    boolean canFire;
    boolean hasFired;
    float fireCooldown;
    float fireRate = 1.5f; // Adjusted fire rate

    public Enemy2(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.canFire = false;
        this.hasFired = false; // Track if the enemy has fired
        this.fireCooldown = MathUtils.random(0.5f, 2.0f); // Initial random cooldown
    }

    public void update(float delta) {
        x -= speed * delta;
        if (x + enemyTexture.getWidth() < 0) { // Check if off-screen
            reset();
        }

        // Update firing cooldown
        fireCooldown -= delta;
        if (fireCooldown <= 0) {
            canFire = true;
            fireCooldown = fireRate; // Reset cooldown
        }

        if (canFire && !hasFired) {
            fireProjectile();
            canFire = false; // Only fire once per cooldown
            hasFired = true; // Set hasFired to true after firing
        }
    }

    public void reset() {
        x = MyGdxGame.WIDTH + MathUtils.random(200, 800); // Reset to right side
        y = MathUtils.random(0, MyGdxGame.HEIGHT - enemyTexture.getHeight()); // Random Y position
        fireCooldown = MathUtils.random(0.5f, 2.0f); // Reset cooldown
        hasFired = false; // Reset the hasFired flag
    }

    void fireProjectile() {
        projectiles.add(new Projectile(x, y));
    }
}
