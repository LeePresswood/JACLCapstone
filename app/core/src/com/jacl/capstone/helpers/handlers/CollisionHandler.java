package com.jacl.capstone.helpers.handlers;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.Player;

/**
 * Handles collision of entities.
 * @author Lee
 */
public class CollisionHandler
{
	public World world;
	
	//Collision objects will be contained on a collision layer.
	public final String COLLISION_LAYER = "collisionobjects";
	public Array<RectangleMapObject> collision_objects;
	
	//Intersection will require further analysis.
	public Rectangle intersector;
	public Vector2 center_holder1, center_holder2;
	public float COMPARE_DISTANCE;								//The distance of the two sprites should be less than this if we're going to compare them. Square root of this is the real distance, but square root is slow. We'll do it this way instead.
	public float COMPARE_BLOCKS = 7.5f;							//How many blocks apart the blocks will be when calculating the above variable.
	
	public CollisionHandler(World world)
	{
		this.world = world;
		intersector = new Rectangle();
		center_holder1 = new Vector2();
		center_holder2 = new Vector2();
	}
	
	public void handlerInit()
	{
		//Get collision object shapes.
		MapObjects objects = world.map_handler.map.getLayers().get(COLLISION_LAYER).getObjects();
		collision_objects = new Array<RectangleMapObject>();
		for(int i = 0; i < objects.getCount(); i++)
		{//Get the map object and put it into the arrays.
			collision_objects.add((RectangleMapObject) objects.get(i));
		}
		
		COMPARE_DISTANCE = (float) Math.pow(world.map_handler.tile_size, 2.0f) * COMPARE_BLOCKS;
	}
	
	/**
	 * Do the sprite collision detection with solid blocks.
	 */
	public void cellCollision(MovingEntity entity, Vector2 last_location)
	{
		entity.sprite.getBoundingRectangle().getCenter(center_holder2);
		
		for(RectangleMapObject obj : world.collision_handler.collision_objects)
		{//We want to speed up this search by only looking at objects within a small distance of the sprite.
			obj.getRectangle().getCenter(center_holder1);
			if(center_holder1.dst2(center_holder2) < COMPARE_DISTANCE && entity.sprite.getBoundingRectangle().overlaps(obj.getRectangle()))
			{//There was a collision. Stop further checking and return to last location.
				//Because we made it here, we've overlapped. We want to get the intersection of the overlap.
				Intersector.intersectRectangles(entity.sprite.getBoundingRectangle(), obj.getRectangle(), intersector);
				if(intersector.width > intersector.height)
				{//Reset Y.
					//Don't stop trapped players from walking away if they get stuck.
					if(entity instanceof Player)
					{
						if(intersector.y > obj.getRectangle().getY() + obj.getRectangle().getHeight() / 2f && Player.class.cast(entity).up != true)
						{
							entity.sprite.setY(last_location.y);
						}
						else if(intersector.y < obj.getRectangle().getY() + obj.getRectangle().getHeight() / 2f && Player.class.cast(entity).down != true)
						{
							entity.sprite.setY(last_location.y);
						}
						else
						{
							System.out.println(intersector.y);
						}
					}
					else
					{
						entity.sprite.setY(last_location.y);
					}
				}
				else
				{//Reset X.
					//Don't stop trapped players from walking away if they get stuck.
					if(entity instanceof Player)
					{
						if(intersector.x > obj.getRectangle().getX() + obj.getRectangle().getWidth() / 2f && Player.class.cast(entity).right != true)
						{
							entity.sprite.setX(last_location.x);
						}
						else if(intersector.x < obj.getRectangle().getX() + obj.getRectangle().getWidth() / 2f && Player.class.cast(entity).left != true)
						{
							entity.sprite.setX(last_location.x);
						}
						else
						{
							System.out.println(last_location.x);
						}
					}
					else
					{
						entity.sprite.setX(last_location.x);
					}
				}
				
				return;
			}
		}
	}
	
	/**
	 * Do the two entities collide? If so, set the knockback variables.
	 * @param a First entity to check.
	 * @param b Second entity to compare to.
	 */
	public void collidesWith(MovingEntity a, MovingEntity b)
	{
		if(Intersector.intersectRectangles(a.sprite.getBoundingRectangle(), b.sprite.getBoundingRectangle(), intersector))
		{//There was an intersection. Determine the colliding edges.
			Rectangle r1 = a.sprite.getBoundingRectangle();
			if(intersector.x > r1.x && intersector.width < intersector.height)
			{
				a.knockback.knockback_direction = Direction.RIGHT;
				b.knockback.knockback_direction = Direction.LEFT;
			}
			if(intersector.y > r1.y && intersector.width >= intersector.height)
			{
				a.knockback.knockback_direction = Direction.UP;
				b.knockback.knockback_direction = Direction.DOWN;
			}
			if(intersector.x + intersector.width < r1.x + r1.width && intersector.width < intersector.height)  
			{
				a.knockback.knockback_direction = Direction.LEFT;
				b.knockback.knockback_direction = Direction.RIGHT;
			}
			if(intersector.y + intersector.height < r1.y + r1.height && intersector.width >= intersector.height)
			{
				a.knockback.knockback_direction = Direction.DOWN;
				b.knockback.knockback_direction = Direction.UP;
			}
		}
	}
}
