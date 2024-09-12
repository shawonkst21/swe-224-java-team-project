package com.mygdx.game.screen;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.screen.GameScreen3.Bossprojectiles;

public class Boss2 {
    float x, y;
    float speed;
    int health;
    private float fireRate = 1.0f; // Boss fires every 1 second
    private float timeSinceLastFire = 0;

    public Boss2(float x, float y, float speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
    }

    public void reset() {
        this.x = MyGdxGame.WIDTH + 100;
        this.y = MathUtils.random(0, MyGdxGame.HEIGHT - 100);
        this.health = 30; // or initial health value
        timeSinceLastFire = 0; // Reset fire timer
        System.out.println("Boss Reset: X=" + x + ", Y=" + y + ", Health=" + health);
    }

    public void update(float delta, float playerX, float playerY) {
        // Example logic for boss movement, can be adjusted as needed
        if (x > playerX) {
            x -= speed * delta; // Move left towards player
        }
        if (y < playerY) {
            y += speed * delta; // Move up towards player
        } else if (y > playerY) {
            y -= speed * delta; // Move down towards player
        }
        System.out.println("Boss Updated: X=" + x + ", Y=" + y + ", Health=" + health);

        // Handle auto-firing
        timeSinceLastFire += delta;
        if (timeSinceLastFire >= fireRate) {
            fire();
            timeSinceLastFire = 0; // Reset fire timer
        }
    }

    private void fire() {
        // Adjust the position and speed of the boss projectile as needed
        Bossprojectiles.add(new Projectile2(x, y + 50, -300));
        //System.out.println("Boss Fired: X=" + x + ", Y=" + y);
    }
}
