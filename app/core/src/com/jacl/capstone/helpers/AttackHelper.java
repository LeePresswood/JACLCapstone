package com.jacl.capstone.helpers;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * Manages attacking.
 * @author Lee
 */
public class AttackHelper
{
	private World world;
	private MovingEntity entity;
	
	public boolean attacking, mid_attack;
	
	public AttackHelper(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
	}
}
