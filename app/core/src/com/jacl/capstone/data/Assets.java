package com.jacl.capstone.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

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
		
		
		//Textures
		load("image.png", Texture.class);
		
		finishLoading();
	}
}
