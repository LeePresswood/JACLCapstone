package com.jacl.capstone.screens.game.world.sector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Idea behind the camera:
 * The world map should be divided into sectors to avoid rendering the whole map at once.
 * The camera will follow the player while still staying within the bounds of the sector.
 * Thus, the sector's bounds take precedence.
 * 
 * The camera will always show the same size of screen. This is regardless of screen resolution.
 * To do this, we must determine how many tiles we want the screen to show and stretch this into
 * the screen's resolution.
 * 
 * Because most modern screens are 16:9, we should go for as close as as we can with our
 * defined width:height ratio.
 * 
 * @author Lee
 */
public class SectorCamera extends OrthographicCamera
{
	//Define how many tiles we want to see. Define one in terms of the other so that we only need to change one in the future.
	public final float TILES_HORIZONTAL = 35f;
	public final float TILES_VERTICAL = TILES_HORIZONTAL * 9f / 16f;
	
	//Each tile will be a certain width/height. This is defined in the map file.
	public final float TILE_SIZE;

	//The map will also define the width and height of the map in tiles.
	public final int TILES_TOTAL_HORIZONTAL, TILES_TOTAL_VERTICAL;
	
	public SectorCamera(TiledMap map)
	{
		super();
		
		//Read bounds and sizes of map.
		System.out.println(map.getProperties().get("Size"));
		TILE_SIZE = (Float) map.getProperties().get("tile_size");
		TILES_TOTAL_HORIZONTAL = (Integer) map.getProperties().get("world_width");
		TILES_TOTAL_VERTICAL = (Integer) map.getProperties().get("world_height");
		
		//Define camera width and height in terms of tiles. This is done by multiplying how many tiles we want to see in each direction by the size of each tile. 
		setToOrtho(false, TILE_SIZE * TILES_HORIZONTAL, TILE_SIZE * TILES_VERTICAL);
		update();
		
		
	}
	
}
