package com.jacl.capstone.hud.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.hud.HUD;

/**
 * This class handles the text within a dialogue box. This text is calculated by being given the full dialogue and
 * the index of the text. Break this text into multiple textboxes. 
 * @author Lee
 */
public class DialogueBox
{
	public HUD hud;
	
	private BitmapFont font;
	private String text;
	
	private float x;
	private float y;
	private final float DIALOGUE_WIDTH = Gdx.graphics.getWidth() > 500f ? 500f : Gdx.graphics.getWidth();
	private final float DIALOGUE_HEIGHT = Gdx.graphics.getHeight() > 300f ? 500f : Gdx.graphics.getHeight();
	
	public DialogueBox(HUD hud, String text)
	{
		this.hud = hud;
		this.text = text;
		
		font = hud.screen.game.assets.get(Assets.FONT_DIALOGUE, BitmapFont.class);
		
		x = Gdx.graphics.getWidth() / 2f - DIALOGUE_WIDTH / 2f;
		y = 0;
	}
	
	/**
	 * Same as other constructor, but draws dialogue box at the specified top/bottom location. 
	 * @param hud
	 * @param text
	 * @param at_top
	 */
	public DialogueBox(HUD hud, String text, boolean at_top)
	{
		this(hud, text);
		
		//Determine Y location.
		if(at_top)
		{//Set to top.
			y = Gdx.graphics.getHeight() - DIALOGUE_HEIGHT;
		}
		else
		{//Y has already been set to the bottom. Do nothing here.
		}
	}
	
	public void update(float delta)
	{//Animation.
	}
	
	public void draw()
	{//In order: Background, text, border.
		font.drawMultiLine(hud.screen.batch, text, x, y);
	}
}
