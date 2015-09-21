package com.jacl.capstone.hud.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.jacl.capstone.hud.HUD;

/**
 * Represents the player's current health in a visual sense. Note: Don't use this
 * visual representation when checking health-related functions (like dying or healing).
 * Instead, we want to have a changeHealth() method in the Player class. This method
 * will call the changeValue() method in this class.
 * @author Lee
 */
public class HealthBar
{
	public HUD hud;
	
	private NinePatch HealthBarBackground;
	private NinePatch HealthBar;
	
	private float current;
	private float max;
	
	private float regen = 0.1f;
	
	float totalBarWidth = 150;
	float width= 0.9f * totalBarWidth;
	float x = 9f;
	float y = 9f;
	
	public HealthBar(HUD hud)
	{
		this.hud = hud;
		
		max = 10f;
		current = 5f;
		
		HealthBarBackground = new NinePatch(new Texture(Gdx.files.internal("health-red.png")),5,5,2,2);
		HealthBar = new NinePatch(new Texture(Gdx.files.internal("health-blue.png")),5,5,2,2);
	}
	
	public void changeCurrentValueTo(float new_value)
	{
		current = new_value;
		if(current > max)
		{
			current = max;
		}
	}
	
	public void changeCurrentValueBy(float change_by)
	{
		current = change_by;
		if(current > max)
		{
			current = max;
		}
	}
	
	public void changeMaxValueTo(float new_max)
	{
		max = new_max;
		if(current > max)
		{
			current = max;
		}
	}
	
	public void changeMaxValueBy(float change_by)
	{
		max += change_by;
	}
	
	public void update(float delta)
	{//Use this for animation setting the width of the bar.
		//Animation.
		
		
		//Regen (if we want this).
		changeCurrentValueBy(regen * delta);
		
		//Bar width.
		width = totalBarWidth * current / max;
	}
	
	public void draw()
	{
		HealthBarBackground.draw(hud.screen.batch, 10, 10, totalBarWidth, 8);
		HealthBar.draw(hud.screen.batch, 10, 10, width, 8);
	}
}
