package com.advancewarsmobile.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.advancewarsmobile.game.AdvanceWarsMobile;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Micro Wars";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new AdvanceWarsMobile(), config);
	}
}
