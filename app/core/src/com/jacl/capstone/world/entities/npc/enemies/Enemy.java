package com.jacl.capstone.world.entities.npc.enemies;

import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.NPC;

/**
 * Parent enemy class. All enemies will hurt the hero upon being hit, and they will
 * react to being hit by a weapon. 
 * 
 * @author Lee
 *
 */
public abstract class Enemy extends NPC
{	
	public Enemy(World world, float x, float y, boolean knockback_on_collide, float move_speed, float health, float damage_on_bump, Alignment alignment)
	{
		super(world, x, y, knockback_on_collide, move_speed, health, damage_on_bump, Alignment.ENEMY);
	}
}
