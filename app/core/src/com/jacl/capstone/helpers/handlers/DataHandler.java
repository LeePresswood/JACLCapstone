package com.jacl.capstone.helpers.handlers;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.world.World;

/**
 * This class will manage the reading and presenting of any XML data.
 * 
 * @author Lee
 *
 */
public class DataHandler
{
	public World world;
	
	public Element player_root;
	public Element entity_root;
	
	public DataHandler(World world)
	{
		this.world = world;
		
		//Get all data roots.
		XmlReader reader = new XmlReader();
		try
		{
			player_root = reader.parse(Gdx.files.local("saves/test.xml"));
			entity_root = reader.parse(Gdx.files.internal("data/enemies.xml"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void handlerInit()
	{
		
	}
}
