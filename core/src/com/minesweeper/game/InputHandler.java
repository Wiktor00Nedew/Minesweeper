package com.minesweeper.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import javax.lang.model.element.TypeElement;

public class InputHandler extends InputAdapter {
    private Board board;
    private Game game;
    private Minesweeper minesweeper;
    private Cursor cursor;

    public InputHandler(Board passedBoard, Game passedGame, Minesweeper passedMinesweeper, Cursor passedCursor) {
        board = passedBoard;
        game = passedGame;
        minesweeper = passedMinesweeper;
        cursor = passedCursor;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int windowX = screenX;
        int windowY = Gdx.graphics.getHeight() - screenY;

        // here goes menu interaction
        if (minesweeper.getCurrentState() == Minesweeper.ProgramState.MENU) {
            if (button == Input.Buttons.LEFT) {
                Event event = new Event(Event.Type.BUTTON_CLICKED);
                event.setCoordinates(windowX, windowY);
                EventManager.get().notify(event);
                return true;
            }
            return true;
        }
        else if (minesweeper.getCurrentState() == Minesweeper.ProgramState.GAME) {
            Gdx.app.log("TAG", windowX + " " + windowY);

            if (!board.isPointOnTheBoard(windowX, windowY)) {
                return false;
            }

            Gdx.app.log("TAG", "First test passed");

            Common.Pair field = board.getFieldCoordinates(windowX, windowY);

            if (button == Input.Buttons.LEFT) {
                if (!game.isGameStarted()) {
                    Event event = new Event(Event.Type.GAME_STARTED);
                    event.setCoordinates(field);
                    EventManager.get().notify(event);
                    return true;
                }
                Event event = new Event(Event.Type.FIELD_UNCOVERED);
                event.setCoordinates(field);
                Gdx.app.log("TAG", "uncoveredEventSent");
                EventManager.get().notify(event);
            } else if (button == Input.Buttons.RIGHT) {
                if (!game.isGameStarted()) {
                    return false;
                }
                Event event = new Event(Event.Type.FIELD_FLAG_ACTION);
                event.setCoordinates(field);
                Gdx.app.log("TAG", "flagEventSent");
                EventManager.get().notify(event);
            }

            return true;
        }
        return true;
    }
}
