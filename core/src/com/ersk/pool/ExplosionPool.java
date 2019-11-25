package com.ersk.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ersk.base.SpritesPool;
import com.ersk.sprite.Explosion;


public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound sound;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas = atlas;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    protected Explosion newObject() {  // объект взрыв
        return new Explosion(atlas, sound);
    }

    @Override
    public void dispose() {
        sound.dispose();
        super.dispose();
    }
}
