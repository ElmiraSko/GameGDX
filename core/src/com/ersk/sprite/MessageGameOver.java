package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class MessageGameOver extends Sprite {

    public MessageGameOver(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
    }
}
