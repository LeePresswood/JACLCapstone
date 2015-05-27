package com.jacl.capstone.world.entities.npc.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.world.entities.npc.NPC;

/**
 * Linear interpolation AI. Move slightly toward the player with every update of time.
 * 
 * @author Lee
 *
 */
public class LerpAI extends AI
{
	//There will be a radius around the AI unit where the unit is close enough to attack the player.
	private final float ATTACK_RADIUS = 0.5f;
	private Vector2 player_position;
	
	public LerpAI(NPC npc)
	{
		super(npc);
		player_position = new Vector2();
	}

	@Override
	public void updateThinking(float delta)
	{
		System.out.println("Looking at player at position " + handler.player.getCenterX() + "," + handler.player.getCenterY());
	}

	@Override
	public void updatePosition(float delta)
	{
		System.out.println("Moving step.");
	}

	@Override
	public void updateAction(float delta)
	{
		//If we're within the attack radius of the player, attack.
		if(Vector2.dst2(npc.getCenterX(), npc.getCenterY(), handler.player.getCenterX(), handler.player.getCenterY()) <= ATTACK_RADIUS)
			System.out.println("Can attack.");
		else
			System.out.println("Can't attack.");
	}
}
