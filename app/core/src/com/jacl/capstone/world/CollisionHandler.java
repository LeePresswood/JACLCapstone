package com.jacl.capstone.world;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.jacl.capstone.world.entities.Entity;

/**
 * Handles collision of entities.
 * @author Lee
 *
 */
public class CollisionHandler
{
	private World world;
	
	private TiledMapTileLayer collision_layer;
	private final int COLLISION_LAYER_INDEX = 1;
	private Cell[][] cells;
	
	public CollisionHandler(World world)
	{
		this.world = world;
		
		collision_layer = (TiledMapTileLayer) world.map.getLayers().get(COLLISION_LAYER_INDEX);
		
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
	public boolean collidesWith(Entity a, Entity b)
	{
		return false;
	}
}
