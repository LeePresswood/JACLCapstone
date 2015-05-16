package com.jacl.capstone.world.entities.events;

import com.badlogic.gdx.math.Rectangle;
import com.jacl.capstone.world.World;

/**
 * Event entities will be scattered throughout the land. If one is stepped upon, we must activate it.<br><br>
 * 
 * This is the actual EventEntity class. Extend from this for entities 
 * 
 * If an event is active, the world's logic will transform into the logic of the event. That is to 
 * say the event will override any movement/attack commands until the event is completed. 
 * 
 * @author Lee
 *
 */
public abstract class EventEntity
{
	//Normal collision would be bad here because barely clipping the corner of the event
	//entity tile would activate the event. Thinking of a doorway, simply walking up to
	//the door would be enough to activate it. This isn't good.
	//Instead, we want to be within the event entity tile before we start it.
	//To cause this effect, create a smaller rectangle from the event entity cell's
	//collision rectangle. Center this smaller rectangle around the event's center.
	//Collide with that.
	private final float SHRINK_SIZE_BY = 0.75f;
	private Rectangle collision_box;
	
	public EventEntity(World world, float x, float y, String... arguments)
	{
		collision_box = new Rectangle();
		collision_box.setSize(SHRINK_SIZE_BY * world.camera.TILE_SIZE);
		collision_box.setCenter(x + world.camera.TILE_SIZE / 2f, y + world.camera.TILE_SIZE / 2f);
	}
	
	/**
	 * 
	 * @return True if collision. False otherwise.
	 */
	public boolean eventCollision(float x, float y)
	{
		return collision_box.contains(x, y);
	}
}
