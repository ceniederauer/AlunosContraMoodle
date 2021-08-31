package com.asteroids.game;

import com.asteroids.game.screens.GameOverScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.asteroids.game.screens.MainMenuScreen;

public class fimDeSemestre extends Game {
        public static final int WIDTH = 480;
        public static final int HEIGHT = 720;
        public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
