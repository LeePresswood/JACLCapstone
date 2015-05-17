package com.jacl.capstone.world;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Helper class that manages the tiled map and its properties.
 * 
 * @author Lee
 *
 */
public class MapManager
{
	private final String MAP_DIRECTORY = "maps/";
	public String map_name;
	public TiledMap map;
	public TiledMapRenderer tiled_map_renderer;
	public int[] layers_under_player, layers_over_player;
	
	/**
	 * Initialize portions of the map.
	 */
	public void map_init(String map_name)
	{
		//Get map data.
		this.map_name = map_name;
		map = new TmxMapLoader().load(MAP_DIRECTORY + map_name);
		tiled_map_renderer = new OrthogonalTiledMapRenderer(map);
		
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
		ArrayList<Integer> under = new ArrayList<Integer>();
		ArrayList<Integer> over = new ArrayList<Integer>();
		
		//Loop through all the map's layers and put them into one of these two categories.
		int layer_counter = 0;
		for(MapLayer m : map.getLayers())
		{
			if(m.getName().charAt(0) <= '1' || m.getName().charAt(0) == '-')
				under.add(layer_counter++);
			else
				over.add(layer_counter++);
		}
		
		//Add these to the arrays.
		int array_counter = 0;
		layers_under_player = new int[under.size()];
		for(Integer i : under)
			layers_under_player[array_counter++] = i;
		
		array_counter = 0;
		layers_over_player = new int[over.size()];
		for(Integer i : over)
			layers_over_player[array_counter++] = i;
	}
}
