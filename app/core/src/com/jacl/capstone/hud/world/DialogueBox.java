package com.jacl.capstone.hud.world;

import java.util.Collections;

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
	
	private final float DIALOGUE_WIDTH = Gdx.graphics.getWidth() > 500f ? 500f : Gdx.graphics.getWidth();
	private final float DIALOGUE_HEIGHT = Gdx.graphics.getHeight() > 300f ? 500f : Gdx.graphics.getHeight();
	
	public DialogueBox(HUD hud, String text)
	{
		this.hud = hud;
		
		font = hud.screen.game.assets.get(Assets.FONT_DIALOGUE, BitmapFont.class);
		this.text = text;
	}
	
	public void update(float delta)
	{//Animation.
	}
	
	public void draw()
	{//In order: Background, text, border.
		font.drawMultiLine(hud.screen.batch, text, 50, 50);
	}
}
