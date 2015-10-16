package com.jacl.capstone.helpers;

import com.jacl.capstone.data.enums.ItemSelection;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.items.ItemFactory;
import com.jacl.capstone.world.entities.player.items.Weapon;

/**
 * Manages attacking.
 * @author Lee
 */
public class AttackHelper
{
	private World world;
	private MovingEntity entity;
	
	public boolean attacking, mid_attack;
	public float attack_time_current;
	public float attack_time_max;
	
	public Weapon weapon;
	
	public AttackHelper(MovingEntity entity)
	{
		this.world = entity.world;
		this.entity = entity;
	}
	
	/**
	 * Attack was requested. Send in the item we attacked with.
	 */
	public void doAttack(ItemSelection item_selection)
	{
		//Get the selected item if a copy of the item does not exist.
		weapon = ItemFactory.spawn(item_selection, world);
		world.entity_handler.add(weapon);
		
		//We don't want to stop mid attack. Commit to the attack until the end by setting a mid-attack flag.
		mid_attack = true;
	}
	
	public void update(float delta)
	{
		if(mid_attack)
		{
			//Update the item's animation and collision.
			weapon.update(delta);
			
			attack_time_current += delta;
			if(attack_time_current > attack_time_max)
			{
				attack_time_current = 0f;
				mid_attack = false;
			}
		}
	}
}
