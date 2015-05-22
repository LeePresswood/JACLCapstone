package com.jacl.capstone.helpers.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;
import com.jacl.capstone.world.entities.npc.enemies.Enemy;
import com.jacl.capstone.world.entities.player.Player;

public class EntityHandler
{
	public World world;
	
	//We will have an all entities array that will be used for drawing. We can sort this array by the Y-values to give an effect of being in front of/behind each other.
	private ArrayList<Entity> all_entities;
	
	//Players
	public Player player;
	
	//Enemies/NPCs
	public ArrayList<Enemy> enemies;
	
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
		player = new Player(world, start_x, start_y);
		
		//Because this is when we are first creating the EnitityHandler, there should be no items in the all_entities array. Let's make it and add the player.
		//Note: We would normally sort the array after adding an entity, but because this is the only entity, there's no need.
		all_entities = new ArrayList<Entity>();
		all_entities.add(player);
		
		enemies = new ArrayList<Enemy>();
	}
	
	/**
	 * Populate the world by adding the passed entity.
	 * @param e Entity to add.
	 */
	public void add(Entity e)
	{
		//Add and sort by Y values. Highest to lowest.
		all_entities.add(e);
		Collections.sort(all_entities, new EntityComparator());
		
		//Split this entity based upon what type it is.
		if(e.alignment == Alignment.PLAYER)
		{
			
		}
		else if(e.alignment == Alignment.ENEMY)
		{
			enemies.add((Enemy) e);
		}
	}
	
	public void update(float delta)
	{
		//player.update(delta);
		for(Entity e : all_entities)
			e.update(delta);
		Collections.sort(all_entities, new EntityComparator());
	}
	
	public void draw()
	{
		world.screen.batch.setProjectionMatrix(world.camera_handler.combined);
		world.screen.batch.begin();
			//While drawing the entities, draw the highest Y values first. 
			//These are the sprites that are farthest from the camera. 
			//They are behind the lower Y values. 
			for(Entity e : all_entities)
				e.draw(world.screen.batch);
		world.screen.batch.end();
	}
	
	/**
	 * Used in sorting the items by Y values. Sort by the top value of the sprite
	 * 
	 * @author Lee
	 *
	 */
	private class EntityComparator implements Comparator<Entity> 
	{
	    @Override
	    public int compare(Entity a, Entity b) 
	    {
	        return a.getCenterY() > b.getCenterY() ? -1 : a.getCenterY() == b.getCenterY() ? 0 : 1;
	    }
	}
}
