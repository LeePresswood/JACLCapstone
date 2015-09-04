package com.jacl.capstone.world.entities.npc.ai;

import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.world.entities.npc.NPC;

public class PathfindingAI extends AI
{
	private Vector2 player_position;
	
	public PathfindingAI(NPC npc)
	{
		super(npc);
		
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
