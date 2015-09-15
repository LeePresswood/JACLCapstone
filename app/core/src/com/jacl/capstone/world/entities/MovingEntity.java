package com.jacl.capstone.world.entities;

import java.util.ArrayList;

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
 * @author Lee
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
	
	//Enemy list.
	public ArrayList<MovingEntity> enemies;
	
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
		world.collision_handler.cellCollision(this, last_location);
		
		//Now that all movement is done, we can reset the last_location variable.
		last_location.set(sprite.getX(), sprite.getY());
	}
	
	private void entityCollision()
	{
		//We don't want to be hit while we're invincible. Completely skip this step if so.
		if(!invincible.is_invincible)
		{
			//The main logic happens in the CollisionHandler. Get a list of this entity's enemies and send it there.
			getEnemies();
			world.collision_handler.entityCollision(this, enemies);
		}
	}
	
	private void getEnemies()
	{
		if(enemies == null)
		{
			enemies = new ArrayList<MovingEntity>();
		}
		
		//Clear the enemy list and begin adding the enemies.
		enemies.clear();
		for(Entity e : world.entity_handler.all_entities)
		{
			if(e instanceof MovingEntity && this.alignment != e.alignment && e.alignment != Alignment.NEUTRAL)
			{
				enemies.add((MovingEntity) e);
			}
		}
	}
	
	/**
	 * Entity was hit by an enemy entity. Set knockback and invincibility.
	 * @param e The enemy this collided with.
	 */
	public void hitBy(MovingEntity e)
	{
		if(e.knockback_on_collide)
		{
			//Knockback.
			knockback.doKnockback();
			
			//Invincibility upon hit is only for players.
			if(this instanceof Player)
			{
				invincible.goInvincible();
			}
		}
	}
	
	protected abstract void move(float delta);
	protected abstract void attack(float delta);
}
