package com.jacl.capstone.world.atmosphere;

import com.jacl.capstone.hud.HUD;

public class Money
{
	public HUD hud;
	public int amount;
	
	public Money(HUD hud){
		this.hud = hud;
		amount = 300;
	}
	
	/**
	 * Change money amount by this value.
	 * @param change_by Amount to change by. Positive adds and negative subtracts.
	 */
	public void change(int change_by){
		amount += change_by;
	}
	
	@Override
	public String toString()
	{
		return "" + amount;
	}
}
