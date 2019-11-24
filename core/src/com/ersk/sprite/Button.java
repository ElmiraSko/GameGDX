package com.ersk.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ersk.base.ScaledTouchUpButton;
import com.ersk.math.Rect;
import com.ersk.screen.GameScreen;

public class Button extends ScaledTouchUpButton {

    private Game game;

    public Button(TextureAtlas atlas, Message message, Game game){
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProportion(0.03f);
        pos.set(message.pos.x, message.pos.y - message.getHeight()*1.5f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}

