package com.jacl.capstone.screens;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.hud.HUDManager;
import com.jacl.capstone.input.InputGame;
import com.jacl.capstone.world.World;

public class ScreenGame extends ScreenParent
{
	public World world;
	public HUDManager hud;
	
	public ScreenGame(CapstoneGame game)
	{
		super(game);
		
		world = new World(this);
		hud = new HUDManager(this);
	}

	@Override
	public Color setUpBackgroundColor()
	{
		return new Color(0f, 0f, 0f, 0f);
	}

	@Override
	public InputProcessor setUpInput()
	{
		return new InputGame(this);
	}

	@Override
	public void update(float delta)
	{
		//Update world first and HUD second to stay parallel with the draw method.
		world.update(delta);
		hud.update(delta);
	}

	@Override
	public void draw()
	{
		/* We want to draw the world first and the HUD second. We do it this
		 * way to assure that the HUD is drawn over the world (bottom of the
		 * draw buffer for that pixel is the first thing that was drawn in
		 * this method).
		 */
		world.draw();
		hud.draw();
	}
	
	@Override
	/**
	 * This is called when the screen is first made.
	 * Gather the player's location and time from the save file.
	 */	
	public void show()
	{
		//Read from save file.
		FileHandle file = Gdx.files.local("test_save.txt");
		Scanner scanner = new Scanner(file.read());
		
		//Read the time.
		String time_line = scanner.nextLine().split(" ")[1];
		
		//Read player's location.
		String location_line[] = scanner.nextLine().split(" ");
		float x = Float.parseFloat(location_line[1]);
		float y = Float.parseFloat(location_line[2]);
		
		//Push these into the game's world.
		
		//Done reading.
		scanner.close();
	}
	
	@Override
	/**
	 * This is called when this screen is closed.
	 * Save the player's location and time to the save file.
	 */
	public void hide()
	{System.out.println(1);
		//Create a new file for save.
		FileHandle file = Gdx.files.local("test_save.txt");
		file.writeString("time " + world.time.toString() + "\n", false);
		file.writeString("player_location " + world.player.getLeft() + " " + world.player.getBottom() + "\n", true);
	}
}
