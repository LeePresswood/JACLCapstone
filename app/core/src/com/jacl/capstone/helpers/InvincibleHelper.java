package com.jacl.capstone.helpers;

import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.Player;

/**
 * Entities will become invincible for a period of time after being hit.
 * 
 * @author Lee
 *
 */
public class InvincibleHelper
{
	private MovingEntity entity;

	private final float INVINCIBLE_TIME_HIT = 0.75f;
	private final float INVINCIBILITY_ALPHA = 0.4f;
	private float invincible_time_trigger;
	private float invincible_time_current;
	public boolean is_invincible;
	
	public InvincibleHelper(MovingEntity entity)
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
	 * @param change_alpha True if we should be transparent during the invincibility.
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
			//If invincibility is over, end.
			invincible_time_current += delta;
			if(invincible_time_current >= invincible_time_trigger)
			{
				is_invincible = false;
				entity.sprite.setAlpha(1f);
			}
		}
	}
}
