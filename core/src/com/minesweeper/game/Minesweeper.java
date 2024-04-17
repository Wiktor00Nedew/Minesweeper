package com.minesweeper.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Minesweeper extends ApplicationAdapter implements Listener {
	private SpriteBatch batch;

	private Game game;
	private Menu menu;
	private InputHandler inputHandler;


	public enum ProgramState {
		MENU,
		GAME
	}

	public ProgramState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ProgramState currentState) {
		this.currentState = currentState;
	}

	ProgramState currentState;

	@Override
	public void create () {
		batch = new SpriteBatch();
		game = new Game(30, 20);
		game.create();

		inputHandler = new InputHandler(game.getBoard(), game, this ,new Cursor());
		Gdx.input.setInputProcessor(inputHandler);

		setCurrentState(ProgramState.GAME);

		EventManager.get().addListener(this);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.24f, 0.24f, 0.24f, 1);
		batch.begin();
		game.draw(batch);
		batch.end();

		EventManager.get().handleEvents();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//todo disposes
	}

	@Override
	public void onNotify(Event event) {
		if (event.getType() == Event.Type.GAME_WON || event.getType() == Event.Type.GAME_LOST) {
			setCurrentState(ProgramState.MENU);
		}
		if (event.getType() == Event.Type.RESTART_CLICKED) {
			setCurrentState(ProgramState.GAME);
		}
		if (event.getType() == Event.Type.EXIT_CLICKED) {
			Gdx.app.exit();
		}
	}
}
