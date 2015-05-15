package com.jacl.capstone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.data.GameConstants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = GameConstants.GAME_NAME + " : " + GameConstants.GAME_VERSION;		
		config.width = GameConstants.GAME_WIDTH;
		config.height = GameConstants.GAME_HEIGHT;
		config.resizable = false;
		
		//config.addIcon(null, null);
		
		new LwjglApplication(new CapstoneGame(), config);
	}
}
