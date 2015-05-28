package com.jacl.capstone.helpers.handlers;

import java.io.IOException;
import java.io.StringWriter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlWriter;
import com.jacl.capstone.world.World;

/**
 * Once the game is opened, read from the save state. Once it is
 * closed, write to the save state. Manage this balance here.
 * 
 * @author Lee
 *
 */
public class SaveHandler
{
	private final String SAVE_DIR = "saves/";
	public World world;
	
	public SaveHandler(World world)
	{
		this.world = world;
		
		//Make the saves/ directory if it does not exist.
		if(!Gdx.files.local(SAVE_DIR).exists() || !Gdx.files.local(SAVE_DIR).isDirectory())
			init();
	}
	
	/**
	 * This is called the first time the game is run. Initializes the game to the initial state.
	 */
	private void init()
	{
		//Make the save directory.
		Gdx.files.local(SAVE_DIR).mkdirs();
		
		//Write a file with the initial information.
		StringWriter writer = new StringWriter();
		XmlWriter xml = new XmlWriter(writer);
		try{
			xml.element("player")
				.element("save")
					.element("time")
						.text("00:00")
					.pop()
					.element("player_location")
						.element("x")
							.text("0")
						.pop()
						.element("y")
							.text("0")
						.pop()
					.pop()
					.element("map")
						.text("test.tmx")
					.pop()
					.element("progress_flag")
						.text("0")
					.pop()
				.pop()
				.element("texture")
					.text("image.png")
				.pop()
				.element("width")
					.text("1.0")
				.pop()
				.element("height")
					.text("1.0")
				.pop()
				.element("health")
					.text("100.0")
				.pop()
				.element("knockback_on_collide")
					.text(new Boolean(false))
				.pop()
				.element("damage_on_collide")
					.text("0.0")
				.pop()
				.element("move_speed")
					.text("5.0")
				.pop()
			.pop();
			
			xml.close();
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//Save our new file.
		Gdx.files.local(SAVE_DIR + "test.xml").writeString(writer.toString(), false);
	}
	
	/**
	 * Save to save file. This happens upon the world closing.
	 */
	public void write()
	{
		//Create a new file for save.
		/*FileHandle file = Gdx.files.local(SAVE_DIR + "test_save.txt");
		file.writeString("time " + world.time.toString() + "\n", false);
		file.writeString("player_location " + world.entity_handler.player.getTileX() + " " + world.entity_handler.player.getTileY() + "\n", true);
		file.writeString("map " + world.map_handler.map_name + "\n", true);*/
	}
}
