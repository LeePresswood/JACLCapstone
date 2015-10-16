package com.jacl.capstone.world.entities.player.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;

/**
 * These are the weapons the player can utilize.
 * 
 * @author Lee
 *
 */
public abstract class Weapon extends Entity
{
	//Qualities that will be manipulated throughout play.
	public boolean knockback_on_collide;
	public float damage_on_collide;
	public float use_time;
	
	//When this flag is set, the item is finished being used.
	public boolean finished;

	public Weapon(World world, float x, float y, Element data)
	{
		super(world, x, y, data, Alignment.PLAYER);
		
		this.knockback_on_collide = data.getBoolean("knockback_on_collide");
		this.damage_on_collide = data.getFloat("damage_on_collide");
		this.use_time = data.getFloat("use_time");
		this.sprite.setRegion(world.screen.game.assets.get(Assets.TEXTURE_BASE + "weapons/" + data.get("texture"), Texture.class));
	}
}
