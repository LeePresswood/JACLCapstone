package com.jacl.capstone.helpers;

import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.Player;

/**
 * Entities will become invincible for a period of time after being hit.
 * 
 * @author Lee
 *
 */
public class InvincibleCounter
{
	private MovingEntity entity;

	private final float INVINCIBLE_TIME_HIT = 0.75f;
	private final float INVINCIBILITY_ALPHA = 0.6f;
	private float invincible_time_trigger;
	private float invincible_time_current;
	public boolean is_invincible;
	
	public InvincibleCounter(MovingEntity entity)
	{
		this.entity = entity;
	}

	/**
	 * After being hit, the player will go invincible for a set period of time.
	 */
	public void goInvincible()
	{
		goInvincible(INVINCIBLE_TIME_HIT, true);
	}
	
	/**
	 * Some spells require the entity in question to go invincible.
	 * @param length Time (in seconds) to be invincible.
	 */
	public void goInvincible(float length, boolean change_alpha)
	{
		invincible_time_trigger = length;
		invincible_time_current = 0f;
		is_invincible = true;
		entity.sprite.setAlpha(change_alpha ? INVINCIBILITY_ALPHA : 1f);
	}

	public void update(float delta)
	{
		//Only need to do invincible calculation for player.
		if(is_invincible && entity instanceof Player)
		{
			//Update timing.
			invincible_time_current += delta;
			
			//If invincibility is over, end.
			if(invincible_time_current >= invincible_time_trigger)
			{
				is_invincible = false;
				entity.sprite.setAlpha(1f);
			}
		}	
	}
}
