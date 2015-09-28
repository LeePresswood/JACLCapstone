package com.jacl.capstone.helpers.handlers.world;

import java.io.IOException;
import java.io.StringWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.hud.HUD;
import com.jacl.capstone.screens.ScreenGame;
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
	public World world;
	public HUD hud;
	
	private final String SAVE_DIR = "saves/";
	private final String SAVE_FILE = "test.xml";
	
	private final String INIT_HEALTH_MAX = "10";
	private final String INIT_HEALTH_CURRENT = INIT_HEALTH_MAX;
	private final String INIT_HEALTH_REGEN = "0";
	private final String INIT_TIME = "00:00";
	private final String INIT_X = "0";
	private final String INIT_Y = "0";
	private final String INIT_MAP = "object_collision_test.tmx";
	private final String INIT_PROGRESS_FLAG = "0";
	
	public SaveHandler(ScreenGame screen_game)
	{
		this.world = screen_game.world;
		this.hud = screen_game.hud;
		
		//Make the saves/ directory if it does not exist.
		if(!Gdx.files.local(SAVE_DIR).exists() || !Gdx.files.local(SAVE_DIR).isDirectory() || !Gdx.files.local(SAVE_DIR + SAVE_FILE).exists())
		{
			init();
		}
	}
	
	/**
	 * This is called the first time the game is run. Initializes the game to the initial state.
	 */
	private void init()
	{
		//Make the save directory.
		Gdx.files.local(SAVE_DIR).mkdirs();
		
		try{
			//Write a file with the initial information.
			StringWriter writer = new StringWriter();
			XmlWriter xml = new XmlWriter(writer);
			
			xml.element("player")
				.element("healthbar")
					.element("max")
						.text(INIT_HEALTH_MAX)
					.pop()
					.element("current")
						.text(INIT_HEALTH_CURRENT)
					.pop()
					.element("regen")
						.text(INIT_HEALTH_REGEN)
					.pop()
				.pop()
				.element("time")
					.text(INIT_TIME)
				.pop()
				.element("player_location")
					.element("x")
						.text(INIT_X)
					.pop()
					.element("y")
						.text(INIT_Y)
					.pop()
				.pop()
				.element("map")
					.text(INIT_MAP)
				.pop()
				.element("progress_flag")
					.text(INIT_PROGRESS_FLAG)
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
			
			//Save our new file and end.
			Gdx.files.local(SAVE_DIR + SAVE_FILE).writeString(writer.toString(), false);
			xml.close();
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Read from the save file into the world.
	 */
	public void getFromSave()
	{
		try
		{
			//Read from save file.
			Element root = new XmlReader().parse(Gdx.files.local(SAVE_DIR + SAVE_FILE));
			
			//Push into world.
			float healthbar_max = root.getChildByName("healthbar").getFloat("max");
			float healthbar_current = root.getChildByName("healthbar").getFloat("current");
			float healthbar_regen = root.getChildByName("healthbar").getFloat("regen");
			int x = root.getChildByName("player_location").getInt("x");
			int y = root.getChildByName("player_location").getInt("y");
			String map = root.get("map");
			world.init(map, x, y, healthbar_max, healthbar_current, healthbar_regen);
			
			//Push into HUD.
			String time = root.get("time");
			hud.init(healthbar_max, healthbar_current, time);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Save to save file. This happens upon the world closing.
	 */
	public void write()
	{
		System.out.print("Writing save file...");
		FileHandle file = Gdx.files.local(SAVE_DIR + SAVE_FILE);
		String file_string = file.readString();
		
		//Get save packages.
		float[] healthbar_package = hud.health_bar.packageForSave();
		
		//Build an XML string from the above packages.
		file_string = file_string.replaceFirst("<healthbar>.*</healthbar>", "<max>" + healthbar_package[0] + "</max>"
				+ "<current>" + healthbar_package[1] + "</current>" 
				+ "<regen>" + healthbar_package[2] + "</regen>");
		file_string = file_string.replaceFirst("<time>.*</time>", "<time>" + hud.time.toString() + "</time>");
		file_string = file_string.replaceFirst("<x>.*</x>", "<x>" + world.entity_handler.player.getTileX() + "</x>");
		file_string = file_string.replaceFirst("<y>.*</y>", "<y>" + world.entity_handler.player.getTileY() + "</y>");
		file_string = file_string.replaceFirst("<map>.*</map>", "<map>" + world.map_handler.map_name + "</map>");
		
		//Write to the XML file for saving.
		file.writeString(file_string, false);
		System.out.print("Done!");
	}
}
