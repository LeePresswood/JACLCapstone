package com.jacl.capstone.world.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;

public class Player extends Entity
{
	public float sprite_speed;
	public boolean up, down, left, right;

	public Player(World world)
	{
		super(world);
		
		sprite_speed = 25f * world.camera.TILE_SIZE;
	}

	@Override
	protected Sprite makeSprite()
	{
		return new Sprite(new Texture(Gdx.files.internal("image.png")));
	}

	@Override
	public void update(float delta)
	{
		//Move.
		playerMove(delta);
		
		//Check collision.
		
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	/**
	 * Read the signals. Translate appropriately.
	 * Keep in mind that simply translating in whatever direction the player is pressing
	 * works for the 4 main directions, but if this method is used for the 4 corners,
	 * the player will be moving at (player_speed) * (root(2)). Do the Pythagorean Theorem
	 * if you don't believe me.<br><br>
	 * 
	 * To correct this, we will translate the player in both directions by the sprite's 
	 * speed divided by the 2^(1/4), or (root(root(2))). Doing the theorem will get a
	 * final speed magnitude of x * root(2) / root(2), or x.<br><br>
	 * 
	 * Rather than calculating the fourth root of two every time, let's just store it here as an approximation.
	 * Move the sprite's speed down by the fourth root of two, do the translation, and correct it.
	 */
	private void playerMove(float delta)
	{
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
	}
}
