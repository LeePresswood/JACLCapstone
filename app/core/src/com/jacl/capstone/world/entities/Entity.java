package com.jacl.capstone.world.entities;

import com.jacl.capstone.world.World;

/**
 * These are the objects within the game. This will be everything that is not a tile, such as:<br>
 * - The Player<br>
 * - Enemies<br>
 * - Projectiles<br>
 * - Puzzle Objects<br><br>
 * 
 * It will also include invisible items that could cause an effect, such as a doorway item that 
 * moves us from one screen to another or an event item that causes a cutscene.
 * 
 * @author Lee
 *
 */
public abstract class Entity
{
	public World world;
	
	public Entity(World world)
	{
		this.world = world;
	}
	
	public abstract void update(float delta);
	public abstract void draw();
}
