package com.jacl.capstone.world.entities.events;

import java.util.HashMap;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;

/**
 * Event entities will be scattered throughout the land. If one is stepped upon, we must activate it.<br><br>
 * 
 * This is the class that handles the activation. It will read from whatever cell the player is
 * currently standing on and determine if there is an event entity here. If there is one, get the
 * event and activate its logic.<br><br>
 * 
 * If an event is active, the world's logic will transform into the logic of the event. That is to 
 * say the event will override any movement/attack commands until the event is completed. 
 * 
 * @author Lee
 *
 */
public class EventEntityHandler
{
	public World world;
	
	private TiledMapTileLayer event_layer;
	private Cell[][] cells;
	private HashMap<String, EventEntity> event_map;
	
	public EventEntityHandler(World world)
	{
		this.world = world;
		
		event_layer = (TiledMapTileLayer) world.map.getLayers().get("Events");
		
		//Get cells.
		if(event_layer != null)
		{
			event_map = new HashMap<String, EventEntity>();
			
			cells = new Cell[event_layer.getHeight()][event_layer.getWidth()];
			for(int y = 0; y < event_layer.getHeight(); y++)
			{	
				for(int x = 0; x < event_layer.getWidth(); x++)
				{
					cells[y][x] = event_layer.getCell(x, y);
					
					//If the above is not null, we can put the event into the event map.
					if(cells[y][x] != null)
						event_map.put(x + "," + y, EventEntityFactory.get(world, (String) event_layer.getProperties().get(x + "," + y)));
				}
			}
		}		
	}
	
	/**
	 * If there is an event in this location, activate it.
	 * @param x Center of player's horizontal size.
	 * @param y Center of player's vertical size.
	 */
	public void doEventEntity(float x, float y)
	{
		//First, determine if there is an event in this location.
		Cell c = cells[(int) (y / event_layer.getTileHeight())][(int) (x / event_layer.getTileWidth())];
		
		if(c != null)
		{
			//Now we need to determine if we have collided with the event.
			//Normal collision would be bad here because barely clipping the corner of the event
			//entity tile would activate the event. Thinking of a doorway, simply walking up to
			//the door would be enough to activate it. This isn't good.
			//Instead, we want to be within the event entity tile before we start it.
			//To cause this effect, create a smaller rectangle from the event entity cell's
			//collision rectangle. Center this smaller rectangle around the event's center.
			//Collide with that.
			final float shrink_by = 0.75f;
			//final Rectangle r = c.
		}
		
		//return a.sprite.getBoundingRectangle().overlaps(b.sprite.getBoundingRectangle());
	}
}
