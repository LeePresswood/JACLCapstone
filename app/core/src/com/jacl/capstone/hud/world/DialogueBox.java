package com.jacl.capstone.hud.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	
	//Box location and size.
	private float box_x;
	private float box_y;
	private final float DIALOGUE_WIDTH = Gdx.graphics.getWidth();
	private final float DIALOGUE_HEIGHT = Gdx.graphics.getHeight() > 100f ? 100f : Gdx.graphics.getHeight();
	
	//Text location inside box.
	private final float TEXT_PAD_X = 5f;
	private final float TEXT_PAD_Y = TEXT_PAD_X;
	
	public DialogueBox(HUD hud, String text)
	{
		this.hud = hud;
		this.text = text;
		
		font = hud.screen.game.assets.get(Assets.FONT_DIALOGUE, BitmapFont.class);
		
		box_x = Gdx.graphics.getWidth() / 2f - DIALOGUE_WIDTH / 2f;
		box_y = 0;
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
			box_y = Gdx.graphics.getHeight() - DIALOGUE_HEIGHT;
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
		hud.screen.renderer.setColor(Color.GREEN);
		hud.screen.renderer.begin(ShapeType.Filled);
			hud.screen.renderer.rect(box_x, box_y, DIALOGUE_WIDTH, DIALOGUE_HEIGHT);
		hud.screen.renderer.end();
		
		hud.screen.renderer.setColor(Color.BLACK);
		hud.screen.renderer.begin(ShapeType.Line);
			hud.screen.renderer.rect(box_x, box_y, DIALOGUE_WIDTH, DIALOGUE_HEIGHT);
		hud.screen.renderer.end();
		
		//font.setColor(Color.YELLOW);
		hud.screen.batch.begin();
			font.drawMultiLine(hud.screen.batch, text, box_x + TEXT_PAD_X, box_y + DIALOGUE_HEIGHT - TEXT_PAD_Y);
		hud.screen.batch.end();
	}
}
