package com.jacl.capstone.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class should be used to load our assets.<br><br>
 * 
 * To retrieve them:<br>
 * load(<file_name>, <asset_type>.class);
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
		load("texture.jpg", Texture.class);
	}
}