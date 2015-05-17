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
public class StateSaver
{
	public World world;
	
	public StateSaver(World world)
	{
		this.world = world;
	}
	
	public void read()
	{
		//Read from save file.
		FileHandle file = Gdx.files.local("test_save.txt");
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
	
	public void write()
	{
		//Create a new file for save.
		FileHandle file = Gdx.files.local("test_save.txt");
		file.writeString("time " + world.time.toString() + "\n", false);
		file.writeString("player_location " + world.player.getLeft() + " " + world.player.getBottom() + "\n", true);
		file.writeString("map test.tmx", true);
	}
}
