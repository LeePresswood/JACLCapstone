package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.InputProcessor;
import com.jacl.capstone.CapstoneGame;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * 
 * @author huy_trinh
 * Also need game state such as menu, in-game, gameover
 * States are in int from 1-3.
 *  
 */
public class ScreenMenu implements Screen{

	private Stage stage;
	public CapstoneGame game;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonExit,buttonLoad,buttonNew,buttonCredit,buttonOptions;
	private BitmapFont bitmap;
	private Label heading;
	
	public ScreenMenu(CapstoneGame game) {
		this.game = game;
	}

	public void draw()
	{
		/* Draw Background with 4 buttons: New Game, Load, Options and Credits 
		 * 
		 */
		
	}
	
	/**
	 * This is called when the user click button
	 * New Game: start a new game
	 * Load: Gather the player's location and time from the save file.
	 * Options: show volume, graphic and such
	 * Credits: mostly name
	 */	
	public void update(float delta)
	{
		
	}

	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		
		//fonts
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bitmap = new BitmapFont(Gdx.files.internal("hud/fonts/font44.fnt"),false);
		
		//buttons styles
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = bitmap;
		
		buttonExit = new TextButton("EXIT", textButtonStyle);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
		buttonExit.pad(20);
		
		buttonNew = new TextButton("New Game",textButtonStyle);
		buttonNew.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(new ScreenGame(game));
			}
		});
		buttonNew.pad(15);
		
		//heading
		LabelStyle headingStyle = new LabelStyle(bitmap, Color.WHITE);
		heading = new Label(game.GAME_VERSION,headingStyle);
		heading.setFontScale(3);
		
		//add together stuffs
		table.add(heading);
		table.row();
		table.getCell(heading).spaceBottom(50);
		table.add(buttonNew);
		table.getCell(buttonNew).spaceBottom(15);
		table.row();
		table.add(buttonExit);
		table.debug();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		bitmap.dispose();
	}
}
