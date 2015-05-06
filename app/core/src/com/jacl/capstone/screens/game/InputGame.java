package com.jacl.capstone.screens.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class InputGame implements InputProcessor
{
	ScreenGame screen;
	
	public InputGame(ScreenGame screen)
	{
		this.screen = screen;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		//keycode is an integer representation of the key being used. This is the key in the down position in this case.
		switch(keycode)
		{
			//The Keys class has static references to all keys on the keyboard. We can use these to decode the button click.
			case Keys.UP:
				screen.world.up = true;
				break;
			case Keys.DOWN:
				screen.world.down = true;
				break;
			case Keys.LEFT:
				screen.world.left = true;
				break;
			case Keys.RIGHT:
				screen.world.right = true;
				break;
		}
		
		//Always return true on input methods you use. This tells LibGDX that it is in use and should be reading for it.
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		//keycode is an integer representation of the key being used. This is the key in the up position in this case. Note: Keys that have never been pressed are also in this position. It is not solely a key being up after being down.
		switch(keycode)
		{
			//The Keys class has static references to all keys on the keyboard. We can use these to decode the button click.
			case Keys.UP:
				screen.world.up = false;
				break;
			case Keys.DOWN:
				screen.world.down = false;
				break;
			case Keys.LEFT:
				screen.world.left = false;
				break;
			case Keys.RIGHT:
				screen.world.right = false;
				break;
		}
		
		//Always return true on input methods you use. This tells LibGDX that it is in use and should be reading for it.
		return true;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
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
