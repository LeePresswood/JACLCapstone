package com.jacl.capstone.input;

import com.badlogic.gdx.InputProcessor;
import com.jacl.capstone.hud.HUDButton;
import com.jacl.capstone.screens.ScreenMainMenu;

public class InputMainMenu implements InputProcessor
{
	public ScreenMainMenu screen;
	public HUDButton button_clicked_down; 
	
	public InputMainMenu(ScreenMainMenu screen)
   {
		this.screen = screen;
   }

	@Override
   public boolean keyDown(int keycode)
   {
	   return false;
   }

	@Override
   public boolean keyUp(int keycode)
   {
	   return false;
   }

	@Override
   public boolean keyTyped(char character)
   {
	   return false;
   }

	@Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button)
   {
		//Determine if the clicked location contains a button. Do this by looping through all the buttons.
		for(HUDButton b : screen.buttons)
		{
			if(b.sprite.getBoundingRectangle().contains(screenX, screenY))
			{
				button_clicked_down = b;
				b.downColor();
				break;
			}
		}
		
	   return true;
   }

	@Override
   public boolean touchUp(int screenX, int screenY, int pointer, int button)
   {
		//Determine if the clicked location contains a button. Do this by looping through all the buttons.
		for(HUDButton b : screen.buttons)
		{
			b.upColor();
			
			if(b.sprite.getBoundingRectangle().contains(screenX, screenY) && button_clicked_down.equals(b))
			{
				b.click();
				break;
			}
		}
		
	   return true;
   }

	@Override
   public boolean touchDragged(int screenX, int screenY, int pointer)
   {
	   return false;
   }

	@Override
   public boolean mouseMoved(int screenX, int screenY)
   {
	   return false;
   }

	@Override
   public boolean scrolled(int amount)
   {
	   return false;
   }
	
}
