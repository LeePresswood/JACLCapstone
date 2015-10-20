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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jacl.capstone.CapstoneGame;

public class ScreenCredit extends ScreenAdapter{

	public ScreenCredit(CapstoneGame game) {
		this.game = game;
		this.last_screen = game.getScreen();
	}
	
	private Screen last_screen;
	private Stage stage;
	public CapstoneGame game;
	private TextureAtlas atlas;
	private Skin skin;
	private TextButton buttonBack;
	private BitmapFont bitmap;
	private Label heading;

	private static final String shortStory="";
	@Override
	/**
	 * This is called when the credits button on main menu is selected.
	 * Show story or contributors.
	 */
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		bitmap = new BitmapFont(Gdx.files.internal("hud/fonts/font44.fnt"),false);
		
		//heading
		String label = "Credits";
		LabelStyle headingStyle = new LabelStyle(bitmap, Color.WHITE);
		heading = new Label(label,headingStyle);
		heading.setFontScale(2);
		
		//create scrollPane content
		LabelStyle contentStyle = new LabelStyle(bitmap, Color.WHITE);
		final Label text = new Label(shortStory,contentStyle);
		text.setFontScale(1);
		text.setAlignment(Align.center);
		text.setWrap(true);
		//add label for contributors
		final Label contributor = new Label("Contributor: ",contentStyle);
		text.setFontScale(1);
		contributor.setAlignment(Align.center);
		contributor.setWrap(true);
		final Label lee = new Label(" - Lee Presswood",contentStyle);
		text.setFontScale(1);
		lee.setAlignment(Align.center);
		lee.setWrap(true);
		final Label cj = new Label(" - Charles Voege",contentStyle);
		text.setFontScale(1);
		cj.setAlignment(Align.center);
		cj.setWrap(true);
		final Label joe = new Label(" - Joseph Trammel",contentStyle);
		text.setFontScale(1);
		joe.setAlignment(Align.center);
		joe.setWrap(true);
		final Label amy = new Label(" - Amy Schmidt",contentStyle);
		text.setFontScale(1);
		amy.setAlignment(Align.center);
		amy.setWrap(true);
		final Label huy = new Label(" - Huy Trinh",contentStyle);
		text.setFontScale(1);
		huy.setAlignment(Align.center);
		huy.setWrap(true);
		
		// scroll table and fonts
		/*
		final Table scrollTable = new Table();
		scrollTable.add(text);
		scrollTable.row();
		scrollTable.add(contributor);
		scrollTable.row();
		scrollTable.add(lee);
		scrollTable.row();
		scrollTable.add(cj);
		scrollTable.row();
		scrollTable.add(joe);
		scrollTable.row();
		scrollTable.add(amy);
		scrollTable.row();
		scrollTable.add(huy);
		scrollTable.row();
		*/
		//final ScrollPane scroller = new ScrollPane(scrollTable);
		
		final Table table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//buttons styles
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = bitmap;
		
		//back button which is back to main menu
		buttonBack = new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(last_screen);
			}
		});
		buttonBack.pad(5);
		
		//add together stuffs
		table.add(heading);
		table.row();
		table.getCell(heading).spaceBottom(70);
		//table.add(scroller);
		table.add(buttonBack);
		table.row();
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
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		bitmap.dispose();
	}
}
