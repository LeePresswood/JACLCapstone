package com.jacl.capstone.world.atmosphere;

import com.badlogic.gdx.graphics.Color;

/**
 * This is a side project that allows for a day-night cycle in the game.<br><br>
 *  
 * Each game update, sent the time into this class. Use this to determine
 * the in-game time. From this time, determine the color to use in the rendering. By tinting the
 * sprites with this color, we can make the outdoors look different depending upon the time of
 * day.
 * 
 * @author Lee
 *
 */
public class TimeColorer
{
	private static final String COLOR_NIGHT = "4D484D40";
	private static final String COLOR_MORNING = "8D8E9240";
	private static final String COLOR_DAY = "819FC140";
	
	public static Color getColor(GameTime time)
	{
		if(time.hours < 6 || time.hours > 22)
			return getColorGradient(time, COLOR_NIGHT, COLOR_MORNING);
		else if(time.hours >= 6 && time.hours < 14)
			return getColorGradient(time, COLOR_MORNING, COLOR_DAY);
		else
			return getColorGradient(time, COLOR_DAY, COLOR_NIGHT);
	}
	
	/**
	 * //Get a gradient of the colors to determine the correct color.
	 * @param time Time of the day.
	 * @param before Current color of the day.
	 * @param next Next color of the day.
	 * @return Color of the time.
	 */
	private static Color getColorGradient(GameTime time, String before, String next)
	{
		//Get the two colors.
		Color before_color = Color.valueOf(before);
		Color next_color = Color.valueOf(next);
		
		//The color we will be creating will be the interpolation of the two with respect to the time.
		int hour_before;
		if(time.hours < 6 || time.hours > 22)
			hour_before = 22;
		else if(time.hours >= 6 && time.hours < 14)
			hour_before = 6;
		else
			hour_before = 14;
		
		//Find the hour gradient.
		int hour = time.hours - hour_before;
		if(hour < 0)
			hour += 8;
		float hour_percent = ((float) hour) / 8f;
		
		//Find the minute gradient.
		//Note: This will contribute only 1/8 the percentage of its initial value.
		float minute_percent = ((float) time.minutes) / 60f;
		minute_percent /= 8f;
		
		//Get the color portions.
		float percent = hour_percent + minute_percent;
		before_color.r *= percent;
		before_color.g *= percent;
		before_color.b *= percent;
		next_color.r *= percent;
		next_color.g *= percent;
		next_color.b *= percent;
		
		//Add these two colors. This will be the new color.
		return new Color(before_color).add(next_color);
	}
}
