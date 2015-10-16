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
	public float attack_time_current;
	public float attack_time_max;
	
	public AttackHelper(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
	}
	
	/**
	 * Attack was requested. Send in the item we attacked with.
	 */
	public void doAttack()
	{
		
	}
	
	public void update(float delta)
	{
		
	}
}
