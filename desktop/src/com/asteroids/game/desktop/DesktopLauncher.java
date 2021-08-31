package com.asteroids.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.asteroids.game.fimDeSemestre;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
                config.width = fimDeSemestre.WIDTH;
                config.height = fimDeSemestre.HEIGHT;
                config.resizable = false;
                new LwjglApplication(new fimDeSemestre(), config);
	}
}
