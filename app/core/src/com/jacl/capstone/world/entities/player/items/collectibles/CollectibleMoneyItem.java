package com.jacl.capstone.world.entities.player.items.collectibles;

import java.util.Random;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;

public class CollectibleMoneyItem extends CollectibleItem
{
	public int value;

	public CollectibleMoneyItem(World world, float x, float y, Element data)
	{
		super(world, x, y, data, Alignment.PLAYER);
		
		//A money drop was requested. Determine its value.
		value = new Random().nextInt(49) + 1;
	}

	@Override
	public void collect()
	{
		remove = true;
		world.screen.hud.money.change(value);
	}
}
