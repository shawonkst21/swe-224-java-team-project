package com.mygdx.game.screen;

public class Projectile {
    float x, y;
    float speed;
    boolean fromShip;

    public Projectile(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed; // Adjust speed as needed
        this.fromShip = true; // Check if projectile is from ship
    }
    public Projectile(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 400; // Adjust speed as needed
        this.fromShip = false; // Check if projectile is from enemy
    }

    public void update(float delta) {
        if (fromShip) {
            y += speed * delta; // Update projectile position for ship (moving upwards)
        } else {
            y -= speed * delta; // Update projectile position for enemy (moving downwards)
        }
    }
}
