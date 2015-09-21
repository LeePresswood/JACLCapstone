package com.jacl.capstone.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jacl.capstone.helpers.handlers.hud.DialogueHandler;
import com.jacl.capstone.hud.world.HealthBar;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.atmosphere.GameTime;
import com.jacl.capstone.world.atmosphere.TimeColorer;

/**
 * Updates and renders the HUD of the world.
 * @author Lee
 *
 */
public class HUD
{
	public ScreenGame screen;
	
	public DialogueHandler dialogue_handler;
	public OrthographicCamera camera;
	public BitmapFont font;
	
	public HealthBar health_bar;
	public GameTime time;
	
	public HUD(ScreenGame screen)
	{
		this.screen = screen;
		
		//To make the HUD easier to use, we should use a second camera.
		//This new camera will change how we draw items in the sprite batch.
		//Set the dimensions of the camera equal to the size of the screen and center the camera on the middle.
		//Draw items as a percentage of the screen.
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
		camera.update();
		
		font = screen.game.assets.get("fonts/font.fnt", BitmapFont.class);
		font.setScale(0.85f);
		
		dialogue_handler = new DialogueHandler(this);
		
		health_bar = new HealthBar(this);
	}
	
	/**
	 * Call this to initialize the HUD. This is called after loading from a save.
	 */
	public void init(float max, float current, float regen, String time_set)
	{
		health_bar.init(max, current, regen);
		time = new GameTime(time_set);
	}
	
	public void update(float delta)
	{
		//Update time.
		time.update(delta);
		
		//If the hour changed, update color.
		if(time.recently_updated_minute)
		{
			screen.world.time_color = TimeColorer.getColor(time);
		}
		
		health_bar.update(delta);
		dialogue_handler.update(delta);
	}
	
	public void draw()
	{
		//Set the projection matrix of the sprite to our new camera. This keeps the two layers from affecting the coordinates of the other.
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			font.draw(screen.batch, time.toString(), 0f, Gdx.graphics.getHeight());
			health_bar.draw();

			dialogue_handler.draw();
		screen.batch.end();
	}
}
