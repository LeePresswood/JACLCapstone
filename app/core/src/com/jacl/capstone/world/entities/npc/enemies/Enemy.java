package com.jacl.capstone.world.entities.npc.enemies;

import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * Parent enemy class. All enemies will hurt the hero upon being hit, and they will
 * react to being hit by a weapon. 
 * 
 * @author Lee
 *
 */
public abstract class Enemy extends MovingEntity
{	
	public Enemy(World world, float x, float y)
	{
		super(world, x, y, Alignment.ENEMY);
		
		knockback_on_collide = true;
	}
}
