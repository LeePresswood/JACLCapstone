package com.jacl.capstone.helpers;

/**
* Calculate the damage an entity takes based upon the amount
* being affected by defense.
* 
* @author Lee
*
*/
public class DamageCalculator
{
	/**
	 * To make damage calculations affected by defense, we
	 * must lower the damage by a certain percentage per
	 * point of defense the given entity has.
	 */
	private final static float PERCENT_PER_DEFENSE_POINT = 2f;
	
	/**
	 * Damage is directly lowered by amount of defense player
	 * has.
	 */
	public static float getDamage(float damage, float defense)
	{
		float total = damage * (100f - defense * PERCENT_PER_DEFENSE_POINT) / 100f;
		return total < 0f ? 0f : total;
	}
}
