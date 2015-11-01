package com.jacl.capstone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {
	//constants
	private final String PREF_VOLUME="volume";
	private final String PREF_MUSIC_ENABLED="music.enable";
	private final String PREF_SOUND_ENABLED="sound.enabled";
	private final String PREF_NAME;
	private final String PREF_VERSION;
	
	public GamePreferences(CapstoneGame capstoneGame) {
		PREF_NAME = capstoneGame.GAME_NAME;
		PREF_VERSION = capstoneGame.GAME_VERSION;
	}
	
	protected Preferences getPrefs()
	{
		return Gdx.app.getPreferences(PREF_NAME);
	}
	
	public boolean isSoundEffectsEnabled()
	{
		return getPrefs().getBoolean(PREF_SOUND_ENABLED,true);
	}
	
	public void setSoundEffectsEnabled(boolean soundEffectsEnabled)
	{
		getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
		getPrefs().flush();
	}
	
	public boolean isMusicEffectsEnabled()
	{
		return getPrefs().getBoolean(PREF_MUSIC_ENABLED,true);
	}
	
	public void setMusicEffectsEnabled(boolean musicEffectsEnabled)
	{
		getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEffectsEnabled);
		getPrefs().flush();
	}
	
	public float getVolume()
	{
		return getPrefs().getFloat(PREF_VOLUME,0.5f);
	}
	
	public void setVolume(float volume)
	{
		getPrefs().putFloat(PREF_VOLUME, volume);
		getPrefs().flush();
	}

}
