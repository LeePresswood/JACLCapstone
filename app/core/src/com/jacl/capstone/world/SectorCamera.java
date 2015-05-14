package com.jacl.capstone.world;

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
	//Define how many tiles we want to see. Define one in terms of the other so that we only need to change the independent one in the future.
	public final float TILES_HORIZONTAL = 20f;
	public final float TILES_VERTICAL = TILES_HORIZONTAL * 9f / 16f;
	
	//Each tile will be a certain width/height. This is defined in the map file.
	public final float TILE_SIZE;

	//The map will also define the width and height of the map in tiles.
	public final int TILES_TOTAL_HORIZONTAL, TILES_TOTAL_VERTICAL;
	
	public SectorCamera(TiledMap map)
	{
		super();
		
		//Read bounds and sizes of map.
		TILE_SIZE = map.getProperties().get("tilewidth", Integer.class);
		TILES_TOTAL_HORIZONTAL = map.getProperties().get("width", Integer.class);
		TILES_TOTAL_VERTICAL = map.getProperties().get("height", Integer.class);
		
		//Define camera width and height in terms of tiles. 
		//This is done by multiplying how many tiles we want to see in each direction by the size of each tile. 
		setToOrtho(false, TILE_SIZE * TILES_HORIZONTAL, TILE_SIZE * TILES_VERTICAL);
		update();
	}
	
	/**
	 * Update the camera each tick of the game. This involves changing the camera's position based upon 
	 * both the player's position and the bounds of the world.<br><br>
	 * 
	 * Main idea here is that the world's bounds have a greater priority than the player's position.
	 * To represent this, move to the player's position first. Afterward, readjust the camera to 
	 * fit with the world's bounds.
	 * @param world
	 */
	public void updateCamera(World world)
	{
		//Look at player.
		position.set(world.player.getCenterX(), world.player.getCenterY(), 0);
				
		//Adjust bounds in accordance with the world's bounds.
		//Left side
		if(position.x < viewportWidth / 2f)
			position.x = viewportWidth / 2f;
		
		//Right side
		else if(position.x > TILES_TOTAL_HORIZONTAL * TILE_SIZE - viewportWidth / 2f)
			position.x = TILES_TOTAL_HORIZONTAL * TILE_SIZE - viewportWidth / 2f;
		
		//Bottom side
		if(position.y < viewportHeight / 2f)
			position.y = viewportHeight / 2f;
		
		//Top side
		else if(position.y > TILES_TOTAL_VERTICAL * TILE_SIZE - viewportHeight / 2f)
			position.y = TILES_TOTAL_VERTICAL * TILE_SIZE - viewportHeight / 2f;
		
		//Finish updating and end.
		update();
	}
}
