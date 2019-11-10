package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;


public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
        setHeightProportion(1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
