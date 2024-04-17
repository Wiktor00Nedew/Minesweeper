package com.minesweeper.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends Sprite implements Listener{
    private Board board;
    private Menu menu;

    private boolean gameStarted;

    private int boardSizeX, boardSizeY;

    boolean isGameStarted() {
        return gameStarted;
    }

    void setGameStarted(boolean started) {
        gameStarted = started;
    }

    void setBoardSize(int sizeX, int sizeY) {
        boardSizeX = sizeX;
        boardSizeY = sizeY;
    }

    public Game(int sizeX, int sizeY) {
        boardSizeX = sizeX;
        boardSizeY = sizeY;
    }

    public Board getBoard() {
        return this.board;
    }
    public void create() {
        gameStarted = false;
        board = new Board();
        menu = new Menu();
        menu.create();
        board.createBoard(boardSizeX, boardSizeY);
        EventManager.get().addListener(this);
        board.setPosition((Gdx.graphics.getWidth() - (boardSizeX * Common.TEXTURE_WIDTH)) / 2f, (Gdx.graphics.getHeight() - (boardSizeY * Common.TEXTURE_HEIGHT)) / 2f);

    }
    @Override
    public void onNotify(Event event) {
        if (event.getType() == Event.Type.GAME_STARTED) {
            setGameStarted(true);
            menu.startTimer();
            menu.setTimerCounting(true);
        }
        if (event.getType() == Event.Type.GAME_WON || event.getType() == Event.Type.GAME_LOST) {
            setGameStarted(false);
            menu.setTimerCounting(false);
        }
        if (event.getType() == Event.Type.RESTART_CLICKED) {
            board.createBoard(boardSizeX, boardSizeY);
        }
    }

    @Override
    public void draw(Batch batch) {
        board.draw(batch);
        menu.draw(batch);
    }
}
