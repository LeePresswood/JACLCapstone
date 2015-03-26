package com.jacl.capstone.screens.game.world;

import com.jacl.capstone.screens.game.ScreenGame;

/**
 * Handles the updating and rendering of game objects.
 * @author Lee
 *
 */
public class WorldManager
{
	public ScreenGame screen; 
	
	public WorldManager(ScreenGame screen)
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
