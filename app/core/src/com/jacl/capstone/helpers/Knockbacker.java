package com.jacl.capstone.helpers;

import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * Manages knockback.
 * 
 * @author Lee
 *
 */
public class Knockbacker
{
	private World world;
	private MovingEntity entity;
	
	//Knockback should always be the same amount for continuity throughout the game regardless of entity.
	private final float KNOCKBACK_BLOCKS = 1.5f;
	private final float KNOCKBACK_SPEED = 20f;
	private final float KNOCKBACK_DISTANCE;
	
	//Knockback itself will have a flag set if happening. It also has distance and direction.
	public boolean being_knocked_back;
	public float current_knockback;
	public Direction knockback_direction;
	
	public Knockbacker(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
		
		//Knockback block distance is knockback_blocks * size of blocks.
		KNOCKBACK_DISTANCE = KNOCKBACK_BLOCKS * world.map_handler.tile_size;
		
		//Knockback is dependent upon the direction the entity is facing. If no movement happens before being hit, no direction is set.
		knockback_direction = Direction.DOWN;
	}
	
	public void doKnockback(float delta)
	{
		if(being_knocked_back)
		{
			//Calculate the knockback.
			current_knockback += delta * KNOCKBACK_SPEED * world.map_handler.tile_size;
			if(current_knockback >= KNOCKBACK_DISTANCE)
			{
				being_knocked_back = false;
			}
			
			//Do the knockback movement. This will depend upon the last direction the entity moved.
			if(knockback_direction == Direction.LEFT)
				entity.sprite.translateX(delta * KNOCKBACK_SPEED * world.map_handler.tile_size);
			else if(knockback_direction == Direction.RIGHT)
				entity.sprite.translateX(-delta * KNOCKBACK_SPEED * world.map_handler.tile_size);
			else if(knockback_direction == Direction.UP)
				entity.sprite.translateY(-delta * KNOCKBACK_SPEED * world.map_handler.tile_size);
			else if(knockback_direction == Direction.DOWN)
				entity.sprite.translateY(delta * KNOCKBACK_SPEED * world.map_handler.tile_size);
		}
	}
}