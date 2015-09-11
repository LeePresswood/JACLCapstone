package com.jacl.capstone.world.entities.npc.enemies.creeps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.ai.AI;
import com.jacl.capstone.world.entities.npc.ai.PathfindingAI;
import com.jacl.capstone.world.entities.npc.enemies.Enemy;

/**
 * This enemy will not be in the final game. Purely for testing.
 */
public class SampleCreep extends Enemy
{
	public SampleCreep(World world, float x, float y, Element data)
	{
		super(world, x, y, data.getChildByName("sample_creep"), Alignment.ENEMY);
	}
	
	@Override
	protected AI initAI()
	{
		//return new LerpAI(this);
		return new PathfindingAI(this);
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
