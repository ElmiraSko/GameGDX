package com.ersk.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Ship;
import com.ersk.math.Rect;
import com.ersk.pool.BulletPool;

public class Enemy extends Ship {

    private Vector2 temp = new Vector2(0, -0.3f); // временная скорость

    public Enemy(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        if (getTop() > worldBounds.getTop()){ //  пока весь корабль не появится на экране
            this.pos.mulAdd(temp, delta);
            reloadTimer = reloadInterval;
        }else{
            reloadTimer += delta;
            if (reloadTimer > reloadInterval) {
                reloadTimer = 0f;
                shoot();
            }
            pos.mulAdd(v, delta);
        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            Sound sound,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.sound = sound;
        setHeightProportion(height);
        this.hp = hp;

        this.v.set(v0);
    }
}
