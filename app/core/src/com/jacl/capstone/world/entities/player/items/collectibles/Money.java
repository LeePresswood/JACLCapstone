package com.jacl.capstone.world.entities.player.items.collectibles;

import java.util.Random;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;

public class Money extends CollectibleItem
{
	public int value;

	public Money(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, alignment);
		
		//A money drop was requested. Determine its value.
		value = new Random().nextInt(50);
	}

	@Override
	public void collect()
	{
		remove = true;
		world.screen.hud.money.change(value);
	}
}
