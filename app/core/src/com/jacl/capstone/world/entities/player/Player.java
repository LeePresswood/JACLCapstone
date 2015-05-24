package com.jacl.capstone.world.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * This is the player that is controlled by input.There should 
 * be a direct link between the inputs that are executed and 
 * the actions this class takes.<br><br>
 * 
 * When an attack is requested, spawn the item from a reference
 * point of this player.<br><br>
 * 
 * When the enemy wishes to attack or move toward the player,
 * do so by aiming at the player's center.<br><br>
 * 
 * The player will be the only entity that communicates with 
 * EventEntities. During the block collision step, check for
 * collisions with event blocks.
 * 
 * @author Lee
 *
 */
public class Player extends MovingEntity
{
	//This number will be used in diagonal movement calculations.
	private final float FOURTH_ROOT_FOUR = 1.189207115f;
	
	//Rather than AI, we will use signals to define the correct time to move/attack.
	public boolean up, down, left, right;

	public Player(World world, float x, float y)
	{
		super(world, x, y, false, 5f, 100f, 0f, Alignment.PLAYER);
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	@Override
	protected Sprite makeSprite(float x, float y)
	{
		Sprite s = new Sprite(world.screen.game.assets.get(Assets.PLAYER, Texture.class));
		s.setBounds(x * world.map_handler.tile_size, y * world.map_handler.tile_size, 1f * world.map_handler.tile_size, 1f * world.map_handler.tile_size);
		return s;
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
	@Override
	protected void move(float delta)
	{
		//Correct if diagonal.
		if(up && left || up && right || down && left || down && right)
			move_speed /= FOURTH_ROOT_FOUR;

		//Do the translation.
		if(up)
		{
			sprite.translateY(move_speed * delta);
		}
		else if(down)
		{
			sprite.translateY(-move_speed * delta);
		}
		if(left)
		{
			sprite.translateX(-move_speed * delta);
		}
		else if(right)
		{
			sprite.translateX(move_speed * delta);
		}
		
		//Undo correction if diagonal.
		if(up && left || up && right || down && left || down && right)
			move_speed *= FOURTH_ROOT_FOUR;
	}
	
	@Override
	public void update(float delta)
	{
		super.update(delta);
		
		//Check for entities we may have started.
		world.event_handler.doEventEntity(getCenterX(), getCenterY());
	}
	
	@Override
	/**
	 * See if player requested an attack. If so, get selected item and do its motion and effect.
	 */
	protected void attack(float delta)
	{
		if(attack.attacking || attack.mid_attack)
		{
			//We don't want to stop mid attack. Commit to the attack until the end by setting a mid-attack flag.
			//mid_attack = true;
			
			//Get the selected item if a copy of the item does not exist.
			/*if(item == null)
			 	item = ItemFactory.get(this);
			 */
			
			//Update the item's animation and collision.
			//item.update(delta);
			/*for(Entity e : world.enemies)
			 	if(world.collision.collidesWith(item, e)
			 		e.damage();
			 */
			
			//If the attack is over, set mid_attack to off. That way, the only thing affecting the player's attacking is the keyboard input.
			/*if(item.isDone())
			{
				mid_attack = false;
				item = null;
			}*/
		}
	}
}
