package com.minesweeper.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Field extends Sprite {
    enum FieldType {
        MINE,
        FREE,
        I,
        II,
        III,
        IV,
        V,
        VI,
        VII,
        VIII
    }

    enum FieldState {
        COVERED,
        UNCOVERED,
        FLAGGED
    }

    private FieldState state;
    private FieldType type;

    private Texture coveredTexture;
    private Texture uncoveredTexture;
    private Texture flaggedTexture;

    public Field() {
        state = FieldState.COVERED;
        type = FieldType.FREE;
    }

    FieldState getState() {
        return state;
    }

    FieldType getType() {
        return type;
    }

    void setState(FieldState newState) {
        state = newState;
    }

    void setType(FieldType newType) {
        type = newType;
    }

    void setTextures(Texture newCoveredTexture, Texture newUncoveredTexture, Texture newFlaggedTexture) {
        uncoveredTexture = newUncoveredTexture;
        coveredTexture = newCoveredTexture;
        flaggedTexture = newFlaggedTexture;
    }

    void setCoveredTexture(Texture newCoveredTexture) {
        coveredTexture = newCoveredTexture;
    }

    void setUncoveredTexture(Texture newUncoveredTexture) {
        uncoveredTexture = newUncoveredTexture;
    }

    void setFlaggedTexture(Texture newFlaggedTexture) {
        flaggedTexture = newFlaggedTexture;
    }

    public void updateTexture() {
        switch(state) {
            case COVERED:
                super.setTexture(coveredTexture);
                break;
            case UNCOVERED:
                super.setTexture(uncoveredTexture);
                break;
            case FLAGGED:
                super.setTexture(flaggedTexture);
                break;
        }
    }

    public void draw(Batch batch, int posX, int posY) {
        batch.draw(this.getTexture(), posX, posY);
    }
}
