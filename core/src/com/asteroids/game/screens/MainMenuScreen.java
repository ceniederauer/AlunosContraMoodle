/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Lucas Caetano
 */
package com.asteroids.game.screens;

import com.asteroids.game.fimDeSemestre;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_Y = 300;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int TITLE_WIDTH = 300;
    private static final int TITLE_HEIGHT = 150;
    private static final int TITLE_Y = 450;

    fimDeSemestre game;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture titleScreen;
    Texture background;

    public MainMenuScreen(fimDeSemestre game) {
        this.game = game;
        background = new Texture("fundoMenu.png");
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        titleScreen = new Texture("TitleScreen2.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(30, 50, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, game.WIDTH, game.HEIGHT);
        int x = fimDeSemestre.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        game.batch.draw(titleScreen, x, TITLE_Y, TITLE_WIDTH, TITLE_HEIGHT);
        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && fimDeSemestre.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && fimDeSemestre.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {
            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        x = fimDeSemestre.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && fimDeSemestre.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && fimDeSemestre.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {
            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
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
    }
}
