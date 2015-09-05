package com.jacl.capstone.world.entities.npc.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.jacl.capstone.world.entities.npc.NPC;

public class PathfindingAI extends AI
{
	private Vector2 player_position;
	private AStar astar;
	private IntArray path;
	
	public PathfindingAI(NPC npc)
	{
		super(npc);
		
		player_position = new Vector2();
		astar = new AStar(npc.world.map_handler.tiles_total_horizontal, npc.world.map_handler.tiles_total_vertical);
	}

	@Override
	public void updateThinking(float delta)
	{
		player_position.set(handler.player.getCenterX() - npc.getCenterX(), handler.player.getCenterY() - npc.getCenterY());
		
		path = astar.getPath(npc.getTileX(), npc.getTileY(), handler.player.getTileX(), handler.player.getTileY());
	}
	
	@Override
	public void updatePosition(float delta)
	{
		float dx = delta * npc.move_speed;
		if(npc.getTileX() > path.pop())
			dx *= -1;
		float dy = delta * npc.move_speed;
		if(npc.getTileY() > path.pop())
			dy *= -1;
		
		//System.out.println(dx + " " + dy);
		//for (int i = 0, n = path.size; i < n; i += 2)
			System.out.println(path.peek());
		
		npc.sprite.translate(dx, dy);
	}
	
	@Override
	public void updateAction(float delta)
	{
	}
	
}
