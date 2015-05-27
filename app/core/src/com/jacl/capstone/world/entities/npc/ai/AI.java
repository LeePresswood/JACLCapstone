package com.jacl.capstone.world.entities.npc.ai;

import com.jacl.capstone.world.entities.npc.NPC;

/**
 * All AI classes will utilize this parent class.
 * 
 * @author Lee
 *
 */
public abstract class AI
{
	public final NPC npc;
	
	public AI(NPC npc)
	{
		this.npc = npc;
	}
	
	/**
	 * From the current position, determine
	 * what the next best move will be.
	 * @param delta Change in time.
	 */
	public abstract void updateThinking(float delta);
	
	/**
	 * Update position after thinking.
	 * @param delta Change in time.
	 */
	public abstract void updatePosition(float delta);
	
	/**
	 * After moving, the AI can decide an action.
	 * @param delta Change in time.
	 */
	public abstract void updateAction(float delta);
}
