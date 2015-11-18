package com.jacl.capstone.world.entities.player.items.collectibles;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;

public class Money extends CollectibleItem
{

	public Money(World world, float x, float y)
	{
		super(world, x, y, new Element, alignment);
	}
	
}
