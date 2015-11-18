package com.jacl.capstone.world.entities.events.spawning;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.data.enums.EnemyType;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.EventEntity;
import com.jacl.capstone.world.entities.npc.enemies.EnemyFactory;

public class SpawnEnemy extends EventEntity
{

	public SpawnEnemy(World world, float x, float y, String[] arguments)
	{
		super(world, x, y, arguments);
		
		//The only thing we're interested in is spawning the enemy.
		world.entity_handler.add(EnemyFactory.spawn(EnemyType.valueOf(arguments[0].toUpperCase()), world, x, y, world.data_handler.entity_root));
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
