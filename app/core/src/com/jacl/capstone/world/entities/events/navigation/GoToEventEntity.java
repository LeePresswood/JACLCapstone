package com.jacl.capstone.world.entities.events.navigation;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.EventEntity;

/**
 * Player requested to go from one map to another via doorway/portal/vortex/etc.
 * To show this, the screen will fade the previous screen to black and fade from
 * black to the new map.
 * 
 * @author Lee
 *
 */
public class GoToEventEntity extends EventEntity
{

	public GoToEventEntity(World world, float x, float y, String... arguments)
	{
		super(world, x, y, arguments);
		
		//First and only argument is the new map we are loading.
	}
	
}
