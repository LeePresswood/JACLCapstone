package com.jacl.capstone.world;

import java.util.ArrayList;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.entities.player.Player;

/**
 * Handles the updating and rendering of game objects. Create managers to keep this class general.
 * @author Lee
 *
 */
public class World
{
	public ScreenGame screen; 
	
	public SectorCamera camera;
	
	public TiledMap map;
	public TiledMapRenderer tiled_map_renderer;
	
	public CollisionHandler collision;
	
	public Player player;
	
	public int[] layers_under_player, layers_over_player;
	
	public World(ScreenGame screen)
	{
		this.screen = screen;
		
		//Tile map.
		map = new TmxMapLoader().load("maps/test.tmx");
		tiled_map_renderer = new OrthogonalTiledMapRenderer(map);
		
		//Collision.		
		collision = new CollisionHandler(this);
		
		//Camera.
		camera = new SectorCamera(map);
		
		//World entities.
		player = new Player(this);
		
		/*
		 * To give a little more flexibility when it comes to rendering items, we want to divide the map
		 * into layers. The layers will be those that are above the player and those that are below the player.
		 * Player's level is considered level 1. He/She is standing on level 1 and collides with level 1 objects.
		 * Level 2+ objects/collisions should be seen as being above his/her head and will be rendered after the
		 * player. Level 0- objects are below the player and the main level ground.
		 */
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
		layers_under_player = new int[over.size()];
		for(Integer i : under)
			layers_under_player[array_counter++] = i;
		
		array_counter = 0;
		layers_over_player = new int[over.size()];
		for(Integer i : over)
			layers_over_player[array_counter++] = i;		
	}
	
	public void update(float delta)
	{
		//Update map tiles.

		
		//Update entities.
		player.update(delta);
		
		//Update camera.
		camera.updateCamera(this);
	}
	
	public void draw()
	{
		//Render layers/objects under player.
      tiled_map_renderer.setView(camera);
      tiled_map_renderer.render(layers_under_player);
      
      //Render player and other entities.		
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			player.draw(screen.batch);
		screen.batch.end();
		
		//Render layers/objects over player.
		tiled_map_renderer.setView(camera);
		tiled_map_renderer.render(layers_over_player);
	}
}
