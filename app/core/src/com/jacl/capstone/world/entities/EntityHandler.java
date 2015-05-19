package com.jacl.capstone.world.entities;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.player.Player;

public class EntityHandler
{
	public World world;
	
	//Players
	public Player player;
	
	//Enemies/NPCs
	
	
	//Projectiles
	
	
	public EntityHandler(World world)
	{
		this.world = world;
	}
	
	/**
	 * Initialize the handler after entering a new screen.
	 * @param start_x Player's starting X location (in blocks).
	 * @param start_y Player's starting Y location (in blocks). 
	 */
	public void handlerInit(int start_x, int start_y)
	{
		//We will eventually have an EntityManager here to complete the set.
		player = new Player(world, start_x * world.map_manager.tile_size, start_y * world.map_manager.tile_size);
		
	}
	
	public void update(float delta)
	{
		player.update(delta);
	}
	
	public void draw()
	{
		world.screen.batch.setProjectionMatrix(world.camera.combined);
		world.screen.batch.begin();
			player.draw(world.screen.batch);
		world.screen.batch.end();
	}
}
