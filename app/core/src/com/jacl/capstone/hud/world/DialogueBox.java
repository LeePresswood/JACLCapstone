package com.jacl.capstone.hud.world;

import com.badlogic.gdx.Gdx;

/**
 * This class handles the text within a dialogue box. This text is calculated by being given the full dialogue and
 * the index of the text. Break this text into multiple textboxes. 
 * @author Lee
 */
public class DialogueBox
{
	private final float DIALOGUE_WIDTH = Gdx.graphics.getWidth() > 500f ? 500f : Gdx.graphics.getWidth();
	private final float DIALOGUE_HEIGHT = Gdx.graphics.getHeight() > 500f ? 500f : Gdx.graphics.getHeight();
	
	/**
	 * Use this to split the text into multiple lines.
	 */
	private final String NEW_LINE_SPLIT = "<n>";
	
	public DialogueBox(String text)
	{
	}	
}
