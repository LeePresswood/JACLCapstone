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
	protected float speed;
	
	//Collision variables.
	protected float store_x, store_y;
	protected float jump_x, jump_y;
	
	public MovingEntity(World world, float x, float y)
	{
		super(world, x, y);
		
		//Speed is set by the derived classes. Set in terms of tiles per second..
		speed = setSpeed() * world.map_manager.TILE_SIZE;
		
		//Block collision detection should only happen at x% of the block's size from the midpoints of the sides.
		final float jump_percent = 0.40f;
		jump_x = jump_percent * sprite.getWidth();
		jump_y = jump_percent * sprite.getHeight();
	}
	
	@Override
	public void update(float delta)
	{
		//Move.
		move(delta);
		
		//Do attack if necessary.
		attack(delta);
		
		//Check collision.
		cellCollision(true, true, true, true);	
	}
	
	/**
	 * Do the sprite collision detection with solid blocks.
	 * Go +-x% of the sprite's width/height away from the centerpoint of the side to get better collisions.
	 * Test for a collision at this point.
	 */
	protected void cellCollision(boolean left, boolean right, boolean up, boolean down)
	{
		//If the cell we collided with is solid, return to our previous position.
		if(left && world.collision.getCollisionCell(this.getLeft(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(left && world.collision.getCollisionCell(this.getLeft(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(right && world.collision.getCollisionCell(this.getRight(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(right && world.collision.getCollisionCell(this.getRight(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(up && world.collision.getCollisionCell(this.getCenterX() + jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(up && world.collision.getCollisionCell(this.getCenterX() - jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(down && world.collision.getCollisionCell(this.getCenterX() + jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
		if(down && world.collision.getCollisionCell(this.getCenterX() - jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
	}
	
	protected abstract float setSpeed();
	protected abstract void move(float delta);
	protected abstract void attack(float delta);
}
