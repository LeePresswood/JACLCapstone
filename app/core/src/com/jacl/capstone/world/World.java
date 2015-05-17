package com.jacl.capstone.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jacl.capstone.data.StateSaver;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.atmosphere.GameTime;
import com.jacl.capstone.world.atmosphere.TimeColorer;
import com.jacl.capstone.world.entities.events.EventEntity;
import com.jacl.capstone.world.entities.events.EventEntityHandler;
import com.jacl.capstone.world.entities.player.Player;

/**
 * Handles the updating and rendering of game objects. Create managers to keep this class general.
 * 
 * @author Lee
 */
public class World
{
	public ScreenGame screen;	
	
	//Helpers.
	public SectorCamera camera;
	public StateSaver saver;
	public MapManager map_manager;
	public CollisionHandler collision;	
	public EventEntityHandler event_handler;
	
	//Entities.
	public Player player;
	public EventEntity event;
	
	//Atmosphere.
	public GameTime time;
	public Color time_color;	
	
	public World(ScreenGame screen)
	{
		this.screen = screen;
		saver = new StateSaver(this);
	}
	
	/**
	 * Call this to initialize the world to the passed map.
	 * @param map_name The map to load. Note: Map directory should not be included. Simply pass the name of the map found in the map directory of the assets folder.
	 * @param start_x Player's starting X location (in blocks).
	 * @param start_y Player's starting Y location (in blocks). 
	 */
	public void init(String map_name, int start_x, int start_y)
	{
		//Tile map.
		map_manager = new MapManager();
		map_manager.map_init(map_name);
		
		//Camera.
		camera = new SectorCamera(map_manager.map);
		
		//World entities.
		player = new Player(this, start_x * map_manager.TILE_SIZE, start_y * map_manager.TILE_SIZE);
		
		//Update camera onto player.
		camera.updateCamera(this);
		
		//Collision.		
		collision = new CollisionHandler(this);
		
		//Get event items.
		event_handler = new EventEntityHandler(this);
	}

	public void update(float delta)
	{
		//If there is an active event, play it. Otherwise, update normally.
		if(event != null)
			event.update(delta);
		else
			worldUpdate(delta);
	}
	
	public void draw()
	{
		//If there is an active event, play it. Otherwise, draw normally.
		if(event != null)
			event.draw(screen.batch);
		else
			worldDraw();
	}
	
	/**
	 * The function in which the actual updating is done. Separated out so that 
	 * events may use the normal game logic without getting held-up in the
	 * update() method.
	 */
	public void worldUpdate(float delta)
	{
		//Update time.
		time.update(delta);
		
		//If the hour changed, update color.
		if(time.recently_updated_hour)
			time_color = TimeColorer.getColor(time);
		
		//Update entities.
		player.update(delta);
		
		//Update camera.
		camera.updateCamera(this);
	}
	
	/**
	 * The function in which the actual drawing is done. Separated out so that 
	 * events may use this as a frame buffer of sorts without getting held-up
	 * in the draw() method.
	 */
	public void worldDraw()
	{
		//Render layers/objects under player.
		map_manager.tiled_map_renderer.setView(camera);
		map_manager.tiled_map_renderer.render(map_manager.layers_under_player);
		
		//Render player and other entities.		
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			player.draw(screen.batch);
		screen.batch.end();
		
		//Render layers/objects over player.
		map_manager.tiled_map_renderer.render(map_manager.layers_over_player);
		
		//Draw the overlay.	
		Gdx.gl.glEnable(GL20.GL_BLEND);
		screen.renderer.setColor(time_color);
		screen.renderer.begin(ShapeType.Filled);	
			screen.renderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screen.renderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
