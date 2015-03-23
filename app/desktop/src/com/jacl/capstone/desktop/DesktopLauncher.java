package com.jacl.capstone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.data.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = Constants.GAME_NAME + " : " + Constants.GAME_VERSION;
		
		config.width = 1080;
		config.height = 670;
		config.resizable = false;
		
		//config.addIcon(null, null);
		
		new LwjglApplication(new CapstoneGame(), config);
	}
}
