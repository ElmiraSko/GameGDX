package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class Message extends Sprite {

    public Message(TextureAtlas atlas){ // конструктор получает атлас
        super(atlas.findRegion("message_game_over")); // вызывает родительский конструктор,
        // в который передает текстуру из атласа используя метод findRegion("message_game_over")
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        // ==
    }
}
