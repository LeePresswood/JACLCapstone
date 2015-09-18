package com.jacl.capstone.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.jacl.capstone.data.enums.EnemyType;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.entities.npc.enemies.EnemyFactory;

public class InputGame implements InputProcessor
{
	public ScreenGame screen;
	
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
			case Keys.W:
				screen.world.entity_handler.player.up = true;
				break;
			case Keys.DOWN:
			case Keys.S:
				screen.world.entity_handler.player.down = true;
				break;
			case Keys.LEFT:
			case Keys.A:
				screen.world.entity_handler.player.left = true;
				break;
			case Keys.RIGHT:
			case Keys.D:
				screen.world.entity_handler.player.right = true;
				break;
			case Keys.SPACE:
				if(screen.hud.dialogue_handler.showing_dialogue)
				{
					
				}
				else
				{
					screen.world.entity_handler.player.attack.attacking = true;
				}
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
			case Keys.W:
				screen.world.entity_handler.player.up = false;
				break;
			case Keys.DOWN:
			case Keys.S:
				screen.world.entity_handler.player.down = false;
				break;
			case Keys.LEFT:
			case Keys.A:
				screen.world.entity_handler.player.left = false;
				break;
			case Keys.RIGHT:
			case Keys.D:
				screen.world.entity_handler.player.right = false;
				break;
			case Keys.SPACE:
				screen.world.entity_handler.player.attack.attacking = false;
				break;
			case Keys.E:
				screen.world.entity_handler.add(EnemyFactory.spawn(EnemyType.SAMPLE_CREEP, screen.world, 2, 4.5f, screen.world.data_handler.entity_root));
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
