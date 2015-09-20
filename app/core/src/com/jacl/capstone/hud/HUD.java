package com.jacl.capstone.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.jacl.capstone.screens.ScreenGame;

/**
 * Updates and renders the HUD of the world.
 * @author Lee
 *
 */
public class HUD
{
	public ScreenGame screen;
	public OrthographicCamera camera;
	
	public BitmapFont font;
	private NinePatch HealthBarBackground;
	private NinePatch HealthBar;
	
	float totalBarWidth=150;
	float width=(90/100)*totalBarWidth;
	float x=9f;
	float y=9f;
	
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
		
		//create healthbar object
		HealthBarBackground = new NinePatch(new Texture(Gdx.files.internal("health-red.png")),5,5,2,2);
		HealthBar = new NinePatch(new Texture(Gdx.files.internal("health-blue.png")),5,5,2,2);
		
		
	}
	
	public void update(float delta)
	{
		
	}
	
	public void draw()
	{
		
		//Set the projection matrix of the sprite to our new camera. This keeps the two layers from affecting the coordinates of the other.
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			font.draw(screen.batch, screen.world.time.toString(), 0f, Gdx.graphics.getHeight());
			HealthBarBackground.draw(screen.batch, 10, 10, totalBarWidth, 8);
			HealthBar.draw(screen.batch, 10, 10, width, 8);
		screen.batch.end();
	}
}
