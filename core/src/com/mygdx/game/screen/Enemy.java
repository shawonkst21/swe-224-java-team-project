package com.mygdx.game.screen;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.screen.GameScreen2.enemyTexture;
import static com.mygdx.game.screen.GameScreen2.projectiles;

public class Enemy {
    public  float x;
    public float y;
    float speed;
    boolean canFire;
    boolean hasFired;
    float fireCooldown;
    float fireRate = 1.5f; // Adjusted fire rate

    public Enemy(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.canFire = false;
        this.hasFired = false; // Track if the enemy has fired
        this.fireCooldown = MathUtils.random(0.5f, 2.0f); // Initial random cooldown
    }

    public void update(float delta) {
        y -= speed * delta; // Move downwards

        // Reset if off-screen
        if (y < -enemyTexture.getHeight()) {
            reset();
        }

        // Update firing cooldown
        fireCooldown -= delta;
        if (fireCooldown <= 0) {
            canFire = true;
            fireCooldown = fireRate; // Reset cooldown
        }

        // Fire projectile if able to fire and hasn't fired yet
        if (canFire && !hasFired) {
            fireProjectile();
            canFire = false; // Only fire once per cooldown
            hasFired = true; // Set hasFired to true after firing
        }
    }

    public void reset() {
        int temp=MathUtils.random(0, MyGdxGame.WIDTH - enemyTexture.getWidth());
        if(temp%200==0)
        {
            x=temp;
        }
        y = MyGdxGame.HEIGHT + MathUtils.random(200, 800); // Reset to top of the screen
        fireCooldown = MathUtils.random(0.5f, 2.0f); // Random initial cooldown
        hasFired = false; // Reset firing status
    }

    void fireProjectile() {
        projectiles.add(new Projectile2(x, y)); // Create a projectile at enemy's position
    }
}