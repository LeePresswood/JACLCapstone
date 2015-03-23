package com.jacl.capstone.screens.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.screens.ScreenParent;

public class ScreenGame extends ScreenParent
{

	public ScreenGame(CapstoneGame game)
	{
		super(game);
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
	}

	@Override
	public void draw()
	{
		//SpriteBatch can be used to draw sprites.
		batch.begin();
		
		batch.end();
		
		//ShapeRenderer can be used to draw basic shapes. Example:
		/*renderer.begin(ShapeType.Filled);
			renderer.rect(0, 0, 50, 50);
		renderer.end();*/
	}
}
