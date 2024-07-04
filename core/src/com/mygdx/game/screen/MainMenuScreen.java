package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class MainMenuScreen implements Screen {
    MyGdxGame game;
    Texture pic, button1, inbutton1, button2, inbutton2;
    Texture about1, about2, ins1, ins2;
    Texture spaceship;
    int spaceshipCount = 15;
    float[] spaceshipX = new float[spaceshipCount];
    float[] spaceshipY = new float[spaceshipCount];
    float[] spaceshipSpeedX = new float[spaceshipCount];
    float[] spaceshipSpeedY = new float[spaceshipCount];

    public MainMenuScreen (MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        pic = new Texture("MainMenu/MenuScreen.jpg");
        button1 = new Texture("MainMenu/In_PlayButton.png");
        inbutton1 = new Texture("MainMenu/Ac_PlayButton.png");
        button2 = new Texture("MainMenu/In_ExitButton.png");
        inbutton2 = new Texture("MainMenu/Ac_ExitButton.png");
        about1 = new Texture("MainMenu/In_AboutButton.png");
        about2 = new Texture("MainMenu/Ac_AboutButton.png");
        ins1 = new Texture("MainMenu/In_instructionButton.png");
        ins2 = new Texture("MainMenu/Ac_instructionButton.png");
        spaceship = new Texture("MainMenu/dot.png");

        for (int i = 0; i < spaceshipCount; i++) {
            spaceshipX[i] = MathUtils.random(0, MyGdxGame.WIDTH - spaceship.getWidth());
            spaceshipY[i] = MathUtils.random(0, MyGdxGame.HEIGHT - spaceship.getHeight());
            spaceshipSpeedX[i] = MathUtils.random(-200, 200) * Gdx.graphics.getDeltaTime();
            spaceshipSpeedY[i] = MathUtils.random(-200, 200) * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        // Update spaceship positions
        for (int i = 0; i < spaceshipCount; i++) {
            spaceshipX[i] += spaceshipSpeedX[i];
            spaceshipY[i] += spaceshipSpeedY[i];

            // Bounce spaceships off the edges
            if (spaceshipX[i] < 0 || spaceshipX[i] > MyGdxGame.WIDTH - spaceship.getWidth()) {
                spaceshipSpeedX[i] = -spaceshipSpeedX[i];
            }
            if (spaceshipY[i] < 0 || spaceshipY[i] > MyGdxGame.HEIGHT - spaceship.getHeight()) {
                spaceshipSpeedY[i] = -spaceshipSpeedY[i];
            }
        }

        int button1X = 427;
        int button1Y = 343;
        int button1Width = 428;
        int button1Height = 112;
        int button2X = 522;
        int button2Y = 222;
        int button2Width = 253;
        int button2Height = 96;
        int button3X = 1069;
        int button3Y = 12;
        int button3Width = 210;
        int button3Height = 36;

        // Check if the mouse is over button1
        boolean isButton1 = Gdx.input.getX() >= button1X && Gdx.input.getX() <= button1X + button1Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button1Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button1Y + button1Height;
        boolean isButton2 = Gdx.input.getX() >= button2X && Gdx.input.getX() <= button2X + button2Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button2Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button2Y + button2Height;
        boolean isButton3 = Gdx.input.getX() >= button3X && Gdx.input.getX() <= button3X + button3Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button3Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button3Y + button3Height;
        boolean isButton4 = Gdx.input.getX() >= 8 && Gdx.input.getX() <= 8 + 210 &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= 15 && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= 15 + 41;

        game.batch.begin();
        game.batch.draw(pic, 0, 0);

        if (isButton1) {
            game.batch.draw(inbutton1, button1X, button1Y, button1Width, button1Height); // Draw hover state texture
            if (Gdx.input.isTouched()) {
               // game.setScreen(new gameScreen(game));
            }
        } else {
            game.batch.draw(button1, button1X, button1Y, button1Width, button1Height); // Draw normal texture
        }

        if (isButton2) {
            game.batch.draw(inbutton2, button2X, button2Y, button2Width, button2Height); // Draw hover state texture
        } else {
            game.batch.draw(button2, button2X, button2Y, button2Width, button2Height); // Draw normal texture
        }

        if (isButton3) {
            game.batch.draw(about2, button3X, button3Y + 1, button3Width, button3Height); // Draw hover state texture
            if (Gdx.input.isTouched()) {
                //game.setScreen(new About(game));
            }
        } else {
            game.batch.draw(about1, button3X, button3Y, button3Width, button3Height); // Draw normal texture
        }

        if (isButton4) {
            game.batch.draw(ins2, 8, 15, 210, 41); // Draw hover state texture
            if (Gdx.input.isTouched()) {
               // game.setScreen(new Instruction(game));
            }
        } else {
            game.batch.draw(ins1, 8, 15, 210, 41); // Draw normal texture
        }

        // Draw the spaceships
        for (int i = 0; i < spaceshipCount; i++) {
            game.batch.draw(spaceship, spaceshipX[i], spaceshipY[i]);
        }

        game.batch.end();
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
        pic.dispose();
        button1.dispose();
        inbutton1.dispose();
        inbutton2.dispose();
        button2.dispose();
        spaceship.dispose(); // Dispose the spaceship texture
    }
}
