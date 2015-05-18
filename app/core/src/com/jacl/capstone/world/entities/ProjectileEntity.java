package com.jacl.capstone.world.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.world.World;

/**
 * Projectiles fall under this category. Extend this to any spells, arrows, or thrown items.
 * 
 * @author Lee
 *
 */
public abstract class ProjectileEntity extends Entity
{
	protected Vector2 position;			//Current location of the projectile. Equal to center of the sprite.
	protected float angle;					//Angle (in degrees) the projectile is moving.
	protected float speed;					//Speed (in blocks per second) of the projectile.
	
	public ProjectileEntity(World world, float x1, float y1, float x2, float y2)
	{
		super(world, x1, x2);
		
		//Set position vector and related attributes.
		position = new Vector2(x1, y1);
		angle = MathUtils.atan2(y2 - y1, x2 - x1) * MathUtils.radiansToDegrees;
		
		//Speed is set by the derived classes. Set in terms of tiles per second.
		speed = setSpeed() * world.map_manager.tile_size;
		
		//Center the sprite around the vector.
		sprite.setPosition(position.x - sprite.getWidth() / 2f, position.y - sprite.getHeight() / 2f);
	}
	
	@Override
	public void update(float delta)
	{
		//Update position. It will be different for each type of projectile.
		move(delta);
		
		//Center sprite around this new position.
		sprite.setPosition(position.x - sprite.getWidth() / 2f, position.y - sprite.getHeight() / 2f);
		
		//Check collision. It will be different for each type of projectile.
		collision();
	}
	
	protected abstract float setSpeed();
	protected abstract void move(float delta);
	protected abstract void collision();
}
