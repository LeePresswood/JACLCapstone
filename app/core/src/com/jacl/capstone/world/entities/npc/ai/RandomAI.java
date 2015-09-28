package com.jacl.capstone.world.entities.npc.ai;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.world.entities.npc.NPC;

/**
 * Think of this AI as having a movement pattern that is the same as what you'd see from NPCs in Pokemon.
 * Actions include either move or rest for movement and attack for special cases.
 * @author Lee
 */
public class RandomAI extends AI
{
	//There will be a radius around the AI unit where the unit is close enough
	//to attack the player.
	private final float ATTACK_RADIUS;
	private Vector2 player_position;
	
	//Two movement actions are either move or wait. Waiting should be the more common action of the two.
	private Random random;
	private final float CHANCE_WAIT = 0.7f;
	
	private boolean moving;
	private float move_current;
	private final float MOVE_MAX = 1f;
	
	private boolean waiting;
	private float wait_current;
	private final float wait_time = 0.75f;
	
	public RandomAI(NPC npc)
	{
		super(npc);
		
		ATTACK_RADIUS = (float) Math.pow(1.5f * npc.world.map_handler.tile_size, 2);
		player_position = new Vector2();
	}
	
	@Override
	public void updateThinking(float delta)
	{
		
	}
	
	@Override
	public void updatePosition(float delta)
	{
	}
	
	@Override
	public void updateAction(float delta)
	{
	}
	
}
