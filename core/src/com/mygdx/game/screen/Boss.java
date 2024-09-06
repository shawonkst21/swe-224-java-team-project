
package com.mygdx.game.screen;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.screen.gameScreen2.Bossprojectiles;

public class Boss {
    static float x;
    static float y;
    float speed;
    public static int health;
    private float fireRate = 1.0f; // Boss fires every 1 second
    private float timeSinceLastFire = 0;

    public Boss(float x, float y, float speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
    }

    public void reset() {
        this.x = MathUtils.random(0, MyGdxGame.WIDTH - 100); // Random X position
        this.y = MyGdxGame.HEIGHT + 100; // Start above the screen
        this.health = 30; // or initial health value
        timeSinceLastFire = 0; // Reset fire timer
    }

    public void update(float delta, float playerX, float playerY) {
        if(y<MyGdxGame.HEIGHT/2)
        {
            y+=speed*delta;
        }

        y -= speed * delta;

        if (x < playerX) {
            x += speed * delta; // Move right towards player
        } else if (x > playerX) {
            x -= speed * delta; // Move left towards player
        }


        // Handle auto-firing
        timeSinceLastFire += delta;
        if (timeSinceLastFire >= fireRate) {
            fire();
            timeSinceLastFire = 0; // Reset fire timer
        }
    }

    private void fire() {
        Bossprojectiles.add(new Projectile2(x + 75, y, -300)); // Center the projectile relative to the boss
    }
}
