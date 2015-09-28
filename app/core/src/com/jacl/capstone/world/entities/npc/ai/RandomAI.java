package com.jacl.capstone.world.entities.npc.ai;

import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.world.entities.npc.NPC;

public class RandomAI extends AI
{
	//There will be a radius around the AI unit where the unit is close enough
	//to attack the player.
	private final float ATTACK_RADIUS;
	private Vector2 player_position;
	
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
