package com.jacl.capstone.data;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.atmosphere.GameTime;
import com.jacl.capstone.world.atmosphere.TimeColorer;

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
		FileHandle file = Gdx.files.local(SAVE_DIR + "test_save.txt");
		file.writeString("time 00:00\n", false);
		file.writeString("player_location 0 1\n", true);
		file.writeString("map test.tmx\n", true);
	}
	
	/**
	 * Read from the save file and push to the world. This happens upon the world
	 * loading.
	 */
	public void read()
	{
		//Read from save file.
		FileHandle file = Gdx.files.local(SAVE_DIR + "test_save.txt");
		Scanner scanner = new Scanner(file.read());
		
		//Read the time.
		String time_line = scanner.nextLine().split(" ")[1];
		
		//Read player's location.
		String location_line[] = scanner.nextLine().split(" ");
		int x = Integer.parseInt(location_line[1]);
		int y = Integer.parseInt(location_line[2]);
		
		//Read map.
		String map = scanner.nextLine().split(" ")[1];
		
		//Push these into the game's world.
		//Time.
		world.time = new GameTime(time_line);
		world.time_color = TimeColorer.getColor(world.time);
		
		//Map.
		world.init(map, x, y);
		
		scanner.close();
	}
	
	/**
	 * Save to save file. This happens upon the world closing.
	 */
	public void write()
	{
		//Create a new file for save.
		FileHandle file = Gdx.files.local(SAVE_DIR + "test_save.txt");
		file.writeString("time " + world.time.toString() + "\n", false);
		file.writeString("player_location " + world.player.getTileX() + " " + world.player.getTileY() + "\n", true);
		file.writeString("map " + world.map_manager.map_name + "\n", true);
	}
}
