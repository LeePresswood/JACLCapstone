package com.jacl.capstone.world.entities;

import com.jacl.capstone.world.World;

/**
 * These are world objects that move. These include enemies and players.
 * On top of movement, however, these entities will interact with combat.
 * Getting hit by a sword will cause knockback, for instance. Thus, they
 * will have combat-related items like health or defense.
 * 
 * @author Lee
 *
 */
public abstract class MovingEntity extends Entity
{
	//Knockback should always be the same amount for continuity throughout the game.
	private boolean being_knocked_back;
	private final float KNOCKBACK_BLOCKS = 1f;
	private final float KNOCKBACK_SPEED = 1.5f;
	private final float KNOCKBACK_DISTANCE;
	private float current_knockback;
	
	//These entities will also become invincible for a period of time after being hit.
	private boolean is_invincible;
	private final float INVINCIBLE_TIME = 1.5f;
	private float invincible_time_current;
	
	//MovingEntities need to be able to move.
	protected float speed;
	public boolean up, down, left, right;
	
	//They will also need to attack.
	public boolean attacking, mid_attack;
	
	//Collision variables.
	protected float store_x, store_y;
	protected float jump_x, jump_y;
	
	public MovingEntity(World world, float x, float y)
	{
		super(world, x, y);
		
		//Knockback block distance is knockback_blocks * size of blocks.
		knockback_distance = KNOCKBACK_DISTANCE * world.map_handler.tile_size;
		
		//Speed is set by the derived classes. Set in terms of tiles per second.
		speed = setSpeed() * world.map_handler.tile_size;
		
		//Block collision detection should only happen at x% of the block's size from the midpoints of the sides.
		final float jump_percent = 0.40f;
		jump_x = jump_percent * sprite.getWidth();
		jump_y = jump_percent * sprite.getHeight();
	}
	
	@Override
	public void update(float delta)
	{
		if(!being_knocked_back)
		{
			move(delta);
			attack(delta);
			entityCollision();
		}
		
		knockback(delta);
		invincible(delta);
		cellCollision();
	}
	
	/**
	 * Do the sprite collision detection with solid blocks.
	 * Go +-x% of the sprite's width/height away from the centerpoint of the side to get better collisions.
	 * Test for a collision at this point.
	 */
	protected void cellCollision()
	{
		//If the cell we collided with is solid, return to our previous position.
		if(left && world.collision_handler.getCollisionCell(this.getLeft(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(left && world.collision_handler.getCollisionCell(this.getLeft(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(right && world.collision_handler.getCollisionCell(this.getRight(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(right && world.collision_handler.getCollisionCell(this.getRight(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(up && world.collision_handler.getCollisionCell(this.getCenterX() + jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(up && world.collision_handler.getCollisionCell(this.getCenterX() - jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(down && world.collision_handler.getCollisionCell(this.getCenterX() + jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
		if(down && world.collision_handler.getCollisionCell(this.getCenterX() - jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
	}
	
	/**
	 * Entity was hit by an enemy entity. Set knockback and invincibility.
	 */
	public void hit()
	{
		//Knockback.
		being_knocked_back = true;
		current_knockback = 0f;
		
		//Invincibility.
		is_invincible = true;
		invincible_time_current = 0f;
	}
	
	private void knockback(float delta)
	{
		if(being_knocked_back)
		{
			current_knockback += delta * KNOCKBACK_SPEED;
			if(current_knockback >= KNOCKBACK_DISTANCE)
			{
				being_knocked_back = false;
			}
		}
	}
	
	private void invincible(float delta)
	{
		if(is_invincible)
		{
			invincible_time_current += delta;
			if(invincible_time_current >= INVINCIBLE_TIME)
			{
				is_invincible = false;
			}
		}	
	}
	
	protected abstract float setSpeed();
	protected abstract void move(float delta);
	protected abstract void attack(float delta);
}
