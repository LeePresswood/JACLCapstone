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
	
	private NinePatch health_bar_background;
	private NinePatch health_bar_foreground;
	
	private float current;
	private float max;
	private float regen;
	
	private float width;
	
	private final float X = 9f;
	private final float Y = 9f;
	private final float CONSTANT_WIDTH = 150f;
	private final float CONSTANT_HEIGHT = 8f;
	
	public HealthBar(HUD hud)
	{
		this.hud = hud;
		
		max = 10f;
		current = 5f;
		
		health_bar_background = new NinePatch(new Texture(Gdx.files.internal("health-red.png")),5,5,2,2);
		health_bar_foreground = new NinePatch(new Texture(Gdx.files.internal("health-blue.png")),5,5,2,2);
	}
	
	/**
	 * Called after loading from a save.
	 * @param max
	 * @param current
	 * @param regen
	 */
	public void init(float max, float current, float regen)
	{
		this.max = max;
		this.current = current;
		this.regen = regen;
	}
	
	/**
	 * Change current variable to the new value.
	 * @param new_value
	 */
	public void changeCurrentValueTo(float new_value)
	{
		current = new_value;
		if(current > max)
		{
			current = max;
		}
	}
	
	/**
	 * Change current variable by the change_by amount.
	 * @param change_by
	 */
	public void changeCurrentValueBy(float change_by)
	{
		current += change_by;
		if(current > max)
		{
			current = max;
		}
	}
	
	/**
	 * Change max variable to the new value.
	 * @param new_value
	 */
	public void changeMaxValueTo(float new_max)
	{
		max = new_max;
		if(current > max)
		{
			current = max;
		}
	}
	
	/**
	 * Change max variable by the change_by amount.
	 * @param change_by
	 */
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
		width = CONSTANT_WIDTH * current / max;
	}
	
	public void draw()
	{
		health_bar_background.draw(hud.screen.batch, X, Y, CONSTANT_WIDTH, CONSTANT_HEIGHT);
		health_bar_foreground.draw(hud.screen.batch, X, Y, width, CONSTANT_HEIGHT);
	}
	
	/**
	 * We're trying to save the game. Package the saveable items here.
	 * @return In order: max, current, regen.
	 */
	public float[] packageForSave()
	{
		return new float[]{max, current, regen};
	}
}
