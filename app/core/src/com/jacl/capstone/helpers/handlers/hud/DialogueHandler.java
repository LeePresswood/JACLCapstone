package com.jacl.capstone.helpers.handlers.hud;

import com.jacl.capstone.hud.HUD;

public class DialogueHandler
{
	public HUD hud;
	
	public boolean showing_dialogue;

	
	
	public DialogueHandler(HUD hud)
	{
		this.hud = hud;
	}
	
	/**
	 * Move to the previous line(s) in the dialogue box.
	 */
	public void reverseDialogue()
	{
		
	}
	
	/**
	 * Move to the next line(s) in the dialogue box.
	 */
	public void forwardDialogue()
	{
		
	}
	
	/**
	 * Handle the updating of dialogue. Should only be used for animations.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		
	}
	
	/**
	 * Draw the dialogue box. To do this, draw in the following order:<br>
	 * Border<br>
	 * Background<br>
	 * Text
	 */
	public void draw()
	{
		//screen.batch
	}
}
