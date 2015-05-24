package com.jacl.capstone.world.entities;

import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.helpers.AttackAnimator;
import com.jacl.capstone.helpers.Knockbacker;
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
	//Helpers.
	public Knockbacker knockback;
	public AttackAnimator attack;
	
	//Entities will become invincible for a period of time after being hit. Part of this time will be during the knockback.
	private final float INVINCIBLE_TIME = 0.5f;
	private boolean is_invincible;
	private float invincible_time_current;
	
	//Qualities that will be manipulated throughout play.
	public boolean knockback_on_collide;
	public float move_speed;
	public float health;
	public float damage_on_bump;
	
	//Collision variables.
	protected float collision_last_x, collision_last_y;
	protected float collision_from_center_x, collision_from_center_y;
	
	//The alignment of entity this is will determine knockback and targetting.
	public Alignment alignment;	
	
	public MovingEntity(World world, float x, float y, boolean knockback_on_collide, float move_speed, float health, float damage_on_bump, Alignment alignment)
	{
		super(world, x, y);
		this.alignment = alignment;
		
		knockback = new Knockbacker(this);
		attack = new AttackAnimator(this);
		
		//Knockback is dependent upon the direction the entity is facing. If no movement happens before being hit, no direction is set.
		knockback.knockback_direction = Direction.DOWN;
		
		//Health, speed, damage, and knockback_on_collide are set by the derived classes.
		this.knockback_on_collide = knockback_on_collide;
		this.move_speed = move_speed * world.map_handler.tile_size;	//Set in terms of tiles per second.
		this.health = health;
		this.damage_on_bump = damage_on_bump;
		
		//Block collision detection should only happen at x% of the block's size from the midpoints of the sides.
		final float jump_percent = 0.40f;
		collision_from_center_x = jump_percent * sprite.getWidth();
		collision_from_center_y = jump_percent * sprite.getHeight();
	}
	
	@Override
	public void update(float delta)
	{
		//Store the current location for collision detection in the future.
		collision_last_x = sprite.getX();
		collision_last_y = sprite.getY();
		
		if(knockback.being_knocked_back)
		{//During knockback, we need to update the knockback variables.
			knockback.doKnockback(delta);
		}
		else
		{//If not being knocked back, update normally with free movement.
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
			if(alignment == Alignment.PLAYER)
			{
				for(MovingEntity e : world.entity_handler.enemies)
				{
					world.collision_handler.newCollidesWith(this, e);
					if(this.sprite.getBoundingRectangle().overlaps(e.sprite.getBoundingRectangle()))
					{//There was a collision.
						this.hitBy(e);
						e.hitBy(this);
					}					
				}
			}
			else if(alignment == Alignment.ENEMY)
			{
				
			}
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
		if(world.collision_handler.getCollisionCell(this.getLeft(), this.getCenterY() + collision_from_center_y) != null)
			sprite.setX(collision_last_x);
		if(world.collision_handler.getCollisionCell(this.getLeft(), this.getCenterY() - collision_from_center_y) != null)
			sprite.setX(collision_last_x);
		if(world.collision_handler.getCollisionCell(this.getRight(), this.getCenterY() + collision_from_center_y) != null)
			sprite.setX(collision_last_x);
		if(world.collision_handler.getCollisionCell(this.getRight(), this.getCenterY() - collision_from_center_y) != null)
			sprite.setX(collision_last_x);
		if(world.collision_handler.getCollisionCell(this.getCenterX() + collision_from_center_x, this.getTop()) != null)
			sprite.setY(collision_last_y);
		if(world.collision_handler.getCollisionCell(this.getCenterX() - collision_from_center_x, this.getTop()) != null)
			sprite.setY(collision_last_y);
		if(world.collision_handler.getCollisionCell(this.getCenterX() + collision_from_center_x, this.getBottom()) != null)
			sprite.setY(collision_last_y);
		if(world.collision_handler.getCollisionCell(this.getCenterX() - collision_from_center_x, this.getBottom()) != null)
			sprite.setY(collision_last_y);
			
		//If we didn't end up moving, we can turn off knockback.
		if(Math.abs(sprite.getX() - collision_last_x) < 0.5f && Math.abs(sprite.getY() - collision_last_y) < 0.5f)
			knockback.being_knocked_back = false;
	}
	
	/**
	 * Entity was hit by an enemy entity. Set knockback and invincibility.
	 */
	private void hitBy(MovingEntity e)
	{
		if(e.knockback_on_collide)
		{
			//Knockback.
			knockback.being_knocked_back = true;
			knockback.current_knockback = 0f;
			
			//Invincibility.
			is_invincible = true;
			invincible_time_current = 0f;	
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
	
	protected abstract void move(float delta);
	protected abstract void attack(float delta);
}
