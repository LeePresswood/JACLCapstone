package com.jacl.capstone.helpers;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * Manages attack.
 * 
 * @author Lee
 *
 */
public class AttackAnimator
{
	private World world;
	private MovingEntity entity;
	
	public boolean attacking, mid_attack;
	
	public AttackAnimator(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
	}
}
