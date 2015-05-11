package com.jacl.capstone.world;

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
	public TiledMapRenderer tiledMapRenderer;
	
	public Player player;
	
	public World(ScreenGame screen)
	{
		this.screen = screen;
		
		//Camera and tile map.
		map = new TmxMapLoader().load("maps/test.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		camera = new SectorCamera(map);
		
		//Player.
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
		camera.update();
      tiledMapRenderer.setView(camera);
      tiledMapRenderer.render();
		
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			player.draw(screen.batch);
		screen.batch.end();
	}
}
