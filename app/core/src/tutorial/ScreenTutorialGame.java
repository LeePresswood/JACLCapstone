package tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenTutorialGame extends ScreenAdapter
{
	public TutorialGame game;					//Allows access of game data objects.
	
	public SpriteBatch batch;					//Manages drawing.
	
	public Sprite sprite;						//Almost everything on the screen will be a sprite.
	
	public float sprite_speed = 50f;			//Try to add the 'f' after every float value to assure the most accurate value.
														//Also keep in mind that, because we're using delta * speed to move, this value
														//is out pixels-per-second speed.
	
	public ScreenTutorialGame(TutorialGame game)
	{
		//Pass in the game reference to access the AssetManager and the game-related methods like setScreen(...).
		this.game = game;
		
		//Initialize SpriteBatch. This object manages the background OpenGL that we don't care about.
		batch = new SpriteBatch();
		
		//Initialize sprites with a texture from the AssetManager extension class found in TutorialGame.
		sprite = new Sprite(game.assets.get("texture.jpg", Texture.class));
		sprite.setBounds(0, 30, 50, 50);
	}
	
	/**
	 * The render() method is called every frame to update and draw the screen.
	 * 
	 * Best way I have found to use it is as follows:
	 * Update all items first. This includes movement, spells, attacks, etc.
	 * Collision detection next. This allows us to destroy any unneeded sprites or set knockback on being hit. Set visibility of objects at this point.
	 * Draw afterward so that the update is the most accurate representation of the current state of the game.
	 */
	@Override
	public void render(float delta)
	{
		//Update. If multiple updates need to be made across multiple managers (like the UI, the world, etc), make a new method for neatness.
		sprite.translateX(delta * sprite_speed);
		
		//If sprite's right edge goes off the right side of the screen, destroy it.
		if(sprite.getX() + sprite.getWidth() > Gdx.graphics.getWidth())
			sprite = null;
		
		//Must start drawing with SpriteBatch.begin(). I like to tab-over all items being drawn to make it look like a block of code for simple finding.
		batch.begin();
		
			//Draw if sprite is not null.
			if(sprite != null)
				sprite.draw(batch);
		
		//Batch must end drawing with SpriteBatch.end(). End tabbing-over here.
		batch.end();
	}
}
