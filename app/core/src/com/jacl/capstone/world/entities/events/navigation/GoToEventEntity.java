package com.jacl.capstone.world.entities.events.navigation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.EventEntity;

/**
 * Player requested to go from one map to another via doorway/portal/vortex/etc.
 * To show this, the screen will fade the previous screen to black and fade from
 * black to the new map.
 * 
 * @author Lee
 *
 */
public class GoToEventEntity extends EventEntity
{
	private float fade_time = 1f;

	public GoToEventEntity(World world, float x, float y, String... arguments)
	{
		super(world, x, y, arguments);	
		
		//First token is the command. We already know that one. Let's look at the others (starting from 1 rather than 0).
	}

	@Override
	public void init()
	{
		//We will be fading to black in this event. To do so, let's grab the frame buffer as a texture.
		sprite = new Sprite(ScreenUtils.getFrameBufferTexture());
		sprite.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void update(float delta)
	{
		fade_time -= delta;
		
		if(fade_time <= 0)
		{
			world.event = null;
			sprite.getTexture().dispose();
		}
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		batch.begin();
			sprite.draw(batch);
		batch.end();
	}
}
