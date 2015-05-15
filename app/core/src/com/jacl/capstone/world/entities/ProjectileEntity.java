package com.jacl.capstone.world.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.world.World;

/**
 * Projectiles fall under this category. Extend this to any spells, arrows, or thrown items.
 * @author Lee
 *
 */
public abstract class ProjectileEntity extends Entity
{
	
	public ProjectileEntity(World world)
	{
		super(world);
	}

	@Override
	protected Sprite makeSprite()
	{
		return null;
	}
	
	@Override
	public void update(float delta)
	{
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
	}
	
}
