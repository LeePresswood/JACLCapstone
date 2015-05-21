package com.jacl.capstone.world.entities.npc.enemies.creeps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.enemies.Enemy;

/**
 * This enemy will not be in the final game. Purely for testing.
 */
public class SampleCreep extends Enemy
{

	public SampleCreep(World world, float x, float y)
	{
		super(world, x, y);
	}

	@Override
	protected Sprite makeSprite(float x, float y)
	{
		Sprite s = new Sprite(world.screen.game.assets.get(Assets.PLAYER, Texture.class);
		s.setBounds(x, y, 1f * world.map_handler.tile_size, 1f * world.map_handler.tile_size);
		return s;
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
