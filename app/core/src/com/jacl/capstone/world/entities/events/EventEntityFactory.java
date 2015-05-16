package com.jacl.capstone.world.entities.events;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.navigation.GoToEventEntity;

/**
 * Get the correct EventEntity from the given properties.
 * 
 * @author Lee
 *
 */
public class EventEntityFactory
{
	public static EventEntity get(World world, int x, int y, String command)
	{
		System.out.println(command);
		//This event will be determined by the command variable.
		String[] tokens = command.split(" ");
		
		//First token is the command. Any additional tokens will be arguments the event will use.
		switch(EventTypes.valueOf(tokens[0]))
		{
			case GOTO:
				return new GoToEventEntity(world, x, y, tokens[1]);
			default:
				return null;
		}
	}	
}
