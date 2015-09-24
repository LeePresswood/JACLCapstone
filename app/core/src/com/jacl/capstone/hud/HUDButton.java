package com.jacl.capstone.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jacl.capstone.screens.ScreenParent;

public abstract class HUDButton
{
	public ScreenParent screen;
	public Sprite sprite;
	
	public boolean is_down;
	
	public HUDButton(ScreenParent screen, float x, float y, float width, float height, Texture texture)
	{
		this.screen = screen;
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, width, height);
	}
	
	public void update(float delta)
	{
		
	}
	
	public void draw()
	{
		
	}
	
	public abstract void upColor();
	public abstract void downColor();
	public abstract void click();
}
