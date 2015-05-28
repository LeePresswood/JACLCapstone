package com.jacl.capstone.world.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;

/**
 * These are the objects within the game. This will be everything that is not a tile, such as:<br>
 * - The Player<br>
 * - Enemies<br>
 * - Projectiles<br>
 * - Puzzle Objects<br><br>
 * 
 * It will also include invisible items that could cause an effect, such as a doorway item that 
 * moves us from one screen to another or an event item that causes a cutscene.
 * 
 * @author Lee
 *
 */
public abstract class Entity
{
	public World world;
	public Alignment alignment;
	
	public Sprite sprite;
		
	public Entity(World world, float x, float y, Element data, Alignment alignment)
	{
		this.world = world;
		this.alignment = alignment;
		
		//Events will not pass a data element up the tree. Other entities will. Watch for this.
		if(data != null)
			makeSprite(x, y, data.getFloat("width"), data.getFloat("height"), world.screen.game.assets.get(data.get("texture"), Texture.class));
		else
			makeSprite(x, y, 1f, 1f);
	}
	
	public void makeSprite(float x, float y, float width, float height, Texture... texture)
	{
		if(texture.length != 0)
			sprite = new Sprite(world.screen.game.assets.get(Assets.PLAYER, Texture.class));
		else
			sprite = new Sprite();
		sprite.setBounds(x * world.map_handler.tile_size, y * world.map_handler.tile_size, width * world.map_handler.tile_size, height * world.map_handler.tile_size);
	}
	
	//These methods will be very helpful in checking bounds of/moving toward an entity.
	public float getCenterX()
	{
		return sprite.getX() + sprite.getWidth() / 2f;
	}
	
	public float getCenterY()
	{
		return sprite.getY() + sprite.getHeight() / 2f;
	}
	
	public void setCenterX(float x)
	{
		sprite.setX(x - sprite.getWidth() / 2f);
	}
	
	public void setCenterY(float y)
	{
		sprite.setY(y - sprite.getHeight() / 2f);
	}
	
	public float getLeft()
	{
		return sprite.getX();
	}
	
	public float getRight()
	{
		return sprite.getX() + sprite.getWidth();
	}
	
	public float getBottom()
	{
		return sprite.getY();
	}
	
	public float getTop()
	{
		return sprite.getY() + sprite.getHeight();
	}
	
	public int getTileX()
	{
		return (int) (getCenterX() / world.map_handler.tile_size);
	}
	
	public int getTileY()
	{
		return (int) (getCenterY() / world.map_handler.tile_size);
	}
	
	public abstract void update(float delta);
	public abstract void draw(SpriteBatch batch);
}
