package com.ersk.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;

public class Explosion extends Sprite { // общий шаблон для спрайта взрыва

    private float animateInterval = 0.01f;
    private float animateTimer;

    private Sound sound;

    public Explosion(TextureAtlas atlas, Sound sound) {
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.sound = sound;
    }

    @Override
    public void update(float delta) { // по таймеру сменяем кадры
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            frame++;
            if (frame == regions.length) { // если дошли до последнего кадра, то destroy()
                destroy();
            }
        }
    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportion(height);
        sound.play();
    }

    @Override
    public void destroy() {
        frame = 0;
        super.destroy();
    }
}
