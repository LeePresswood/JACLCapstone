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
	protected Directions last_direction;
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
		
		//Knockback is dependent upon the direction the entity is facing. If no movement happens before being hit, no direction is set.
		last_direction = Directions.DOWN;
		
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
		//Store the current location for collision detection in the future.
		store_x = sprite.getX();
		store_y = sprite.getY();
		
		if(being_knocked_back)
		{//During knockback, we need to update the knockback variable.
			knockback(delta);	
		}
		if(!being_knocked_back)
		{//If not being knocked back
			move(delta);
			attack(delta);
			entityCollision();
		}
		
		//Calculate invincibility frames.
		invincible(delta);
		
		//Calculate solid block collision.
		cellCollision();
	}
	
	private void entityCollision()
	{
		//We don't want to be hit while we're invincible.
		if(!is_invincible)
		{
			//Scan through all the entities that are enemies to this entity.
			
		}
	}
	
	/**
	 * Do the sprite collision detection with solid blocks.
	 * Go +-x% of the sprite's width/height away from the centerpoint of the side to get better collisions.
	 * Test for a collision at this point.
	 */
	private void cellCollision()
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
			
		//If we didn't end up moving, we can turn off knockback.
		if(sprite.getX() == store_x && sprite.getY() == store_y)
			being_knocked_back = false;
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
			//Calculate the knockback.
			current_knockback += delta * KNOCKBACK_SPEED;
			if(current_knockback >= KNOCKBACK_DISTANCE)
			{
				being_knocked_back = false;
			}
			
			//Do the knockback movement. This will depend upon the last direction the entity moved.
			//Direction moved is opposite of the direction facing.
			if(last_direction == Directions.LEFT)
				sprite.translateX(delta * KNOCKBACK_SPEED);
			if(last_direction == Directions.RIGHT)
				sprite.translateX(-delta * KNOCKBACK_SPEED);
			if(last_direction == Directions.UP)
				sprite.translateY(-delta * KNOCKBACK_SPEED);
			if(last_direction == Directions.DOWN)
				sprite.translateY(delta * KNOCKBACK_SPEED);
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
