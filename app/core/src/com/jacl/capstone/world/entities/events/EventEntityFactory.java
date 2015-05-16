package com.jacl.capstone.world.entities.events;

import com.jacl.capstone.world.World;

/**
 * Get the correct EventEntity from the given properties.
 * 
 * @author Lee
 *
 */
public class EventEntityFactory
{
	public static EventEntity get(World world, String command)
	{
		//We will be creating a new EventEntity.
		EventEntity e;
		
		//This event will be determined by the command variable.
		String[] tokens = command.split(" ");
		
		//First token is the command. Any additional tokens will be arguments the event will use.
		
		
		return e;
	}	
}
