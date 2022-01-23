package com.advancewarsmobile.game.desktop;


import com.advancewarsmobile.game.AdvanceWarsMobile;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Micro Wars");
		config.setWindowSizeLimits(480, 800, -1, -1);
		new Lwjgl3Application(new AdvanceWarsMobile(), config);
	}
}
