package com.mygdx.game.screen;

public class Healthkit2 {
    float x, y;
    float speed;

    public Healthkit2(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update(float delta) {
        x -= speed * delta;
    }
}
