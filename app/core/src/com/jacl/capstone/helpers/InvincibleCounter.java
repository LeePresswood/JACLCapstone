package com.jacl.capstone.helpers;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

public class InvincibleCounter
{
	private World world;
	private MovingEntity entity;
	
	//Entities will become invincible for a period of time after being hit. Part of this time will be during the knockback.
	public final float INVINCIBLE_TIME = 0.5f;
	public boolean is_invincible;
	public float invincible_time_current;
	
	public InvincibleCounter(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
	}

	public void doInvincible(float delta)
	{
		if(is_invincible)
		{
			invincible_time_current += delta;
			if(invincible_time_current >= INVINCIBLE_TIME)
			{
				is_invincible = false;
			}
		}	
	}
}
