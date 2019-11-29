package com.ersk.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ersk.base.ScaledTouchUpButton;
import com.ersk.math.Rect;
import com.ersk.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen){
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.03f);
        setTop(-0.05f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}

