package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class Message extends Sprite {

    public Message(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }
}
