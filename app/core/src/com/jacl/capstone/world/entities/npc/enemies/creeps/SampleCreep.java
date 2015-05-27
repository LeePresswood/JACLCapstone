package com.jacl.capstone.world.entities.npc.enemies.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.ai.AI;
import com.jacl.capstone.world.entities.npc.ai.NothingAI;
import com.jacl.capstone.world.entities.npc.enemies.Enemy;

/**
 * This enemy will not be in the final game. Purely for testing.
 */
public class SampleCreep extends Enemy
{
	public SampleCreep(World world, float x, float y)
	{
		super(world, x, y, true, 3f, 10f, 10f, Alignment.ENEMY);
	}

	@Override
	protected Sprite makeSprite(float x, float y)
	{
		Sprite s = new Sprite(world.screen.game.assets.get(Assets.PLAYER, Texture.class));
		s.setBounds(x * world.map_handler.tile_size, y * world.map_handler.tile_size, 1f * world.map_handler.tile_size, 1f * world.map_handler.tile_size);
		return s;
	}
	
	@Override
	protected AI initAI()
	{
		return new NothingAI(this);
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}

	@Override
	protected void attack(float delta)
	{
	}
}
