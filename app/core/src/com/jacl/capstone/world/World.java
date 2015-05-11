package com.jacl.capstone.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jacl.capstone.screens.ScreenGame;

/**
 * Handles the updating and rendering of game objects. Create managers to keep this class general.
 * @author Lee
 *
 */
public class World
{
	public ScreenGame screen; 
	
	public SectorCamera camera;
	
	public TiledMap map;
	public TiledMapRenderer tiledMapRenderer;
	
	public Sprite sprite;
	public float sprite_speed;
	public boolean up, down, left, right;
	
	public World(ScreenGame screen)
	{
		this.screen = screen;
		
		//Camera and tile map.
		map = new TmxMapLoader().load("maps/test.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		camera = new SectorCamera(map);
		
		//Sprite.
		sprite = new Sprite(new Texture(Gdx.files.internal("image.png")));
		sprite_speed = 25f * camera.TILE_SIZE;
	}
	
	public void update(float delta)
	{
		/*
		 * Read the signals. Translate appropriately.
		 * Keep in mind that simply translating in whatever direction the player is pressing
		 * works for the 4 main directions, but if this method is used for the 4 corners,
		 * the player will be moving at (player_speed) * (root(2)). Do the Pythagorean Theorem
		 * if you don't believe me.
		 * 
		 * To correct this, we will translate the player in both directions by the sprite's 
		 * speed divided by the 2^(1/4), or (root(root(2))). Doing the theorem will get a
		 * final speed magnitude of x * root(2) / root(2), or x.
		 * 
		 * Rather than calculating the fourth root of two every time, let's just store it here as an approximation.
		 * Move the sprite's speed down by the fourth root of two, do the translation, and correct it.
		 */
		if(up && left || up && right || down && left || down && right)
			sprite_speed /= 1.189207115f;

		//Do the translation.
		if(up)
			sprite.translateY(sprite_speed * delta);
		else if(down)
			sprite.translateY(-sprite_speed * delta);
		if(left)
			sprite.translateX(-sprite_speed * delta);
		else if(right)
			sprite.translateX(sprite_speed * delta);
		
		//Recorrect if diagonal.
		if(up && left || up && right || down && left || down && right)
			sprite_speed *= 1.189207115f;
		
		//Update camera
		camera.updateCamera(this);
	}
	
	public void draw()
	{
		camera.update();
      tiledMapRenderer.setView(camera);
      tiledMapRenderer.render();
		
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			sprite.draw(screen.batch);
		screen.batch.end();
	}
}
