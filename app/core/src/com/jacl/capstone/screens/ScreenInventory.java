package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jacl.capstone.CapstoneGame;

public class ScreenInventory implements Screen {
	public ScreenInventory(CapstoneGame game) {
		this.game = game;
		this.last_screen = game.getScreen();
	}
	
	private InventoryActor inventoryActor;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private BitmapFont bitmap28;
	private TextButton buttonBack;
	public CapstoneGame game;
	private Screen last_screen;
	public static Stage stage;
	private Label heading;
	

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		
		bitmap28 = new BitmapFont(Gdx.files.internal("hud/fonts/font28.fnt"), false);
		
		// heading
		String label = "Options";
		LabelStyle headingStyle = new LabelStyle(bitmap28, Color.WHITE);
		heading = new Label(label, headingStyle);
		heading.setFontScale(2);
		
		//buttons styles
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = bitmap28;
		
		//back button which is back to main menu
		buttonBack = new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(last_screen);
			}
		});
		buttonBack.pad(5);
		
		//create row and column of actors 
		//then show it in grid / table
		int rowActors = 1;
		int columnActors = 8;
		//int actorWidth = Gdx.graphics.getWidth() / rowActors;
		int actorWidth = 2;
		//int actorHeight = Gdx.graphics.getHeight() / columnActors;
		int actorHeight = 2;
		Actor[] actors = new Actor[rowActors * columnActors];
		table.add(heading).padBottom(70);
		table.add();
		table.row();
		for (int i = 0 ; i < rowActors ; i++){
			for(int j = 0; j < columnActors ; j++)
			{
				Actor actor = actors[(i * columnActors) + j];
				//table.add(actor).width(actorWidth).height(actorHeight);
			}
			table.row();
		}
		
		table.add(buttonBack);
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
		bitmap28.dispose();
		
	}

}
