package com.jacl.capstone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.data.GameConstants;

public class DesktopLauncher {
	public static final String GAME_NAME = "Game";		//Use this to write the name of the game in the UI box.
	public static final String GAME_VERSION = "0.2 Alpha";	/* Use this to determine the version of the game in V.U format. 
								 * Example: Version 1 - Update 4 would be version "1.4"
								 * Increment the Update number frequently (weekly?) and the Version
								 * number only when a major feature addition has been made.
								 * Version number should be 0 until first working version
								 * of the game has been produced. Another name for this is "Alpha".
								 * Also note that "0.1" and "0.10" are different things.
								 */
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = (int) (9f / 16f * GAME_WIDTH);
	
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = GameConstants.GAME_NAME + " : " + GameConstants.GAME_VERSION;		
		config.width = GameConstants.GAME_WIDTH;
		config.height = GameConstants.GAME_HEIGHT;
		config.resizable = false;
		
		//config.addIcon(null, null);
		
		new LwjglApplication(new CapstoneGame(), config);
	}
}
