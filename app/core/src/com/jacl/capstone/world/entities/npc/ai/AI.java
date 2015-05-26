package com.jacl.capstone.world.entities.npc.ai;

import com.jacl.capstone.world.entities.npc.NPC;

/**
 * Implement this interface for all AI classes.
 * 
 * @author Lee
 *
 */
public interface AI
{
	/**
	 * From the current position, determine
	 * what the next best move will be.
	 * @param delta Change in time.
	 */
	public void updateThinking(float delta);
	
	/**
	 * Update position after thinking.
	 * @param delta Change in time.
	 */
	public void updatePosition(float delta);
	
	/**
	 * After moving, the AI can decide an action.
	 * @param delta Change in time.
	 */
	public void updateAction(float delta);

	public void init(NPC npc);
}
