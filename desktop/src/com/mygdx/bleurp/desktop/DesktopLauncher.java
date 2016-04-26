package com.mygdx.bleurp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.bleurp.Bleurp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Bleurp.WIDTH;
        config.height = Bleurp.HEIGHT;
		new LwjglApplication(new Bleurp(), config);
	}
}
