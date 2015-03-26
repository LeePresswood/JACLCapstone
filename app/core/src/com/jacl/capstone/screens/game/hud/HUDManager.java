package com.jacl.capstone.screens.game.hud;

import com.jacl.capstone.screens.game.ScreenGame;

/**
 * Updates and renders the HUD of the world.
 * @author Lee
 *
 */
public class HUDManager
{
	public ScreenGame screen;
	
	public HUDManager(ScreenGame screen)
	{
		this.screen = screen;
	}
	
	public void update(float delta)
	{
		
	}
	
	public void draw()
	{
		screen.batch.begin();
		
		screen.batch.end();
	}
}
