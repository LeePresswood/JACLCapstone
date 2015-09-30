package com.jacl.capstone.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.entities.MovingEntity;

public class TextureHelper
{
	public MovingEntity entity;
	
	private boolean moving;
	private final int MOVE_FRAMES = 2;
	private float move_check = 0.1f;
	private float frame_change = 0.2f;
	private float frame_change_current;
	private int frame_current = 1;
	private Direction direction;
	private Texture[] up_frames;
	private Texture[] down_frames;
	private Texture[] left_frames;
	private Texture[] right_frames;
	
	public TextureHelper(MovingEntity entity)
	{
		this.entity = entity;
	}
	
	/**
	 * Go into the given folder and turn the images into sprites.
	 * @param folder Folder to search.
	 */
	public void setMovementSprites(String folder)
	{
		//Create arrays for the textures.
		up_frames = new Texture[MOVE_FRAMES];
		down_frames = new Texture[MOVE_FRAMES];
		left_frames = new Texture[MOVE_FRAMES];
		right_frames = new Texture[MOVE_FRAMES];
		
		String base_texture_folder = Assets.TEXTURE_BASE + folder;
		
		for(String file : Gdx.files.internal(base_texture_folder).readString().split("\n"))
		{
			//Get the file that we're looking at.
			String file_name = base_texture_folder + file;
			String[] parts = file_name.split("_");
			
			//parts[1] is what we're really interested in. It will tell us the direction and the frame.
			if(parts[1].startsWith("bk"))
			{//Back
				up_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("fr"))
			{//Front
				down_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("lf"))
			{//Left
				left_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("rt"))
			{//Right
				right_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
		}
		
		//On top of loading the images, we're also interested in setting the sprite's texture after loading. Let's just use up_frames[1].
		direction = Direction.DOWN;
		entity.sprite.setRegion(down_frames[1]);
	}
	
	/**
	 * Set the correct sprite. The sprite direction we use will be based upon the amount we moved.
	 * @param delta
	 */
	public void update(float delta)
	{
		if(Math.abs(sprite.getX() - last_location.x) + Math.abs(sprite.getY() - last_location.y) > move_check)
		{
			if(Math.abs(sprite.getX() - last_location.x) - Math.abs(sprite.getY() - last_location.y) > move_check)
			{
				if(Math.signum(sprite.getX() - last_location.x) == -1)
				{//Left
					if(direction == Direction.LEFT)
					{
						//Update timing.
						frame_change_current += delta;
						if(frame_change_current >= frame_change)
						{
							frame_change_current -= frame_change;
							frame_current = (frame_current + 1) % 2;
						}
					}
					else
					{//Reset timing 
						direction = Direction.LEFT;
						frame_change_current = 0f;
						frame_current = 1;
					}
				}
				else
				{//Right
					if(direction == Direction.RIGHT)
					{
						//Update timing.
						frame_change_current += delta;
						if(frame_change_current >= frame_change)
						{
							frame_change_current -= frame_change;
							frame_current = (frame_current + 1) % 2;
						}
					}
					else
					{//Reset timing 
						direction = Direction.RIGHT;
						frame_change_current = 0f;
						frame_current = 1;
					}
				}
			}
			else
			{
				if(Math.signum(sprite.getY() - last_location.y) == -1)
				{//Down
					if(direction == Direction.DOWN)
					{
						//Update timing.
						frame_change_current += delta;
						if(frame_change_current >= frame_change)
						{
							frame_change_current -= frame_change;
							frame_current = (frame_current + 1) % 2;
						}
					}
					else
					{//Reset timing 
						direction = Direction.DOWN;
						frame_change_current = 0f;
						frame_current = 1;
					}
				}
				else
				{//Up
					if(direction == Direction.UP)
					{
						//Update timing.
						frame_change_current += delta;
						if(frame_change_current >= frame_change)
						{
							frame_change_current -= frame_change;
							frame_current = (frame_current + 1) % 2;
						}
					}
					else
					{//Reset timing 
						direction = Direction.UP;
						frame_change_current = 0f;
						frame_current = 1;
					}
				}
			}
		}
		else
		{//Movement isn't happening.
			frame_change_current = 0f;
			frame_current = 1;
		}
		
		//Sprite is set based upon direction.
		switch(direction)
		{
			case DOWN:
				sprite.setRegion(down_frames[frame_current]);
				break;
			case LEFT:
				sprite.setRegion(left_frames[frame_current]);
				break;
			case RIGHT:
				sprite.setRegion(right_frames[frame_current]);
				break;
			case UP:
				sprite.setRegion(up_frames[frame_current]);
				break;
		}
	}
}
