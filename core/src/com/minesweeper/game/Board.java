package com.minesweeper.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Board extends Sprite implements Listener{
    private ArrayList<Field[]> board;

    private Texture oneTexture;
    private Texture twoTexture;
    private Texture threeTexture;
    private Texture fourTexture;
    private Texture fiveTexture;
    private Texture sixTexture;
    private Texture sevenTexture;
    private Texture eightTexture;
    private Texture freeTexture;
    private Texture coveredTexture;
    private Texture mineTexture;
    private Texture flaggedTexture;

    private int boardSizeX, boardSizeY;
    private float posX, posY;

    private int uncoveredCount;

    private int mineCount;

    public Board() {
        oneTexture = new Texture("one.png");
        twoTexture = new Texture("two.png");
        threeTexture = new Texture("three.png");
        fourTexture = new Texture("four.png");
        fiveTexture = new Texture("five.png");
        sixTexture = new Texture("six.png");
        sevenTexture = new Texture("seven.png");
        eightTexture = new Texture("eight.png");
        freeTexture = new Texture("free.png");
        coveredTexture = new Texture("covered.png");
        mineTexture = new Texture("mine.png");
        flaggedTexture = new Texture("flagged.png");

        EventManager.get().addListener(this);

        setPosition(0, 0);
    }

    @Override
    public void setPosition(float x, float y) {
        posX = x;
        posY = y;
        super.setPosition(x, y);
    }

    public void createBoard(int sizeX, int sizeY) {
        uncoveredCount = 0;
        setPosition((Gdx.graphics.getWidth() - (boardSizeX * Common.TEXTURE_WIDTH)) / 2f, (Gdx.graphics.getHeight() - (boardSizeY * Common.TEXTURE_HEIGHT)) / 2f);
        board = new ArrayList<Field[]>();
        boardSizeX = sizeX;
        boardSizeY = sizeY;
        for(int y = 0; y < sizeY; y++) {
            Field[] fieldArray = new Field[sizeX];
            for(int x = 0; x < sizeX; x++) {
                fieldArray[x] = new Field();
                fieldArray[x].setType(Field.FieldType.FREE);
                fieldArray[x].setState(Field.FieldState.COVERED);
                fieldArray[x].setTextures(coveredTexture, freeTexture, flaggedTexture);
            }
            board.add(fieldArray);
        }
    }

    public void fillBoard(int numberOfMines) {
        mineCount = numberOfMines;
        Random rand = new Random();
        for(int i = 0; i < numberOfMines; i++) {
            int mineY;
            int mineX;
            do {
                mineX = rand.nextInt(0, boardSizeX - 1);
                mineY = rand.nextInt(0, boardSizeY - 1);
            } while (board.get(mineY)[mineX].getType() == Field.FieldType.MINE ||
                     board.get(mineY)[mineX].getState() != Field.FieldState.COVERED);
            board.get(mineY)[mineX].setType(Field.FieldType.MINE);
            board.get(mineY)[mineX].setUncoveredTexture(mineTexture);
        }

        //fill in the numbers
        for(int y = boardSizeY - 1; y >= 0; y--) {
            for(int x = 0; x < boardSizeX; x++) {
                if (board.get(y)[x].getType() != Field.FieldType.MINE) {
                    int mineCount = 0;
                    for (int i = 0; i < Common.dx.length; i++) {
                        if (x == 0 && Common.dx[i] == -1) continue;
                        if (y == 0 && Common.dy[i] == -1) continue;
                        if (x == boardSizeX - 1 && Common.dx[i] == 1) continue;
                        if (y == boardSizeY - 1 && Common.dy[i] == 1) continue;

                        if(board.get(y + Common.dy[i])[x + Common.dx[i]].getType() == Field.FieldType.MINE)
                            mineCount++;
                    }
                    Gdx.app.log("TAG", mineCount + " " + x + " " + y);
                    switch(mineCount){
                        case 0:
                            board.get(y)[x].setType(Field.FieldType.FREE);
                            board.get(y)[x].setUncoveredTexture(freeTexture);
                            break;
                        case 1:
                            board.get(y)[x].setType(Field.FieldType.I);
                            board.get(y)[x].setUncoveredTexture(oneTexture);
                            break;
                        case 2:
                            board.get(y)[x].setType(Field.FieldType.II);
                            board.get(y)[x].setUncoveredTexture(twoTexture);
                            break;
                        case 3:
                            board.get(y)[x].setType(Field.FieldType.III);
                            board.get(y)[x].setUncoveredTexture(threeTexture);
                            break;
                        case 4:
                            board.get(y)[x].setType(Field.FieldType.IV);
                            board.get(y)[x].setUncoveredTexture(fourTexture);
                            break;
                        case 5:
                            board.get(y)[x].setType(Field.FieldType.V);
                            board.get(y)[x].setUncoveredTexture(fiveTexture);
                            break;
                        case 6:
                            board.get(y)[x].setType(Field.FieldType.VI);
                            board.get(y)[x].setUncoveredTexture(sixTexture);
                            break;
                        case 7:
                            board.get(y)[x].setType(Field.FieldType.VII);
                            board.get(y)[x].setUncoveredTexture(sevenTexture);
                            break;
                        case 8:
                            board.get(y)[x].setType(Field.FieldType.VIII);
                            board.get(y)[x].setUncoveredTexture(eightTexture);
                            break;
                    }
                }
            }
        }

    }

    public void uncoverField(int x, int y) {
        board.get(y)[x].setState(Field.FieldState.UNCOVERED);
        uncoveredCount++;

        if(board.get(y)[x].getType() == Field.FieldType.FREE) {
            for (int i = 0; i < Common.dx.length; i++) {
                if (x == 0 && Common.dx[i] == -1) continue;
                if (y == 0 && Common.dy[i] == -1) continue;
                if (x == boardSizeX - 1 && Common.dx[i] == 1) continue;
                if (y == boardSizeY - 1 && Common.dy[i] == 1) continue;

                if (board.get(y + Common.dy[i])[x + Common.dx[i]].getState() == Field.FieldState.COVERED)
                    this.uncoverField(x + Common.dx[i], y + Common.dy[i]);
            }
        }
        else if (board.get(y)[x].getType() == Field.FieldType.MINE) {
            EventManager.get().notify(new Event(Event.Type.GAME_LOST));
        }

        if (uncoveredCount == boardSizeX * boardSizeY - mineCount) {
            EventManager.get().notify(new Event(Event.Type.GAME_WON));
        }
    }

    public void uncoverFirstField(int x, int y, int numberOfMines) {
        board.get(y)[x].setState(Field.FieldState.UNCOVERED);

        for (int i = 0; i < Common.dx.length; i++) {
            if (x == 0 && Common.dx[i] == -1) continue;
            if (y == 0 && Common.dy[i] == -1) continue;
            if (x == boardSizeX - 1 && Common.dx[i] == 1) continue;
            if (y == boardSizeY - 1 && Common.dy[i] == 1) continue;

            board.get(y + Common.dy[i])[x + Common.dx[i]].setState(Field.FieldState.UNCOVERED);
        }

        this.fillBoard(numberOfMines);

        for (int i = 0; i < Common.dx.length; i++) {
            if (x == 0 && Common.dx[i] == -1) continue;
            if (y == 0 && Common.dy[i] == -1) continue;
            if (x == boardSizeX - 1 && Common.dx[i] == 1) continue;
            if (y == boardSizeY - 1 && Common.dy[i] == 1) continue;

            this.coverField(x + Common.dx[i], y + Common.dy[i]);
        }

        this.uncoverField(x, y);
    }

    public void flagField(int x, int y) {
        board.get(y)[x].setState(Field.FieldState.FLAGGED);
    }

    public void coverField(int x, int y) {
        board.get(y)[x].setState(Field.FieldState.COVERED);
    }

    @Override
    public void onNotify(Event event) {
        if (event.getType() == Event.Type.FIELD_FLAG_ACTION) {
            if (board.get(event.getCoordinates().second)[event.getCoordinates().first].getState() == Field.FieldState.COVERED)
                flagField(event.getCoordinates().first, event.getCoordinates().second);
            else if (board.get(event.getCoordinates().second)[event.getCoordinates().first].getState() == Field.FieldState.FLAGGED)
                coverField(event.getCoordinates().first, event.getCoordinates().second);
        }
        else if (event.getType() == Event.Type.FIELD_UNCOVERED) {
            if (board.get(event.getCoordinates().second)[event.getCoordinates().first].getState() == Field.FieldState.COVERED)
                uncoverField(event.getCoordinates().first, event.getCoordinates().second);
        }
        else if (event.getType() == Event.Type.GAME_STARTED) {
            uncoverFirstField(event.getCoordinates().first, event.getCoordinates().second, 108); // todo add menu to set this
            Gdx.app.log("TAG", "filled board");
        }
    }

    public boolean isPointOnTheBoard(int globalX, int globalY) {
        return !(globalX < this.posX) &&
                !(globalX > this.posX + (this.boardSizeX * Common.TEXTURE_WIDTH)) &&
                !(globalY < this.posY) &&
                !(globalY > this.posY + (this.boardSizeY * Common.TEXTURE_HEIGHT));
    }
    public Common.Pair getFieldCoordinates(int globalX, int globalY) {
        Common.Pair coordinates = new Common.Pair();
        coordinates.first = (globalX - Math.round(posX)) / Common.TEXTURE_WIDTH;
        coordinates.second = (globalY - Math.round(posY)) / Common.TEXTURE_HEIGHT;

        return coordinates;
    }

    @Override
    public void draw(Batch batch) {
        for(int y = boardSizeY - 1; y >= 0; y--) {
            for(int x = 0; x < boardSizeX; x++) {
                board.get(y)[x].updateTexture();
                board.get(y)[x].draw(batch, (int)posX + x * Common.TEXTURE_WIDTH, (int)posY + y * Common.TEXTURE_HEIGHT);
            }
        }
    }
}
