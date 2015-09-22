package com.jacl.capstone.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * This class should be used to load our assets.<br><br>
 * 
 * To load assets:<br>
 * load(<file_name>, <asset_type>.class);<br><br>
 * 
 * To retrieve them:<br>
 * get(<file_name>, <asset_type>.class);<br><br>
 * 
 * Various String constants will be added to allow
 * for calling of file names in a cleaner fashion.
 * @author Lee
 */
public class Assets extends AssetManager
{
	public static final String PLAYER = "image.png";
	public static final String HEALTHBAR_BACKGROUND = "hud/healthbar/health-red.png";
	public static final String HEALTHBAR_FOREGROUND = "hud/healthbar/health-blue.png";
	
	public static final String FONT16 = "hud/fonts/font16.fnt";
	public static final String FONT24 = "hud/fonts/font24.fnt";
	public static final String FONT32 = "hud/fonts/font32.fnt";
	public static final String FONT44 = "hud/fonts/font44.fnt";
	public static final String FONT56 = "hud/fonts/font56.fnt";
	
	public Assets()
	{
		/* Note: These will all be loaded asynchronously.
		 * Trying to grab these assets before they are
		 * fully loaded will result in an error and
		 * a crash.
		 * 
		 * To fix this, we will want to either
		 * finishLoading() at the end of this constructor 
		 * or create a loading screen screen that shows
		 * the progress of our assets being loaded.
		 *
		 * For this project, the second option will be
		 * utilized.
		 */
		//Load different assets.
		//Audio
		
		
		//Fonts
		load(FONT16, BitmapFont.class);
		load(FONT24, BitmapFont.class);
		load(FONT32, BitmapFont.class);
		load(FONT44, BitmapFont.class);
		load(FONT56, BitmapFont.class);
		
		//Textures
		load(PLAYER, Texture.class);
		load(HEALTHBAR_BACKGROUND, Texture.class);
		load(HEALTHBAR_FOREGROUND, Texture.class);
		
		finishLoading();
	}
}
