package com.jacl.capstone.world.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;

public class Player extends Entity
{
	public float speed;
	public boolean up, down, left, right;
	private float store_x, store_y;

	public Player(World world)
	{
		super(world);
		
		speed = 5f * world.camera.TILE_SIZE;
	}

	@Override
	protected Sprite makeSprite()
	{
		Sprite s = new Sprite(new Texture(Gdx.files.internal("image.png")));
		s.setBounds(0, 0, 1f * world.camera.TILE_SIZE, 1f * world.camera.TILE_SIZE);
		return s;
	}

	@Override
	public void update(float delta)
	{
		//Move.
		move(delta);
		
		//Check collision.
		collision();		
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
	private void move(float delta)
	{
		//Store the current location for collision detection in the future.
		store_x = sprite.getX();
		store_y = sprite.getY();
		
		//Correct if diagonal.
		if(up && left || up && right || down && left || down && right)
			speed /= 1.189207115f;

		//Do the translation.
		if(up)
			sprite.translateY(speed * delta);
		else if(down)
			sprite.translateY(-speed * delta);
		if(left)
			sprite.translateX(-speed * delta);
		else if(right)
			sprite.translateX(speed * delta);
		
		//Undo correction if diagonal.
		if(up && left || up && right || down && left || down && right)
			speed *= 1.189207115f;
	}
	
	/**
	 * Do the sprite collision. Test the four corners and the four midpoints. All must pass.
	 */
	private void collision()
	{
		//Left.
		if(world.collision.getCollisionCell(this.getLeft(), this.getTop()) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getLeft(), this.getCenterY()) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getLeft(), this.getBottom()) != null)
			sprite.setX(store_x);
		
		//Right.
		if(world.collision.getCollisionCell(this.getRight(), this.getTop()) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getRight(), this.getCenterY()) != null)
			sprite.setX(store_x);
		if(world.collision.getCollisionCell(this.getRight(), this.getBottom()) != null)
			sprite.setX(store_x);
		
		//Top.
		if(world.collision.getCollisionCell(this.getLeft(), this.getTop()) != null)
			sprite.setY(store_y);
		if(world.collision.getCollisionCell(this.getCenterX(), this.getTop()) != null)
			sprite.setY(store_y);
		if(world.collision.getCollisionCell(this.getRight(), this.getTop()) != null)
			sprite.setY(store_y);
		
		//Bottom.
		if(world.collision.getCollisionCell(this.getLeft(), this.getBottom()) != null)
			sprite.setY(store_y);
		if(world.collision.getCollisionCell(this.getCenterX(), this.getBottom()) != null)
			sprite.setY(store_y);
		if(world.collision.getCollisionCell(this.getRight(), this.getBottom()) != null)
			sprite.setY(store_y);
	}
}
