package com.jacl.capstone.world.entities.player.items.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;

public abstract class CollectibleItem extends Entity
{	
	public CollectibleItem(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data.getChildByName("money"), alignment);
	}

	@Override
	public void update(float delta)
	{
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	public abstract void collect();
}
