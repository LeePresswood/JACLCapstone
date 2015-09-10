package com.jacl.capstone.helpers.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jacl.capstone.world.World;

/**
 * Helper class that manages the tiled map and its properties.
 * 
 * @author Lee
 *
 */
public class MapHandler
{
	public World world;
	
	//Map naming.
	private final String MAP_DIRECTORY = "maps/";
	public String map_name;
	
	//Map and layers.
	public TiledMap map;
	public TiledMapRenderer tiled_map_renderer;
	ArrayList<MapLayer> under;
	ArrayList<MapLayer> over;
	
	//Each tile will be a certain width/height. This is defined in the map file. The map will also define the width and height of the map in tiles.
	public float tile_size;
	public int tiles_total_horizontal, tiles_total_vertical;
	
	//Other map qualities.
	public boolean is_outside;
	
	/*
	 * Note for future Lee:
	 * ***********************************http://stackoverflow.com/questions/23144367/why-do-i-have-lines-going-across-my-libgdx-game-using-tiled**********************************************
	 */
	
	public MapHandler(World world)
	{
		this.world = world;
	}

	/**
	 * Initialize portions of the map.
	 */
	public void handlerInit(String map_name)
	{
		//Get map data.
		this.map_name = map_name;
		map = new TmxMapLoader().load(MAP_DIRECTORY + map_name);
		tiled_map_renderer = new OrthogonalTiledMapRenderer(map);
		
		//Read bounds and sizes of map.
		tile_size = map.getProperties().get("tilewidth", Integer.class);
		tiles_total_horizontal = map.getProperties().get("width", Integer.class);
		tiles_total_vertical = map.getProperties().get("height", Integer.class);
		
		//Read other map qualties
		is_outside = Boolean.parseBoolean(map.getProperties().get("outside", String.class));
		
		//Separate the map layers.
		separateLayers();
	}
	
	/**
	 * To give a little more flexibility when it comes to rendering items, we want to divide the map
	 * into layers. The layers will be those that are above the player and those that are below the player.
	 * Player's level is considered level 1. He/She is standing on level 1 and collides with level 1 objects.
	 * Level 2+ objects/collisions should be seen as being above his/her head and will be rendered after the
	 * player. Level 0- objects are below the player and the main level ground.
	 */
	private void separateLayers()
	{		
		under = new ArrayList<MapLayer>();
		over = new ArrayList<MapLayer>();
		
		//Loop through all the map's layers and put them into one of these two categories.
		for(int i = 0; i < map.getLayers().getCount(); i++)
		{
			if(i <= 3)
			{
				under.add(map.getLayers().get(i));
			}
			else
			{
				over.add(map.getLayers().get(i));
			}
		}
	}
}
