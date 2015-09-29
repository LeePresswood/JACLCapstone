package com.jacl.capstone.world.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.helpers.AttackHelper;
import com.jacl.capstone.helpers.DamageCalculator;
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
	
	public float health_current;
	public float health_max;
	public float health_regen;
	
	//Qualities that will be manipulated throughout play.
	public float move_speed;
	public boolean knockback_on_collide;
	public float damage_on_collide;
	public float defense;
	
	//Enemy list.
	public ArrayList<MovingEntity> enemies;
	
	//Movement sprites.
	private final int MOVE_FRAMES = 2;
	private Texture[] up_frames;
	private Texture[] down_frames;
	private Texture[] left_frames;
	private Texture[] right_frames;
	
	public MovingEntity(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, alignment);
		
		knockback = new KnockbackHelper(this);
		attack = new AttackHelper(this);
		invincible = new InvincibleHelper(this);
		
		//Health, speed, damage, and knockback_on_collide are set by the entity list.
		knockback_on_collide = data.getBoolean("knockback_on_collide");
		move_speed = data.getFloat("move_speed") * world.map_handler.tile_size;	//Set in terms of tiles per second.
		damage_on_collide = data.getFloat("damage_on_collide");
		
		//Set movement sprites.
		setMovementSprites(data.get("texture_folder"));
	}
	
	/**
	 * Go into the given folder and turn the images into sprites.
	 * @param folder Folder to search.
	 */
	private void setMovementSprites(String folder)
	{
		//Create arrays for the textures.
		up_frames = new Texture[MOVE_FRAMES];
		down_frames = new Texture[MOVE_FRAMES];
		left_frames = new Texture[MOVE_FRAMES];
		right_frames = new Texture[MOVE_FRAMES];
		
		String base_texture_folder = "textures/" + folder;
		
		for(FileHandle file : Gdx.files.internal(base_texture_folder).list())
		{
			//Get the file that we're looking at.
			String file_name = file.nameWithoutExtension();
			String[] parts = file_name.split("_");
			
			//parts[1] is what we're really interested in. It will tell us the direction and the frame.
			if(parts[1].startsWith("bk"))
			{//Back
				up_frames[Character.getNumericValue(parts[1].charAt(parts[1].length() - 1)) - 1] = world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("fr"))
			{//Front
				down_frames[Character.getNumericValue(parts[1].charAt(parts[1].length() - 1)) - 1] = world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("lf"))
			{//Left
				left_frames[Character.getNumericValue(parts[1].charAt(parts[1].length() - 1)) - 1] = world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("rt"))
			{//Right
				right_frames[Character.getNumericValue(parts[1].charAt(parts[1].length() - 1)) - 1] = world.screen.game.assets.get(file_name, Texture.class);
			}
		}
		
		//On top of loading the images, we're also interested in setting the sprite's texture after loading. Let's just use up_frames[1].
		sprite.setRegion(up_frames[1]);
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
		
		//Calculate regeneration (if we want this).
		changeCurrentHealthValueBy(health_regen * delta);
		
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
			if(e instanceof MovingEntity && e.alignment != Alignment.NEUTRAL && this.alignment != e.alignment)
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
			//Knockback and damage.
			knockback.doKnockback();
			health_current -= DamageCalculator.getDamage(e.damage_on_collide, defense);
			
			//Invincibility upon hit is only for players.
			if(this instanceof Player)
			{
				invincible.goInvincible();
			}
		}
	}
	
	/**
	 * Change current health to the new value.
	 * @param new_value
	 */
	public void changeCurrentHealthValueTo(float new_value)
	{
		health_current = new_value;
		if(health_current < 0.0f)
		{
			health_current = 0.0f;
		}
		if(health_current > health_max)
		{
			health_current = health_max;
		}
	}
	
	/**
	 * Change current health by the change_by amount.
	 * @param change_by
	 */
	public void changeCurrentHealthValueBy(float change_by)
	{
		health_current += change_by;
		if(health_current < 0.0f)
		{
			health_current = 0.0f;
		}
		if(health_current > health_max)
		{
			health_current = health_max;
		}
	}
	
	/**
	 * Change max health to the new value.
	 * @param new_value
	 */
	public void changeMaxHealthValueTo(float new_max)
	{
		health_max = new_max;
		if(health_max < 0.0f)
		{
			health_max = 0.0f;
		}
		if(health_current > health_max)
		{
			health_current = health_max;
		}
	}
	
	/**
	 * Change max health by the change_by amount.
	 * @param change_by
	 */
	public void changeMaxHealthValueBy(float change_by)
	{
		health_max += change_by;
		if(health_max < 0.0f)
		{
			health_max = 0.0f;
		}
	}
	
	protected abstract void move(float delta);
	protected abstract void attack(float delta);
}
