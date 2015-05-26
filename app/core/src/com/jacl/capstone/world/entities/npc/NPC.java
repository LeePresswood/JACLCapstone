package com.jacl.capstone.world.entities.npc;

import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.npc.ai.AI;

/**
 * All enemies and other non-player characters will be a MovingEntity
 * with an added component of artificial intelligence.
 * 
 * @author Lee
 *
 */
public abstract class NPC extends MovingEntity
{
	protected AI ai;
	
	public NPC(World world, float x, float y, boolean knockback_on_collide,	float move_speed, float health, float damage_on_bump,	Alignment alignment, AI ai)
	{
		super(world, x, y, knockback_on_collide, move_speed, health, damage_on_bump, alignment);
		this.ai = ai;
	}
	
	@Override
	/**
	 * AI will take over in this case.
	 */
	protected void move(float delta)
	{
		ai.updateThinking(delta);
		ai.updatePosition(delta);
		ai.updateAction(delta);
	}
	
	@Override
	/**
	 * Attacking will be an action done through AI's updateAction.
	 * Thus, this attack method is not required for AI.
	 */
	protected void attack(float delta)
	{
	}
}
