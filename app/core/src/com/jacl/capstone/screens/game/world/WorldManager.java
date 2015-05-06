package com.jacl.capstone.screens.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jacl.capstone.screens.game.ScreenGame;
import com.jacl.capstone.screens.game.world.sector.SectorCamera;

/**
 * Handles the updating and rendering of game objects. Create managers to keep this class general.
 * @author Lee
 *
 */
public class WorldManager
{
	public ScreenGame screen; 
	
	public SectorCamera camera;
	
	public TiledMap map;
	public TiledMapRenderer tiledMapRenderer;
	
	public Sprite sprite;
	public float sprite_speed;
	public boolean up, down, left, right;
	
	public WorldManager(ScreenGame screen)
	{
		this.screen = screen;
		
		//Camera and tile map.
		camera = new SectorCamera();
		map = new TmxMapLoader().load("maps/test.tmx");
      tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
      
      
      //Sprite.
		sprite = new Sprite(new Texture(Gdx.files.internal("image.png")));
		sprite_speed = 10f * camera.TILE_SIZE;
	}
	
	public void update(float delta)
	{
		//Read the signals. Translate appropriately.
		if(up)
			sprite.translateY(sprite_speed * delta);
		if(down)
			sprite.translateY(-sprite_speed * delta);
		if(left)
			sprite.translateX(-sprite_speed * delta);
		if(right)
			sprite.translateX(sprite_speed * delta);
		
		//Follow character.
		camera.position.set(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f, 0);
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
