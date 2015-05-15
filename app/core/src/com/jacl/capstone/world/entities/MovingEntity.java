package com.jacl.capstone.world.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.world.World;

/**
 * These are world objects that move.
 * These include enemies and players.
 * 
 * @author Lee
 *
 */
public abstract class MovingEntity extends Entity
{
	//Collision stuff.
	protected float store_x, store_y;
	protected float jump_x, jump_y;
	
	public MovingEntity(World world)
	{
		super(world);
	}

	@Override
	protected Sprite makeSprite()
	{
		return null;
	}
	
	@Override
	public void update(float delta)
	{
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
	}
	
	/**
	 * Do the sprite collision into solid blocks.
	 * Go +-x% of the sprite's width/height away from the centerpoint of the side to get better collisions.
	 * Test for a collision at this point.
	 */
	protected void cellCollision()
	{
		if(world.collision.getCollisionCell(this.getLeft(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getLeft(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getRight(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getRight(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getCenterX() + jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(world.collision.getCollisionCell(this.getCenterX() - jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(world.collision.getCollisionCell(this.getCenterX() + jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
		if(world.collision.getCollisionCell(this.getCenterX() - jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
	}
}
