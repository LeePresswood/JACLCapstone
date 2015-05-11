package com.jacl.capstone.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.hud.HUDManager;
import com.jacl.capstone.input.InputGame;
import com.jacl.capstone.world.World;

public class ScreenGame extends ScreenParent
{
	public World world;
	public HUDManager hud;
	
	public ScreenGame(CapstoneGame game)
	{
		super(game);
		
		world = new World(this);
		hud = new HUDManager(this);
	}

	@Override
	public Color setUpBackgroundColor()
	{
		return new Color(0f, 0f, 0f, 0f);
	}

	@Override
	public InputProcessor setUpInput()
	{
		return new InputGame(this);
	}

	@Override
	public void update(float delta)
	{
		//Update world first and HUD second to stay parallel with the draw method.
		world.update(delta);
		hud.update(delta);
	}

	@Override
	public void draw()
	{
		/* We want to draw the world first and the HUD second. We do it this
		 * way to assure that the HUD is drawn over the world (bottom of the
		 * draw buffer for that pixel is the first thing that was drawn in
		 * this method).
		 */
		world.draw();
		hud.draw();
	}
}
