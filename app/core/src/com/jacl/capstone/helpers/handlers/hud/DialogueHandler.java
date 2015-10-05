package com.jacl.capstone.helpers.handlers.hud;

import com.badlogic.gdx.Gdx;
import com.jacl.capstone.hud.HUD;
import com.jacl.capstone.hud.world.DialogueBox;
import com.jacl.capstone.input.InputGame;

public class DialogueHandler
{
	public HUD hud;
	
	public boolean showing_dialogue;
	public DialogueBox[] boxes;
	public int current_box;
	
	/**
	 * This is a special character that will be inserted into dialogue scripts.
	 * Upon getting the script of the dialogue, we will split it into multiple
	 * dialogue boxes using this character.
	 */
	private final String SPLIT_CHAR = "<s>";
	
	public DialogueHandler(HUD hud)
	{
		this.hud = hud;
	}
	
	/**
	 * Create a textbox from the given text.
	 * @param file_text
	 */
	public void startDialogue(String file_text)
	{
		//Initialize world for dialogue box.
		InputGame.class.cast(Gdx.input.getInputProcessor()).clearInput();
		showing_dialogue = true;
		
		//Split the text into DialogueBoxes.
		if(file_text.length() > 0)
		{
			//Create an array of strings that represents the texts of the dialogue boxes.
			String[] split = file_text.split(SPLIT_CHAR);
			
			//Each of these strings needs to be made into a dialogue box.
			boxes = new DialogueBox[split.length];
			for(int i = 0; i < split.length; i++)
			{
				boxes[i] = new DialogueBox(hud, split[i]);
			}
		}
	}
	
	/**
	 * Move to the previous line(s) in the dialogue box.
	 */
	public void reverseDialogue()
	{
		current_box--;
		if(current_box < 0)
		{//Beginning of dialogue.
			current_box = 0;
		}
	}
	
	/**
	 * Move to the next line(s) in the dialogue box.
	 */
	public void forwardDialogue()
	{
		current_box++;
		if(current_box >= boxes.length)
		{//End of dialogue.
			showing_dialogue = false;
		}
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
		if(showing_dialogue && current_box >= 0 && current_box < boxes.length)
		{
			//screen.batch

			boxes[current_box].draw();
		}
	}
}
