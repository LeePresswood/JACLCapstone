package com.jacl.capstone.helpers;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.Player;

public class InvincibleCounter
{
	private World world;
	private MovingEntity entity;
	
	//Entities will become invincible for a period of time after being hit. Part of this time will be during the knockback.
	public final float INVINCIBLE_TIME = 1f;
	public boolean is_invincible;
	public float invincible_time_current;
	
	//This is how transparant the player will be.
	public final float INVINCIBILITY_ALPHA = 0.3f;
	
	public InvincibleCounter(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
	}

	public void doInvincible(float delta)
	{
		//Only need to do invincible calculation for player.
		if(is_invincible && entity instanceof Player)
		{
			//Update the sprite to show invincibility.
			entity.sprite.setAlpha(INVINCIBILITY_ALPHA);
			
			//Update timing.
			invincible_time_current += delta;
			
			//If invincibility over, end.
			if(invincible_time_current >= INVINCIBLE_TIME)
			{
				is_invincible = false;
				entity.sprite.setAlpha(1f);
			}
		}	
	}
}
