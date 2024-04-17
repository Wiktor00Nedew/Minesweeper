package com.minesweeper.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.w3c.dom.Text;

public class Label extends Sprite{
    BitmapFont font;
    GlyphLayout glyph;
    String text;

    Texture background;

    float posX, posY;

    public Label() {
        font = new BitmapFont();
        text = "";
        glyph = new GlyphLayout(font, text);
        background = new Texture("label.png");
        setTexture(background);
        posX = 0;
        posY = 0;
    }
    @Override
    public void draw(Batch batch) {
        batch.draw(this.getTexture(), posX, posY);
        font.draw(batch, text, posX + (Common.BUTTON_WIDTH - glyph.width) / 2f, posY + (Common.BUTTON_HEIGHT - glyph.height) / 1.4f);
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
