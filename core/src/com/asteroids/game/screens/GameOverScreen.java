package com.asteroids.game.screens;

import com.asteroids.game.fimDeSemestre;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

public class GameOverScreen implements Screen {

    private static final int BANNER_HEIGHT = 100;


    fimDeSemestre game;

    int score, highscore;
    Texture background;
    BitmapFont scoreFont;

    public GameOverScreen (fimDeSemestre game, int score) {
        this.game = game;
        background = new Texture("fundoMenu.png");
        this.score = score;

        Preferences prefs = Gdx.app.getPreferences("asteroidParadigms");
        this.highscore = prefs.getInteger("highscore", 0);

        if (score > highscore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, game.WIDTH, game.HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "SCORE: \n" + score, Color.WHITE, 0, Align.center, true);
        GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "HIGHSCORE: \n" + highscore, Color.WHITE, 0, Align.center, true);
        scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT );
        scoreFont.draw(game.batch, highscoreLayout, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - scoreLayout.height - 15 * 3);


        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "JOGAR DENOVO");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "MENU INICIAL");

        float tryAgainX = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width /2;
        float tryAgainY = Gdx.graphics.getHeight() /2 - tryAgainLayout.height /2;
        float mainMenuX = Gdx.graphics.getWidth() /2 - mainMenuLayout.width /2;
        float mainMenuY = Gdx.graphics.getHeight() /2 - mainMenuLayout.height /2 - tryAgainLayout.height - 30;

        float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if(Gdx.input.isTouched()) {
            if(touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                return;
            }
            if(touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }

        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

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

    }
}
