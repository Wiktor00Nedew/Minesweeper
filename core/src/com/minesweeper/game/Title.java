package com.minesweeper.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.w3c.dom.Text;

public class Title extends Sprite{
    BitmapFont font;
    GlyphLayout glyph;
    String text;

    Texture background;

    float posX, posY;

    public Title() {
        font = new BitmapFont();
        font.getData().setScale(3);
        text = "";
        glyph = new GlyphLayout(font, text);
        background = new Texture("title.png");
        setTexture(background);
        posX = 0;
        posY = 0;
    }
    @Override
    public void draw(Batch batch) {
        batch.draw(this.getTexture(), posX, posY);
        font.draw(batch, text, posX + (Common.BUTTON_WIDTH + 0.31f * glyph.width), posY + (Common.BUTTON_HEIGHT + 0.31f * glyph.height));
    }

    @Override
    public void setPosition(float x, float y) {
        posX = x;
        posY = y;
        super.setPosition(x, y);
    }

    public void setText(String newText) {
        text = newText;
        glyph.setText(font, text);
    }
}

