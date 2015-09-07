package com.jacl.capstone.world.entities.npc.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.jacl.capstone.world.entities.npc.NPC;

public class PathfindingAI extends AI
{
	private AStar astar;
	private IntArray path;
	
	private boolean is_mid_movement;
	private int start_move_x;
	private int start_move_y;
	
	public PathfindingAI(NPC npc)
	{
		super(npc);
		astar = new AStar(npc.world.map_handler.tiles_total_horizontal, npc.world.map_handler.tiles_total_vertical);
	}

	@Override
	public void updateThinking(float delta)
	{
		if(!is_mid_movement)
		{
			//Start movement.
			is_mid_movement = true;
			start_move_x = npc.getTileX();
			start_move_y = npc.getTileY();
			
			//Get a path to the player.
			path = astar.getPath(npc.getTileX(), npc.getTileY(), handler.player.getTileX(), handler.player.getTileY());
			
			//After getting the path, we have to prune doubles.
			System.out.println("Current Location: " + npc.getTileX() + "," + npc.getTileY());
			System.out.println("Target Location: " + handler.player.getTileX() + "," + handler.player.getTileY());
			System.out.print("Path: ");
			for(int i = 0; i < path.size; i += 2)
			{
				System.out.print(path.get(i) + "," + path.get(i + 1) + " ");
			}
			System.out.println("");
		}
	}
	
	@Override
	public void updatePosition(float delta)
	{
		/* Note about the path:
		 * Path is in a single-dimensional representation of points.
		 * That is to say that for every tile (x,y) the NPC is to cross
		 * over, the tile will do X first and then Y second. The first and
		 * second values are the final location of the search. The second
		 * two become the second. So on and so on.
		 * Also note that the x,y,x,y... pattern will lead to the last Y value
		 * being at the end of the list. Because the last X and Y values will
		 * make up the next place we're aiming, grabbing that will lead to
		 * our next location. We have to grab Y before X, however. 
		 * 
		 * Example:
		 * Current Location: 10,14
		 * Path: 9,11 10,12 10,13
		 * Grab Y and X (in that order) from the end of the list. Our next location should be 10,13.
		 */
		if(is_mid_movement)
		{
			//Determine if we're on a new tile yet.
			if(npc.getTileX() != start_move_x || npc.getTileY() != start_move_y)
			{//We're on a new tile.
				is_mid_movement = false;
				return;
			}
			
			if(path.size <= 0)
			{
				is_mid_movement = false;
				return;
			}
			float dx = delta * npc.move_speed;
			float dy = delta * npc.move_speed;
			int next_y = path.pop();
			int next_x = path.pop();
			
			//Otherwise, we need to keep moving.
			if(npc.getTileY() == next_y)
			{
				System.out.println("npc.getTileY() == next_y");
				dx = 0;
			}
			if(npc.getTileX() == next_x)
			{
				System.out.println("npc.getTileX() == next_x");
				dy = 0;
			}
			if(npc.getTileY() > next_y)
			{
				dy *= -1;
			}
			if(npc.getTileX() > next_x)
			{
				dx *= -1;
			}
				
			System.out.println(dx + " " + dy + "\n");
			
			
			npc.sprite.translate(dx, dy);
		}
	}
	
	@Override
	public void updateAction(float delta)
	{
	}
	
}
