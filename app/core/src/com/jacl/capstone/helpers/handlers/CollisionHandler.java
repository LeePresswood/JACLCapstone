package com.jacl.capstone.helpers.handlers;

import java.util.HashMap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * Handles collision of entities.
 * @author Lee
 */
public class CollisionHandler
{
	public World world;
	
	private MapLayer collision_layer;
	private final String COLLISION_LAYER = "collisionobjects";
	private Cell[][] cells;
	
	public Array<Ellipse> ellipses;
	public Array<Rectangle> rectangles;
	public Array<Polygon> polygons;
		
	public CollisionHandler(World world)
	{
		this.world = world;
	}
	
	public void handlerInit()
	{
		collision_layer = world.map_handler.map.getLayers().get(COLLISION_LAYER);
		MapObjects objects = collision_layer.getObjects();
		
		//Collision object shapes.
		Array<Ellipse> ellipses = new Array<Ellipse>();
		Array<Rectangle> rectangles = new Array<Rectangle>();
		Array<Polygon> polygons = new Array<Polygon>();
		
		float tile_width = world.map_handler.tile_size;
		float tile_height = world.map_handler.tile_size;
		for(int i = 0; i < objects.getCount(); i++)
		{
			PolygonMapObject obj = (PolygonMapObject) objects.get(i);
			Polygon p = obj.getPolygon();
			polygons.add(p);
		}
	}
	
	/**
	 * Do the two entities collide? If so, set the knockback variables.
	 * @param a First entity to check.
	 * @param b Second entity to compare to.
	 */
	public void collidesWith(MovingEntity a, MovingEntity b)
	{
		if(Intersector.intersectRectangles(a.sprite.getBoundingRectangle(), b.sprite.getBoundingRectangle(), intersection))
		{//There was an intersection. Determine the colliding edges.
			Rectangle r1 = a.sprite.getBoundingRectangle();
			if(intersection.x > r1.x && intersection.width < intersection.height)                                  
			{
				a.knockback.knockback_direction = Direction.RIGHT;
				b.knockback.knockback_direction = Direction.LEFT;
			}
			if(intersection.y > r1.y && intersection.width >= intersection.height)
			{
				a.knockback.knockback_direction = Direction.UP;
				b.knockback.knockback_direction = Direction.DOWN;
			}
			if(intersection.x + intersection.width < r1.x + r1.width && intersection.width < intersection.height)  
			{
				a.knockback.knockback_direction = Direction.LEFT;
				b.knockback.knockback_direction = Direction.RIGHT;
			}
			if(intersection.y + intersection.height < r1.y + r1.height && intersection.width >= intersection.height)
			{
				a.knockback.knockback_direction = Direction.DOWN;
				b.knockback.knockback_direction = Direction.UP;
			}
		}
	}
}
