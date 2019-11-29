package com.ersk.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ersk.base.SpritesPool;
import com.ersk.sprite.Man;

public class ManPool extends SpritesPool<Man> {

    private TextureRegion region;

    public ManPool(TextureRegion region){
        this.region = region;
    }
    @Override
    protected Man newObject() {
        return new Man(region);
    }
}
