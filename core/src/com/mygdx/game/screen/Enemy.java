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

