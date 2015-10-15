package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.jacl.capstone.CapstoneGame;

public class ScreenOptions extends ScreenAdapter
{
	
	public ScreenOptions(CapstoneGame game)
	{
		this.game = game;
		this.last_screen = game.getScreen();
	}
	
	private Screen last_screen;
	private Stage stage;
	public CapstoneGame game;
	private TextureAtlas atlas;
	private Skin skin;
	private TextButton buttonBack;
	private Table table;
	private BitmapFont bitmap;
	private Label heading;
	private Label volumeValue;
	
	@Override
	/**
	 * This is called when the screen is created.
	 * show settings in game
	 * putting sliders and checkbox for sound and graphic
	 */
	public void show()
	{
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		
		// TableLayout layout = table.getTableLayout();
		
		// fonts
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bitmap = new BitmapFont(Gdx.files.internal("hud/fonts/font44.fnt"), false);
		
		// buttons styles
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = bitmap;
		
		// back button which is back to main menu
		buttonBack = new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(last_screen);
			}
		});
		buttonBack.pad(5);
		
		// heading
		String label = "Options";
		LabelStyle headingStyle = new LabelStyle(bitmap, Color.WHITE);
		heading = new Label(label, headingStyle);
		heading.setFontScale(2);
		
		/*
		 * //create sounds option final CheckBox soundEffectsCheckbox = new
		 * CheckBox("Sound Effects", skin);
		 * soundEffectsCheckbox.setChecked(game.getPreferences
		 * ().isSoundEffectsEnabled() ); soundEffectsCheckbox.addListener(new
		 * ClickListener() { public void clicked(InputEvent event, float x, float
		 * y) { boolean enabled = soundEffectsCheckbox.isChecked();
		 * game.getPreferences().setSoundEffectsEnabled(enabled); } });
		 */
		/*
		 * //create music option final CheckBox musicEffectsCheckbox = new
		 * CheckBox("Music Effects", skin);
		 * musicEffectsCheckbox.setChecked(game.getPreferences
		 * ().isMusicEffectsEnabled() ); musicEffectsCheckbox.addListener(new
		 * ClickListener() { public void clicked(InputEvent event, float x, float
		 * y) { boolean enabled = musicEffectsCheckbox.isChecked();
		 * game.getPreferences().setMusicEffectsEnabled(enabled); } });
		 */
		// range is [0.0,1.0]; step is 0.1f
		/**
		 * slider doesn't work correctly
		 * 
		 * Lee's edit: I looked at your Slider, and it looks like your problem is
		 * with the skin you're using. To fix that, you'll need a SliderStyle.
		 * This will allow you to set the background of the slider bar and the
		 * little knob that the user moves. I made an example in the code. 
		 * 
		 * Also, the volumeValue label is broken for the same reason. The name "default"
		 * is not defined in the skin you're using. You'll need to make a LabelStyle to fix this.
		 */
		SliderStyle style = new SliderStyle();
		style.background = skin.getDrawable("button.up");
		style.knob = skin.getDrawable("button.down");
		
		final Slider volumeSlider = new Slider(0.1f, 4, 0.1f, false, style);
		volumeSlider.setValue(1);
		volumeSlider.getCaptureListeners();
		// volumeValue = new Label("volume", skin);
		updateVolumeLabel();
		
		// add together stuffs
		table.add(heading);
		table.row();
		table.getCell(heading).spaceBottom(70);
		table.add(volumeSlider);
		// table.add(volumeValue);
		// table.add(musicEffectsCheckbox);
		// table.row();
		// table.add(soundEffectsCheckbox);
		// table.row();
		table.add(buttonBack);
		table.row();
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
	}
	
	@Override
	public void dispose()
	{
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		bitmap.dispose();
	}
	
	private void updateVolumeLabel()
	{
		// float volume=(game.getPreferences().getVolume()*100);
		// volumeValue.setText(String.format((Locale.US, "%0.1f%%",volume));
	}
}
