package com.jacl.capstone.helpers;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.Player;

public class InvincibleCounter
{
	private World world;
	private MovingEntity entity;
	
	//Entities will become invincible for a period of time after being hit.
	private final float INVINCIBLE_TIME_HIT = 1f;
	private float invincible_time_trigger;
	private float invincible_time_current;
	public boolean is_invincible;
	
	//This is how transparant the player will be.
	private final float INVINCIBILITY_ALPHA = 0.6f;
	
	public InvincibleCounter(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
	}

	/**
	 * After being hit, the player will go invincible for a set period of time.
	 */
	public void goInvincible()
	{
		goInvincible(INVINCIBLE_TIME_HIT);
	}
	
	/**
	 * Some spells require the entity in question to go invincible.
	 * @param length Time (in seconds) to be invincible.
	 */
	public void goInvincible(float length)
	{
		invincible_time_trigger = length;
		invincible_time_current = 0f;
		is_invincible = true;
	}

	public void invincibleTick(float delta)
	{
		//Only need to do invincible calculation for player.
		if(is_invincible && entity instanceof Player)
		{
			//Update the sprite to show invincibility.
			entity.sprite.setAlpha(INVINCIBILITY_ALPHA);
			
			//Update timing.
			invincible_time_current += delta;
			
			//If invincibility over, end.
			if(invincible_time_current >= INVINCIBLE_TIME_HIT)
			{
				is_invincible = false;
				entity.sprite.setAlpha(1f);
			}
		}	
	}
}
