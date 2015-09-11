package com.jacl.capstone.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
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
	
	//Qualities that will be manipulated throughout play.
	public float move_speed;
	public float health;
	public boolean knockback_on_collide;
	public float damage_on_collide;
	
	public MovingEntity(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, alignment);
		
		knockback = new KnockbackHelper(this);
		attack = new AttackHelper(this);
		invincible = new InvincibleHelper(this);
		
		//Health, speed, damage, and knockback_on_collide are set by the entity list.
		this.knockback_on_collide = data.getBoolean("knockback_on_collide");
		this.move_speed = data.getFloat("move_speed") * world.map_handler.tile_size;	//Set in terms of tiles per second.
		this.health = data.getFloat("health");
		this.damage_on_collide = data.getFloat("damage_on_collide");
		
		
	}
	
	@Override
	public void update(float delta)
	{
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
		
		//Now that all movement is done, we can reset the last_location variable.
		last_location.set(sprite.getX(), sprite.getY());
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
	 */
	private void cellCollision()
	{
		for(RectangleMapObject obj : world.collision_handler.collision_objects)
		{
			if(sprite.getBoundingRectangle().overlaps(obj.getRectangle()))
			{//There was a collision. Stop further checking and return to last location.
				//We want to do the return by getting a better view of the overlap.
				Rectangle r = new Rectangle();
				Intersector.intersectRectangles(sprite.getBoundingRectangle(), obj.getRectangle(), r);
				
				System.out.println(r);
				if(r.width > r.height)
				{//Reset Y.
					//Don't stop trapped players from walking away if they get stuck.
					if(this instanceof Player)
					{
						if(r.y > obj.getRectangle().getY() + obj.getRectangle().getHeight() / 2f && Player.class.cast(this).up != true)
						{
							sprite.setY(last_location.y);
						}
						else if(r.y < obj.getRectangle().getY() + obj.getRectangle().getHeight() / 2f && Player.class.cast(this).down != true)
						{
							sprite.setY(last_location.y);
						}
					}
					else
					{
						sprite.setY(last_location.y);
					}
				}
				else
				{//Reset X.
					sprite.setX(last_location.x);
				}
				
				//Avoid a sticky feeling near walls.
				/*if(this instanceof Player)
				{
					if(obj.getRectangle().y + obj.getRectangle().getHeight() > sprite.getBoundingRectangle().y)
					{
						sprite.setY(last_location.y);
					}
					else if(obj.getRectangle().y < sprite.getBoundingRectangle().y + sprite.getBoundingRectangle().height)
					{
						sprite.setY(last_location.y);
					}
					else if(obj.getRectangle().x + obj.getRectangle().getWidth() > sprite.getBoundingRectangle().x)
					{
						sprite.setX(last_location.x);
					}
					else if(obj.getRectangle().x < sprite.getBoundingRectangle().x + sprite.getBoundingRectangle().width)
					{
						sprite.setX(last_location.x);
					}
				}*/
				
				//Return to last_location and end checking.
				//sprite.setPosition(last_location.x, last_location.y);
				return;
			}
		}
	}
	
	/**
	 * Entity was hit by an enemy entity. Set knockback and invincibility.
	 */
	private void hitBy(MovingEntity e)
	{
		if(e.knockback_on_collide)
		{
			//Knockback.
			knockback.doKnockback();
			
			//Invincibility upon hit is only for players.
			if(this instanceof Player)
				invincible.goInvincible();
		}
	}
	
	protected abstract void move(float delta);
	protected abstract void attack(float delta);
}
