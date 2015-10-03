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
	private String[] lines;
	
	private final float DIALOGUE_WIDTH = Gdx.graphics.getWidth() > 500f ? 500f : Gdx.graphics.getWidth();
	private final float DIALOGUE_HEIGHT = Gdx.graphics.getHeight() > 300f ? 500f : Gdx.graphics.getHeight();
	
	/**
	 * Use this to split the text into multiple lines.
	 */
	private final String NEW_LINE_SPLIT = "<n>";
	
	public DialogueBox(HUD hud, String text)
	{
		this.hud = hud;
		
		font = hud.screen.game.assets.get(Assets.FONT16, BitmapFont.class);
		lines = text.split(NEW_LINE_SPLIT);
	}
	
	public void update(float delta)
	{//Animation.
	}
	
	public void draw()
	{//In order: Background, text, border.
		font.draw(hud.screen.batch, "Hello. This is a long string. TESTTIEUEUEUEJFKJDKJJKJKAKFJAKJFJKSAFKJSFJK", 50, 50);
	}
}
