package com.jacl.capstone.world.entities.events.navigation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	private final float FADE_TIME = 0.5f;			//Time to fade (in seconds). Double this to see how long the full transition is. 
	private float current_fade = 0f;					//Current amount of time since fade started.
	private boolean fade_out = true;					//Are we fading in or out?
	
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
		/*
		 * The logic here states that we will fade to black while we leave the current map.
		 * We will fade in from black as we arrive at the new location.
		 * 
		 * Do this in three parts:
		 * 1) Fading out.
		 * 2) Fading in.
		 * 3) Finalizing the new map by re-initializing the world.
		 */
		if(fade_out)
		{
			current_fade += delta;
			
			//Are we ready for Phase 2?
			if(current_fade >= FADE_TIME)
			{
				//Switch to the opposite direction.
				fade_out = false;
				current_fade = FADE_TIME;
				
				//Delete frame buffer.
				sprite.getTexture().dispose();
				
				//Initialize the new map.
				world.init("maps/test.tmx");
			}
		}
		else
		{
			current_fade -= delta;
			
			//Are we ready for Phase 3?
			if(current_fade <= 0f)
			{
				world.event = null;
			}			
		}
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		//Draw the frame buffer during fading out. It is no longer necessary after that.
		if(fade_out)
		{
			batch.begin();
				sprite.draw(batch);
			batch.end();
		}
		//Otherwise, do the draw function of the world.
		else
		{
			world.worldDraw();
		}
		
		
		//Draw the fading.
		Gdx.gl.glEnable(GL20.GL_BLEND);
		world.screen.renderer.setColor(new Color(Color.rgba8888(0f, 0f, 0f, current_fade * 1f / FADE_TIME)));
		world.screen.renderer.begin(ShapeType.Filled);
			world.screen.renderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		world.screen.renderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
