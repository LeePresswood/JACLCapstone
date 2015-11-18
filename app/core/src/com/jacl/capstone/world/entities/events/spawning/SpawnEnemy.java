package com.jacl.capstone.world.entities.events.spawning;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.EventEntity;

public class SpawnEnemy extends EventEntity
{

	public SpawnEnemy(World world, float x, float y, String[] arguments)
	{
		super(world, x, y, arguments);
	}

	@Override
	public void init()
	{
	}

	@Override
	public void update(float delta)
	{//Nothing needs to be initialized here. This event is very simple.
	}

	@Override
	public void draw(SpriteBatch batch)
	{
	}

	@Override
	public boolean canCollide()
	{
		return false;
	}
}
