package com.jacl.capstone.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
	}
	
	public void update(float delta)
	{
		player.update(delta);
		
		//Update camera
		camera.updateCamera(this);
	}
	
	public void draw()
	{
      tiled_map_renderer.setView(camera);
      tiled_map_renderer.render();
		
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			player.draw(screen.batch);
		screen.batch.end();
	}
}
