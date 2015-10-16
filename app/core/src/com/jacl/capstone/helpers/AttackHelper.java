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
	
	public Weapon weapon;
	public boolean mid_attack;
	public float attack_time_current;
	public float attack_time_max;
	
	public AttackHelper(MovingEntity entity)
	{
		this.world = entity.world;
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
		attack_time_current = 0f;
		attack_time_max = weapon.use_time;
	}
	
	public void update(float delta)
	{
		if(mid_attack)
		{
			weapon.update(delta);
			attack_time_current += delta;
			if(attack_time_current > attack_time_max)
			{
				mid_attack = false;
			}
		}
	}
}
