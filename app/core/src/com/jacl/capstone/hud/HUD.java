package com.jacl.capstone.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.jacl.capstone.helpers.handlers.hud.DialogueHandler;
import com.jacl.capstone.hud.world.HealthBar;
import com.jacl.capstone.screens.ScreenGame;

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
	
	public void update(float delta)
	{
		dialogue_handler.update(delta);
	}
	
	public void draw()
	{
		
		//Set the projection matrix of the sprite to our new camera. This keeps the two layers from affecting the coordinates of the other.
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			font.draw(screen.batch, screen.world.time.toString(), 0f, Gdx.graphics.getHeight());
			

			dialogue_handler.draw();
		screen.batch.end();
	}
}
