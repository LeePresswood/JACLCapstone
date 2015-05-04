package tutorial;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class InputTutorial implements InputProcessor{
	
	public ScreenTutorialGame screen;
	
	public InputTutorial(ScreenTutorialGame screenTutorialGame) {
		// TODO Auto-generated constructor stub
		this.screen = screenTutorialGame;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode){
		case Keys.UP:
			screen.up = true;
			break;
		case Keys.DOWN:
			screen.down = true;
			break;
		case Keys.LEFT:
			screen.left = true;
			break;
		case Keys.RIGHT:
			screen.right = true;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode){
		case Keys.UP:
			screen.up = false;
			break;
		case Keys.DOWN:
			screen.down = false;
			break;
		case Keys.LEFT:
			screen.left = false;
			break;
		case Keys.RIGHT:
			screen.right = false;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
