package com.jacl.capstone.world.entities.npc.enemies.creeps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.enemies.Enemy;

public class SampleCreep extends Enemy
{

	public SampleCreep(World world, float x, float y)
	{
		super(world, x, y);
	}

	@Override
	protected Sprite makeSprite(float x, float y)
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
