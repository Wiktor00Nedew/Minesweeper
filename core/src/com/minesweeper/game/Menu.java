package com.minesweeper.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.TimeUtils;

public class Menu extends Sprite implements Listener{
    private Button buttonRestart;
    private Button buttonExit;
    private Label timeCounter;
    private Title title;

    private long startingTime;

    private boolean countingTime;

    private boolean displayMenu;
    public void create() {
        buttonRestart = new Button();
        buttonRestart.setText("Restart");
        buttonRestart.setPosition(120, 500);

        buttonExit = new Button();
        buttonExit.setText("Exit");
        buttonExit.setPosition(120, 372);

        startingTime = TimeUtils.millis();
        countingTime = false;

        displayMenu = false;

        timeCounter = new Label();
        timeCounter.setText(String.valueOf(TimeUtils.timeSinceMillis(startingTime) / 1000));
        timeCounter.setPosition(1360, 500);

        title = new Title();
        title.setText("");
        title.setPosition(500, 780);

        EventManager.get().addListener(this);
    }

    public void startTimer() {
        startingTime = TimeUtils.millis();
    }

    public void setTimerCounting(boolean value) {
        countingTime = value;
    }

    @Override
    public void draw(Batch batch) {
        if (displayMenu) {
            buttonRestart.draw(batch);
            buttonExit.draw(batch);
        }

        title.draw(batch);

        if (countingTime)
            timeCounter.setText(String.valueOf(TimeUtils.timeSinceMillis(startingTime) / 1000));
        timeCounter.draw(batch);
    }

    @Override
    public void onNotify(Event event) {
        if (event.getType() == Event.Type.BUTTON_CLICKED) {
            if (event.getCoordinates().first >= buttonRestart.posX &&
                event.getCoordinates().first <= buttonRestart.posX + Common.BUTTON_WIDTH &&
                event.getCoordinates().second >= buttonRestart.posY &&
                event.getCoordinates().second <= buttonRestart.posY + Common.BUTTON_HEIGHT) {
                EventManager.get().notify(new Event(Event.Type.RESTART_CLICKED));
            }

            if (event.getCoordinates().first >= buttonExit.posX &&
                event.getCoordinates().first <= buttonExit.posX + Common.BUTTON_WIDTH &&
                event.getCoordinates().second >= buttonExit.posY &&
                event.getCoordinates().second <= buttonExit.posY + Common.BUTTON_HEIGHT) {
                EventManager.get().notify(new Event(Event.Type.EXIT_CLICKED));
            }
        }

        if (event.getType() == Event.Type.GAME_WON || event.getType() == Event.Type.GAME_LOST) {
            displayMenu = true;
        }
        if (event.getType() == Event.Type.GAME_WON) {
            title.setText("YOU WIN");
        }
        if (event.getType() == Event.Type.GAME_LOST) {
            title.setText("YOU LOSE");
        }
        if (event.getType() == Event.Type.RESTART_CLICKED) {
            displayMenu = false;
            title.setText("");
        }
    }
}
