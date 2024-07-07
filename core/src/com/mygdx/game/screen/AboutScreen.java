/*package com.mygdx.game.screen;

public class AboutScreen {
}*/
package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class AboutScreen implements Screen {
    MyGdxGame game;
    Texture about;
    Texture back1,back2;
    public AboutScreen(MyGdxGame game)
    {
        this.game=game;
        about=new Texture("AboutScreen/preparedBY.png");
        back1=new Texture("AboutScreen/In_BackButon.png");
        back2=new Texture("AboutScreen/Ac_Backbutton.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 0);
        int button5X = 11;
        int button5Y = 651;
        int button5Width = 135;
        int button5Height = 56;

        boolean isBack = Gdx.input.getX() >= button5X && Gdx.input.getX() <= button5X + button5Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button5Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button5Y + button5Height;
        game.batch.begin();
        game.batch.draw(about,0,0);

        if (isBack) {
            game.batch.draw(back2, button5X, button5Y, button5Width, button5Height);// Draw hover state texture
            if(Gdx.input.isTouched())
            {
                game.setScreen(new MainMenuScreen(game));

            }
        } else {
            game.batch.draw(back1, button5X, button5Y, button5Width, button5Height); // Draw normal texture
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
        about.dispose();
        back2.dispose();
        back1.dispose();
    }
}
