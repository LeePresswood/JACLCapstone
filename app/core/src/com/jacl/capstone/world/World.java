package com.jacl.capstone.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.atmosphere.GameTime;
import com.jacl.capstone.world.atmosphere.TimeColorer;
import com.jacl.capstone.world.entities.events.EventEntity;
import com.jacl.capstone.world.entities.events.EventEntityHandler;
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
	
	//Atmosphere.
	public GameTime time;
	public Color time_color;

	//Events.
	public EventEntityHandler event_handler;
	public EventEntity event;
	
	public World(ScreenGame screen)
	{
		this.screen = screen;
		
		init("maps/test.tmx");
		
		//Collision.		
		collision = new CollisionHandler(this);
		
		//Get the world timing and the effects that will result from it.
		time = new GameTime();
		time_color = TimeColorer.getColor(time);
		
		//Get event items.
		event_handler = new EventEntityHandler(this);
	}
	
	/**
	 * Call this to initialize the world to the passed map.
	 * 
	 * @param map
	 */
	public void init(String map_name)
	{
		//Tile map.
		map = new TmxMapLoader().load(map_name);
		tiled_map_renderer = new OrthogonalTiledMapRenderer(map);
		
		//Camera.
		camera = new SectorCamera(map);
		
		//World entities.
		player = new Player(this, 1f, 1f);
		
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

	public void update(float delta)
	{
		//If there is an active event, play it. Otherwise, update normally.
		if(event != null)
		{
			event.update(delta);
		}
		else
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
	}
	
	public void draw()
	{
		//If there is an active event, play it. Otherwise, draw normally.
		if(event != null)
		{
			event.draw(screen.batch);
		}
		else
		{
			worldDraw();
		}
	}
	
	/**
	 * The function in which the actual drawing is done. Separated out so that 
	 * events may use this as a frame buffer of sorts without getting held-up
	 * in the draw() method.
	 */
	public void worldDraw()
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
		tiled_map_renderer.render(layers_over_player);
		
		//Draw the overlay.	
		Gdx.gl.glEnable(GL20.GL_BLEND);
		screen.renderer.setColor(time_color);
		screen.renderer.begin(ShapeType.Filled);	
			screen.renderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screen.renderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
