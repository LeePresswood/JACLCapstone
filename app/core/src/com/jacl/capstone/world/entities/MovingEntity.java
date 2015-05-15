package com.jacl.capstone.world.entities;

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
	//MovingEntities need to be able to move.
	public float speed;
	
	//Collision variables.
	protected float store_x, store_y;
	protected float jump_x, jump_y;
	
	public MovingEntity(World world)
	{
		super(world);
		
		//Speed is set by the derived classes.
		speed = setSpeed();
		
		//Block collision detection should only happen at x% of the block's size from the midpoints of the sides.
		final float jump_percent = 0.35f;
		jump_x = jump_percent * sprite.getWidth();
		jump_y = jump_percent * sprite.getHeight();
	}
	
	protected abstract float setSpeed();
	protected abstract void move(float delta);
	
	@Override
	public void update(float delta)
	{
		//Move.
		move(delta);
		
		//Check collision.
		cellCollision();	
	}
	
	/**
	 * Do the sprite collision detection with solid blocks.
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
