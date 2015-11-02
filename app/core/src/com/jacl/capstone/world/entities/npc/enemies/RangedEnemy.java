package com.jacl.capstone.world.entities.npc.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.ai.AI;

public class RangedEnemy extends Enemy
{

	public RangedEnemy(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, alignment);
	}

	@Override
	protected AI initAI()
	{
		return null;
	}

	@Override
	public void draw(SpriteBatch batch)
	{
	}
	
}
