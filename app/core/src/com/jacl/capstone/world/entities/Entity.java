package com.jacl.capstone.world.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public Sprite sprite;
	
	public Entity(World world, float x, float y)
	{
		this.world = world;
		
		sprite = makeSprite(x, y);
	}
	
	//These methods will be very helpful in checking bounds of/moving toward an entity.
	public float getCenterX()
	{
		return sprite.getX() + sprite.getWidth() / 2f;
	}
	
	public float getCenterY()
	{
		return sprite.getY() + sprite.getHeight() / 2f;
	}
	
	public void setCenterX(float x)
	{
		sprite.setX(x - sprite.getWidth() / 2f);
	}
	
	public void setCenterY(float y)
	{
		sprite.setY(y - sprite.getHeight() / 2f);
	}
	
	public float getLeft()
	{
		return sprite.getX();
	}
	
	public float getRight()
	{
		return sprite.getX() + sprite.getWidth();
	}
	
	public float getBottom()
	{
		return sprite.getY();
	}
	
	public float getTop()
	{
		return sprite.getY() + sprite.getHeight();
	}
	
	public int getTileX()
	{
		return (int) (getCenterX() / world.map_handler.tile_size);
	}
	
	public int getTileY()
	{
		return (int) (getCenterY() / world.map_handler.tile_size);
	}
	
	/**
	 * The constructor will automatically call this method to create the sprite.
	 * This method should define the sprite's location, size, texture, rotation,
	 * and any other qualities that may be useful here.
	 * @param y 
	 * @param x 
	 * @return An instance of the sprite that we will use.
	 */
	protected abstract Sprite makeSprite(float x, float y);	
	public abstract void update(float delta);
	public abstract void draw(SpriteBatch batch);
}
