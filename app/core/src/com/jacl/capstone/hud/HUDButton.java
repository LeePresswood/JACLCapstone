package com.jacl.capstone.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jacl.capstone.screens.ScreenParent;

public class HUDButton
{
	public ScreenParent screen;
	public Sprite sprite;
	
	public HUDButton(ScreenParent screen, float x, float y, float width, float height, Texture texture)
	{
		this.screen = screen;
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, width, height);
	}

	public void downColor()
   {
   }

	public void click()
   {
   }
	
}
