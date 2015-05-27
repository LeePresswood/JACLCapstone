package com.jacl.capstone.world.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.helpers.AttackHelper;
import com.jacl.capstone.helpers.InvincibleHelper;
import com.jacl.capstone.helpers.KnockbackHelper;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.player.Player;

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
	public KnockbackHelper knockback;
	public AttackHelper attack;
	public InvincibleHelper invincible;	
	
	//Collision variables.
	private Rectangle cell;
	private Circle circle;
	private float circle_shrink = 0.8f;
	private float collision_last_x, collision_last_y;
	private float collision_from_center_x, collision_from_center_y;
	
	//Qualities that will be manipulated throughout play.
	public boolean knockback_on_collide;
	public float move_speed;
	public float health;
	public float damage_on_bump;
	public Alignment alignment;
	
	public MovingEntity(World world, float x, float y, boolean knockback_on_collide, float move_speed, float health, float damage_on_bump, Alignment alignment)
	{
		super(world, x, y);
		this.alignment = alignment;
		
		knockback = new KnockbackHelper(this);
		attack = new AttackHelper(this);
		invincible = new InvincibleHelper(this);
		
		//Health, speed, damage, and knockback_on_collide are set by the derived classes.
		this.knockback_on_collide = knockback_on_collide;
		this.move_speed = move_speed * world.map_handler.tile_size;	//Set in terms of tiles per second.
		this.health = health;
		this.damage_on_bump = damage_on_bump;
		
		//Block collision detection should only happen at x% of the block's size from the midpoints of the sides.
		final float jump_percent = 0.40f;
		collision_from_center_x = jump_percent * sprite.getWidth();
		collision_from_center_y = jump_percent * sprite.getHeight();
		
		circle = new Circle(getCenterX(), getCenterY(), sprite.getWidth() / 2f * circle_shrink);
	}
	
	@Override
	public void update(float delta)
	{
		//Store the current location for collision detection in the future.
		collision_last_x = sprite.getX();
		collision_last_y = sprite.getY();
		
		if(knockback.is_being_knocked_back)
		{//During knockback, we need to update the knockback variables.
			knockback.update(delta);
		}
		else
		{//If not being knocked back, update normally with free movement.
			move(delta);
			attack(delta);
			entityCollision();
		}
		
		//Calculate invincibility frames if necessary.
		invincible.update(delta);
		
		//Calculate solid block collision.
		cellCollision();
		
		//Reset collision circle.
		circle.setPosition(getCenterX(), getCenterY());
	}
	
	private void entityCollision()
	{
		//We don't want to be hit while we're invincible.
		if(!invincible.is_invincible)
		{
			//Scan through all the entities that are enemies to this entity.
			if(alignment == Alignment.PLAYER)
			{
				for(MovingEntity e : world.entity_handler.enemies)
				{
					world.collision_handler.collidesWith(this, e);
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
		cell = world.collision_handler.collidesWithTile(circle);
		if(cell != null)
		{
			Vector2 segment1 = new Vector2();
			Vector2 segment2 = new Vector2();
			Vector2 circle_center = new Vector2(circle.x, circle.y);
			
			//Bottom of cell.
			segment1.set(cell.x, cell.x);
			segment2.set(cell.x, cell.x + cell.width);
			if(Intersector.intersectSegmentCircle(segment1, segment2, circle_center, circle.radius * circle.radius))
				sprite.setPosition(collision_last_x, collision_last_y);
		}
		
		
		
		
		/*if(world.collision_handler.getCollisionCell(this.getLeft(), this.getCenterY() + collision_from_center_y) != null)
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
			sprite.setY(collision_last_y);*/
	}
	
	/**
	 * Entity was hit by an enemy entity. Set knockback and invincibility.
	 */
	private void hitBy(MovingEntity e)
	{
		if(e.knockback_on_collide && this instanceof Player)
		{
			//Knockback.
			knockback.doKnockback();
			
			//Invincibility.
			invincible.goInvincible();
		}
	}
	
	protected abstract void move(float delta);
	protected abstract void attack(float delta);
}
