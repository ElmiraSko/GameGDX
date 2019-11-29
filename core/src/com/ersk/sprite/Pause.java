package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ersk.base.ScaledTouchUpButton;
import com.ersk.math.Rect;
import com.ersk.screen.GameScreen;

public class Pause extends ScaledTouchUpButton {

    private GameScreen gameScreen;
    private boolean secondPressed = false; // повторное нажатие Keys.SPACE

    public Pause(TextureRegion region, GameScreen gameScreen) {
        super(region);
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        pos.x = worldBounds.getRight() - 0.04f;
        pos.y = worldBounds.getBottom() + 0.04f;
    }

    @Override
    public void action() {
        if (!secondPressed){
            System.out.println(" Pause ");
            gameScreen.pause();
            secondPressed = true;
        } else {
            gameScreen.resume();
            secondPressed = false;}
    }
}
