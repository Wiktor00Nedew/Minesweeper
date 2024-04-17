package com.minesweeper.game;

public class Event {
    public enum Type {
        FIELD_UNCOVERED,
        FIELD_FLAG_ACTION,
        GAME_STARTED,
        GAME_LOST,
        GAME_WON,
        BUTTON_CLICKED,
        RESTART_CLICKED,
        EXIT_CLICKED
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private Type type;
    private Common.Pair coordinates;

    public Event (Type eventType) {
        this.type = eventType;
        coordinates = new Common.Pair();
    }
    public void setCoordinates(int x, int y) {
        coordinates.first = x;
        coordinates.second = y;
    }

    public void setCoordinates(Common.Pair newCoordinates) {
        coordinates.first = newCoordinates.first;
        coordinates.second = newCoordinates.second;
    }

    public Common.Pair getCoordinates() {
        return coordinates;
    }


}
