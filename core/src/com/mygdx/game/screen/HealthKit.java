package com.mygdx.game.screen;

public class HealthKit {
    float x, y;
    float speed;

    public HealthKit(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update(float delta) {
        y -= speed * delta;
    }
}