package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
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
	private TextureAtlas atlas1;
	private Skin skin1;
	private TextButton buttonBack;
	private Table table;
	private BitmapFont bitmap;
	private Label heading;
	private Label volumeValue;
	private Slider volumeSlider;
	
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
		
		atlas1 = new TextureAtlas("uiskin.atlas");
		skin1 = new Skin(atlas1);
		
		// TableLayout layout = table.getTableLayout();
		
		// fonts
		table = new Table(skin1);
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
		
		
		// make checkbox Style
		CheckBoxStyle checkboxStyle = new CheckBoxStyle();
		checkboxStyle.checkboxOff = skin1.getDrawable("check-off");
		checkboxStyle.checkboxOn = skin1.getDrawable("check-on");
		checkboxStyle.font=bitmap;
		
		//create sound checkbox effects
		final CheckBox soundEffectsCheckbox = new CheckBox("Sound Effects", checkboxStyle);
		soundEffectsCheckbox.setChecked(true);
		//soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled() ); 
		/*soundEffectsCheckbox.addListener(newClickListener() { public void clicked(InputEvent event, float x, float y) { 
				boolean enabled = soundEffectsCheckbox.isChecked();
				game.getPreferences().setSoundEffectsEnabled(enabled); 
			} 
		});*/
		//create music checkbox effects
		final CheckBox musicEffectsCheckbox = new CheckBox("Music Effects", checkboxStyle);
		musicEffectsCheckbox.setChecked(true);
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
		style.background = skin1.getDrawable("default-slider");
		style.knob = skin1.getDrawable("default-slider-knob");
		
		volumeSlider = new Slider(0, 100, 1, false, style);
		volumeSlider.setValue(50);
		//volumeSlider.getCaptureListeners();
		volumeSlider.addCaptureListener(new ChangeListener(){
			public void changed (ChangeEvent event, Actor actor){
				int volume = Math.round(volumeSlider.getValue());
				volumeValue.setText(Integer.toString(volume));
			}
		});
		CharSequence x = "50";
		volumeValue = new Label(x,headingStyle);
		updateVolumeLabel();
		
		// add together stuffs
		table.add(heading);
		table.row();
		table.getCell(heading).spaceBottom(70);
		table.add(volumeSlider);
		table.add(volumeValue);
		table.row();
		table.add(musicEffectsCheckbox);
		table.row();
		table.add(soundEffectsCheckbox);
		table.row();
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
	
	public void update(float delta){
		float volume = (volumeSlider.getValue()/4*100);
		volumeValue.setText(Float.toString(volume));
	}
	
	private void updateVolumeLabel()
	{
		//float volume=(game.getPreferences().getVolume()*100);
		//float volume = volumeSlider.getValue();
		//volumeValue.setText(String.format("%0.1f%%",volume));
	}
}
