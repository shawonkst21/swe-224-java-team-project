package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sound.gameSound;

public class GameMode implements Screen {
    MyGdxGame game;
    Texture button1,button2,button3,button4;
    Texture img;
    public static boolean check=true;
    Texture spaceship;
    int spaceshipCount = 15;
    float[] spaceshipX = new float[spaceshipCount];
    float[] spaceshipY = new float[spaceshipCount];
    float[] spaceshipSpeedX = new float[spaceshipCount];
    float[] spaceshipSpeedY = new float[spaceshipCount];
    public GameMode(MyGdxGame game)
    {
        this.game=game;
        img=new Texture("gameMode/mode.png");
        button1=new Texture("gameMode/modeButton1.png");
        button2=new Texture("gameMode/modeButton2.png");
        button3=new Texture("gameMode/modeButton3.png");
        button4=new Texture("gameMode/modeButton4.png");
        spaceship = new Texture("MainMenu/dot.png");

        for (int i = 0; i < spaceshipCount; i++) {
            spaceshipX[i] = MathUtils.random(0, MyGdxGame.WIDTH - spaceship.getWidth());
            spaceshipY[i] = MathUtils.random(0, MyGdxGame.HEIGHT - spaceship.getHeight());
            spaceshipSpeedX[i] = MathUtils.random(-200, 200) * Gdx.graphics.getDeltaTime();
            spaceshipSpeedY[i] = MathUtils.random(-200, 200) * Gdx.graphics.getDeltaTime();
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,0);
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
        int button1X = 306;
        int button1Y = 12;
        int button1Width = 232;
        int button1Height = 248;
        int button2X = 746;
        int button2Y = 12;
        int button2Width = 231;
        int button2Height = 253;
        int button3X = 8;
        int button3Y = 209;
        int button3Width = 429;
        int button3Height = 368;
        int button4X = 837;
        int button4Y = 187;
        int button4Width = 431;
        int button4Height = 361;
        boolean isButton1 = Gdx.input.getX() >= button1X && Gdx.input.getX() <= button1X + button1Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button1Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button1Y + button1Height;
        boolean isButton2 = Gdx.input.getX() >= button2X && Gdx.input.getX() <= button2X + button2Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button2Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button2Y + button2Height;

        game.batch.begin();
        game.batch.draw(img,0,0);
        game.batch.draw(button1, button1X, button1Y, button1Width, button1Height);//button1
        game.batch.draw(button2, button2X, button2Y, button2Width, button2Height);//buttton2

        if(isButton1)
        {
            game.batch.draw(button3, button3X, button3Y, button3Width, button3Height);//buttton3
            if(Gdx.input.isTouched())
            {

                check=true;
                game.setScreen(new LoadingScreen(game));

            }

        }
        if(isButton2)
        {
            game.batch.draw(button4, button4X, button4Y, button4Width, button4Height);//buttton4
            if(Gdx.input.isTouched())
            {
                check=false;
                game.setScreen(new LoadingScreen(game));
            }
        }
        // Draw the spaceships
        for (int i = 0; i < spaceshipCount; i++) {
            game.batch.draw(spaceship, spaceshipX[i], spaceshipY[i]);
        }
        game.batch.end();


    }

    @Override
    public void resize(int i, int i1) {

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
    }
}
