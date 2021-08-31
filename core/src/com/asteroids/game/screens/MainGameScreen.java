package com.asteroids.game.screens;

import com.asteroids.game.fimDeSemestre;
import com.asteroids.game.entities.Bullet;
import com.asteroids.game.entities.Asteroids;
import com.asteroids.game.entities.Explosion;
import com.asteroids.game.tools.CollisionRect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements Screen {

	public static final float SPEED = 1000;
	public static final float ANIMATION_SPEED = 0.5f;
	public static final int SHIP_WIDTH_PIXEL = 17;
	public static final int SHIP_HEIGHT_PIXEL = 33;
	public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
	public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
	public static final float ROLL_TIMER_SWITCH_TIME = 0.10f;
	public static final float SHOOT_WAIT_TIME = 0.3f;
	public static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;
	public static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;

	Animation<TextureRegion>[] rolls;

	float x;
	float y;
	int roll;
	float scoreAsteroidTimer;
	float stateTime;
	float rollTimer;
	float shootTimer;
	float asteroidSpawnTimer;
	
	fimDeSemestre game;
	Texture background;
	Random random;
	
	ArrayList<Bullet> bullets;
	ArrayList<Asteroids> asteroids;
	ArrayList<Explosion> explosions;

	BitmapFont scoreFont;

	int score;
	Texture blank;
	CollisionRect playerRect;
	float health = 1;

	public MainGameScreen(fimDeSemestre game) {
		this.game = game;
		background = new Texture("fundo.png");
		y = 15;
		x = fimDeSemestre.WIDTH / 2 - SHIP_WIDTH / 2;
	
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroids>();
		explosions = new ArrayList<Explosion>();
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

		score = 0;
		playerRect = new CollisionRect(0, 0, SHIP_WIDTH, SHIP_HEIGHT);

		blank = new Texture("blank.png");

		random = new Random();
		asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME)
				+ MIN_ASTEROID_SPAWN_TIME;
		shootTimer = 0;
		roll = 2;
		rollTimer = 0;
		rolls = new Animation[5];

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("sprites.png"), SHIP_WIDTH_PIXEL,
				SHIP_HEIGHT_PIXEL);

		rolls[0] = new Animation<TextureRegion>(ANIMATION_SPEED, rollSpriteSheet[0]);
		rolls[1] = new Animation<TextureRegion>(ANIMATION_SPEED, rollSpriteSheet[1]);
		rolls[2] = new Animation<TextureRegion>(ANIMATION_SPEED, rollSpriteSheet[2]);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		shootTimer += delta;
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
			shootTimer = 0;
			bullets.add(new Bullet(x - 14));
			bullets.add(new Bullet(x + SHIP_WIDTH - 14));
		}

		asteroidSpawnTimer -= delta;
		if (asteroidSpawnTimer <= 0) {
			asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME)
					+ MIN_ASTEROID_SPAWN_TIME - scoreAsteroidTimer;
			asteroids.add(new Asteroids(random.nextInt(Gdx.graphics.getWidth() - Asteroids.WIDTH)));
		}


		ArrayList<Asteroids> asteroidsToRemove = new ArrayList<Asteroids>();
		for (Asteroids asteroid : asteroids) {
			asteroid.update(delta);
			if (asteroid.remove)
				asteroidsToRemove.add(asteroid);
		}
		asteroids.removeAll(asteroidsToRemove);
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets) {
			bullet.update(delta);
			if (bullet.remove)
				bulletsToRemove.add(bullet);
		}
		ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
		for (Explosion explosion : explosions) {
			explosion.update(delta);
			if (explosion.remove)
				explosionsToRemove.add(explosion);
		}
		explosions.removeAll(explosionsToRemove);

		for (Asteroids asteroid : asteroids) {
			if (asteroid.getCollisionRect().collidesWith(playerRect)) {
				asteroidsToRemove.add(asteroid);
				health -= 0.1;

				if(health <= 0) {
					this.dispose();
					game.setScreen(new GameOverScreen(game, score));
					return;
				}
			}
		}
		asteroids.removeAll(asteroidsToRemove);
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			x -= SPEED * Gdx.graphics.getDeltaTime();
			if (x < 0) {

				x = 0;
			}

			if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
				rollTimer = 0;
				roll = 1;
			}
			rollTimer -= Gdx.graphics.getDeltaTime();

		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x += SPEED * Gdx.graphics.getDeltaTime();
			if (x + SHIP_WIDTH > Gdx.graphics.getWidth()) {
				x = Gdx.graphics.getWidth() - SHIP_WIDTH;
			}
			rollTimer += Gdx.graphics.getDeltaTime();
			if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME) {
				rollTimer = 0;
				roll = 2;

			}

		} else {
			if (roll != 0) {
				rollTimer -= Gdx.graphics.getDeltaTime();
				if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME) {
					rollTimer = 0;
					roll = 0;
				}
			}
		}

		for (Bullet bullet : bullets) {
			for (Asteroids asteroids : asteroids) {
				if (bullet.getCollisionRect().collidesWith(asteroids.getCollisionRect())) {// Collision occured
					bulletsToRemove.add(bullet);
					asteroidsToRemove.add(asteroids);
					explosions.add(new Explosion(asteroids.getX(),asteroids.getY()));
					score += 100;
					scoreAsteroidTimer += 0.006f;
				}
			}
		}
		bullets.removeAll(bulletsToRemove);
		asteroids.removeAll(asteroidsToRemove);

		stateTime += delta;

		Gdx.gl.glClearColor(0,0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
		
		game.batch.draw(background, 0, 0, game.WIDTH, game.HEIGHT);
		scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2,
				Gdx.graphics.getHeight() - scoreLayout.height - 10);
		playerRect.move(x, y);
		for (Bullet bullet : bullets) {
			bullet.render(game.batch);
		}
		for (Asteroids asteroid : asteroids) {
			asteroid.render(game.batch);
		}
		for (Explosion explosion : explosions) {
			explosion.render(game.batch);
		}

		if (health > 0.6f)
			game.batch.setColor(Color.GREEN);
		else if (health > 0.2f)
			game.batch.setColor(Color.ORANGE);
		else
			game.batch.setColor(Color.RED);

		game.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * health, 5);
		game.batch.setColor(Color.WHITE);
		game.batch.draw(rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);

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