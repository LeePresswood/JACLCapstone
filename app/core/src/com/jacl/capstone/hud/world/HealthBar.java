package com.jacl.capstone.hud.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.jacl.capstone.hud.HUD;

public class HealthBar
{
	public HUD hud;
	
	private NinePatch HealthBarBackground;
	private NinePatch HealthBar;
	
	float totalBarWidth = 150;
	float width= 0.9f * totalBarWidth;
	float x = 9f;
	float y = 9f;
	
	public HealthBar(HUD hud)
	{
		this.hud = hud;
		
		HealthBarBackground = new NinePatch(new Texture(Gdx.files.internal("health-red.png")),5,5,2,2);
		HealthBar = new NinePatch(new Texture(Gdx.files.internal("health-blue.png")),5,5,2,2);
	}
	
	public void update(float delta)
	{
		
	}
	
	public void draw()
	{
		HealthBarBackground.draw(hud.screen.batch, 10, 10, totalBarWidth, 8);
		HealthBar.draw(hud.screen.batch, 10, 10, width, 8);
	}
}
