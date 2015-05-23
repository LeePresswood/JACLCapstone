package com.jacl.capstone.helpers.handlers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * Handles collision of entities.
 * 
 * @author Lee
 *
 */
public class CollisionHandler
{
	public World world;
	
	private TiledMapTileLayer collision_layer;
	private final int COLLISION_LAYER_INDEX = 1;
	private Cell[][] cells;
	
	private Rectangle intersection;
	
	public CollisionHandler(World world)
	{
		this.world = world;
		
		intersection = new Rectangle();
	}
	
	public void handlerInit()
	{
		collision_layer = (TiledMapTileLayer) world.map_handler.map.getLayers().get(COLLISION_LAYER_INDEX);
		
		//Get cells.
		cells = new Cell[collision_layer.getHeight()][collision_layer.getWidth()];
		for(int y = 0; y < collision_layer.getHeight(); y++)
			for(int x = 0; x < collision_layer.getWidth(); x++)
				cells[y][x] = collision_layer.getCell(x, y);
	}
	
	/**
	 * Using the given X and Y values, determine if there was a collision in this location.
	 * @param x X location to check.
	 * @param y Y location to check.
	 * @return The cell in that location, or null if nothing is there.
	 */
	public Cell getCollisionCell(float x, float y)
	{
		return cells[(int) (y / collision_layer.getTileHeight())][(int) (x / collision_layer.getTileWidth())];
	}
	
	/**
	 * Do the two entities collide?
	 * @param a First entity to check.
	 * @param b Second entity to compare to.
	 * @return True if there is a collision. False otherwise.
	 */
	/*public boolean collidesWith(Entity a, Entity b)
	{
		return a.sprite.getBoundingRectangle().overlaps(b.sprite.getBoundingRectangle());
	}*/
	
	public void newCollidesWith(MovingEntity a, MovingEntity b)
	{
		if(Intersector.intersectRectangles(a.sprite.getBoundingRectangle(), b.sprite.getBoundingRectangle(), intersection))
		{//There was an intersection. Determine the colliding edges.
			Rectangle r1 = a.sprite.getBoundingRectangle();
			if(intersection.x > r1.x && intersection.width < intersection.height)                                  
			{
				b.knockback_direction = Direction.LEFT;
				a.knockback_direction = Direction.RIGHT;
			}
			if(intersection.y > r1.y && intersection.width >= intersection.height)
			{
				b.knockback_direction = Direction.DOWN;
				a.knockback_direction = Direction.UP;
			}
			if(intersection.x + intersection.width < r1.x + r1.width && intersection.width < intersection.height)  
			{
				b.knockback_direction = Direction.RIGHT;
				a.knockback_direction = Direction.LEFT;
			}
			if(intersection.y + intersection.height < r1.y + r1.height && intersection.width >= intersection.height)
			{
				b.knockback_direction = Direction.UP;
				a.knockback_direction = Direction.DOWN;
			}
		}
	}
}
