
package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class GameOver implements Screen {
    MyGdxGame game;
    Texture win, lose;
    BitmapFont font;
    boolean check = false;
    int score, highScore;

    public GameOver(MyGdxGame game, int score) {
        this.game = game;
        this.score = score;
        Preferences prefs = Gdx.app.getPreferences("SPACE SHOOTER");
        this.highScore = prefs.getInteger("highScore", 0);

        if (score > highScore) {
            prefs.putInteger("highScore", score);
            prefs.flush();
            check = true;
        }

        win = new Texture("win.png");
        lose = new Texture("lose.png");
        font = new BitmapFont(Gdx.files.internal("font/score.fnt")); // Load your font
        //font.getData().setScale(.9f); // Scale the font if needed
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        game.batch.begin();

        // Draw the background image (win or lose)
        if(check)
        {
            game.batch.draw(win, 0,0);
        }
        else {
            game.batch.draw(lose, 0, 0);

        }

        // Prepare text layouts
        GlyphLayout scoreLayout = new GlyphLayout(font, "" + score);
        GlyphLayout highScoreLayout = new GlyphLayout(font, "" + highScore);

        // Draw the scores
        if(check)
        {
            font.draw(game.batch, scoreLayout, ((MyGdxGame.WIDTH - scoreLayout.width) / 2)+10, 215);
            font.draw(game.batch, highScoreLayout, ((MyGdxGame.WIDTH - highScoreLayout.width) / 2)+10, 137);
        }
        else
        {
            font.draw(game.batch, scoreLayout, ((MyGdxGame.WIDTH - scoreLayout.width) / 2)+10, 210);
            font.draw(game.batch, highScoreLayout, ((MyGdxGame.WIDTH - highScoreLayout.width) / 2)+10, 132);
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
        win.dispose();
        lose.dispose();
        font.dispose(); // Dispose of the font
    }
}
