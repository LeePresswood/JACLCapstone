package com.jacl.capstone.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class should be used to load our assets.<br><br>
 * 
 * To load assets:<br>
 * load(<file_name>, <asset_type>.class);
 * 
 * To retrieve them:<br>
 * get(<file_name>, <asset_type>.class);
 * 
 * Various String constants will be added to allow
 * for calling of file names in a cleaner fashion.
 * 
 * @author Lee
 *
 */
public class Assets extends AssetManager
{
	public Assets()
	{
		//Audio
		
		
		//Fonts
		
		
		//Textures
		load("image.png", Texture.class);
	}
}
