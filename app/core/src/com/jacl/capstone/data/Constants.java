package com.jacl.capstone.data;

/**
 * Rather than having random magical numbers floating around everywhere, use this class
 * 
 * @author Lee
 *
 */
public class Constants
{
	//All values should be static members to this class to allow retrieval without instantiation.
	public static final String GAME_NAME = "Game";		//Use this to write the name of the game in the UI box.
	public static final String GAME_VERSION = "0.2";	/* Use this to determine the version of the game in V.U format. 
																		 * Example: Version 1 - Update 4 would be version "1.4"
																		 * Increment the Update number frequently (weekly?) and the Version
																		 * number only when a major feature addition has been made.
																		 * Version number should be 0 until first working version
																		 * of the game has been produced. Another name for this is "Alpha".
																		 */
}
