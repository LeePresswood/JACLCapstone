package com.jacl.capstone.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.hud.HUDButton;
import com.jacl.capstone.input.InputMainMenu;

/**
 * 
 * @author Lee, huy_trinh 
 * This should be main menu in the game play where it has menu to pause game, 
 * Options to use item, etc.
 * 
 */
public class ScreenMainMenu extends ScreenParent
{
	public HUDButton[] buttons;
	
	public ScreenMainMenu(CapstoneGame game)
	{
		super(game);
	}
	
	public void draw()
	{
		/*
		 * Draw Background and 4 buttons: New Game, Load, Options and Credits
		 */
		
	}
	
	/**
	 * This is called when the user click button New Game: start a new game Load:
	 * Gather the player's location and time from the save file. Options: show
	 * volume, graphic and such Credits: mostly name
	 */
	public void update(float delta)
	{
		
	}
	
	@Override
	public Color setUpBackgroundColor()
	{
		return Color.BLACK;
	}
	
	@Override
	public InputProcessor setUpInput()
	{
		return new InputMainMenu(this);
	}
	
	/**
	 * This is called when the user clicks the new game button.
	 */
	public void newGameButtonClick()
	{
		
	}
}
