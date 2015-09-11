package com.jacl.capstone.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jacl.capstone.helpers.handlers.CameraHandler;
import com.jacl.capstone.helpers.handlers.CollisionHandler;
import com.jacl.capstone.helpers.handlers.DataHandler;
import com.jacl.capstone.helpers.handlers.EntityHandler;
import com.jacl.capstone.helpers.handlers.EventEntityHandler;
import com.jacl.capstone.helpers.handlers.MapHandler;
import com.jacl.capstone.helpers.handlers.SaveHandler;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.atmosphere.GameTime;
import com.jacl.capstone.world.atmosphere.TimeColorer;

/**
 * Handles the updating and rendering of game objects. Create managers to keep this class general.
 * 
 * @author Lee
 */
public class World
{
	public ScreenGame screen;	
	
	//Handlers.
	public CameraHandler camera_handler;
	public SaveHandler save_handler;
	public MapHandler map_handler;
	public EntityHandler entity_handler;
	public CollisionHandler collision_handler;	
	public EventEntityHandler event_handler;
	public DataHandler data_handler;
	
	//Atmosphere.
	public GameTime time;
	public Color time_color;	
	
	public World(ScreenGame screen)
	{
		this.screen = screen;
		
		//Initialize helpers.
		save_handler = new SaveHandler(this);
		map_handler = new MapHandler(this);
		entity_handler = new EntityHandler(this);
		collision_handler = new CollisionHandler(this);
		event_handler = new EventEntityHandler(this);
		camera_handler = new CameraHandler(this);
		data_handler = new DataHandler(this);
		
		//Load from save.
		save_handler.getFromSave();
	}
	
	/**
	 * Call this to initialize the world to the passed map.
	 * @param map_name The map to load. Note: Map directory should not be included. Simply pass the name of the map found in the map directory of the assets folder.
	 * @param start_x Player's starting X location (in blocks).
	 * @param start_y Player's starting Y location (in blocks). 
	 */
	public void init(String map_name, int start_x, int start_y)
	{
		map_handler.handlerInit(map_name);
		entity_handler.handlerInit(start_x, start_y);
		camera_handler.handlerInit();
		collision_handler.handlerInit();
		event_handler.handlerInit();
		data_handler.handlerInit();
	}

	public void update(float delta)
	{
		//If there is an active event, play it. Otherwise, update normally.
		if(event_handler.event != null)
		{
			event_handler.event.update(delta);
		}
		else
		{
			worldUpdate(delta);
		}
	}
	
	public void draw()
	{
		//If there is an active event, draw it. Otherwise, draw normally.
		if(event_handler.event != null)
		{
			event_handler.event.draw(screen.batch);
		}
		else
		{
			worldDraw();
		}
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
		if(time.recently_updated_minute)
		{
			time_color = TimeColorer.getColor(time);
		}
		
		//Update entities.
		entity_handler.update(delta);
		
		//Update camera onto player.
		camera_handler.updateCamera();
	}
	
	/**
	 * The function in which the actual drawing is done. Separated out so that 
	 * events may use this as a frame buffer of sorts without getting held-up
	 * in the draw() method.
	 */
	public void worldDraw()
	{
		//Render layers/objects under player.
		map_handler.tiled_map_renderer.setView(camera_handler);
		map_handler.tiled_map_renderer.render(map_handler.layers_under_player);
		
		//Render player and other entities.		
		entity_handler.draw();
		
		//Render layers/objects over player.
		map_handler.tiled_map_renderer.render(map_handler.layers_over_player);
		
		//Draw the day/night overlay if we are outside.
		if(map_handler.is_outside)
		{
			Gdx.gl.glEnable(GL20.GL_BLEND);
			screen.renderer.setColor(time_color);
			screen.renderer.begin(ShapeType.Filled);	
				screen.renderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			screen.renderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}
}
