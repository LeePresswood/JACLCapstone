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
	private float jump_x, jump_y;
	
	private final float FOURTH_ROOT_FOUR = 1.189207115f;

	public Player(World world)
	{
		super(world);
		
		speed = 5f * world.camera.TILE_SIZE;
		
		final float jump_percent = 0.35f;
		jump_x = jump_percent * sprite.getWidth();
		jump_y = jump_percent * sprite.getHeight();
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
		
		//Check collision if player moved. This include player-controlled movement, knockback,
		//and other random movement forms.
		if(up || down || left || right)
			collision();		
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	/**
	 * Read the signals. Translate appropriately.<br><br>
	 * 
	 * Keep in mind that simply translating in whatever direction the player is pressing
	 * works for the 4 main directions, but if this method is used for the 4 corners,
	 * the player will be moving at (player_speed) * (root(2)). To correct this, we 
	 * will translate the player in both directions by the sprite's speed divided by 
	 * the 2^(1/4), or (root(root(2))). Doing the theorem will get a final speed magnitude 
	 * of x * root(2) / root(2), or x.<br><br>
	 * 
	 * Rather than calculating the fourth root of two every time, let's just store it here as 
	 * an approximation. Move the sprite's speed down by the fourth root of two, do the 
	 * translation, and correct it.
	 */
	private void move(float delta)
	{
		//Store the current location for collision detection in the future.
		store_x = sprite.getX();
		store_y = sprite.getY();
		
		//Correct if diagonal.
		if(up && left || up && right || down && left || down && right)
			speed /= FOURTH_ROOT_FOUR;

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
			speed *= FOURTH_ROOT_FOUR;
	}
	
	/**
	 * Do the sprite collision. Test the midpoint of the side(s) that face the direction you are moving.
	 */
	private void collision()
	{
		//Go +-x% of the sprite's width/height away from the centerpoint of the side to get better collisions.
		if(left && world.collision.getCollisionCell(this.getLeft(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(left && world.collision.getCollisionCell(this.getLeft(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(right && world.collision.getCollisionCell(this.getRight(), this.getCenterY() + jump_y) != null)
			sprite.setX(store_x);
		if(right && world.collision.getCollisionCell(this.getRight(), this.getCenterY() - jump_y) != null)
			sprite.setX(store_x);
		if(up && world.collision.getCollisionCell(this.getCenterX() + jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(up && world.collision.getCollisionCell(this.getCenterX() - jump_x, this.getTop()) != null)
			sprite.setY(store_y);
		if(down && world.collision.getCollisionCell(this.getCenterX() + jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
		if(down && world.collision.getCollisionCell(this.getCenterX() - jump_x, this.getBottom()) != null)
			sprite.setY(store_y);
	}
	
	/**
	 * Player requested to attack. If an item is selected, do its use motion and effect.
	 */
	public void attack()
	{
		
	}
}
