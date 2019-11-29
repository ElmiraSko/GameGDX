package com.ersk.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Ship;
import com.ersk.math.Rect;
import com.ersk.pool.BulletPool;
import com.ersk.pool.ExplosionPool;
import com.ersk.pool.ManPool;


public class Enemy extends Ship {

    private enum State { DESCENT, FIGHT }
    private State state;

    private Vector2 descentV = new Vector2(0, -0.15f);

    private ManPool manPool; // пул космонавтов
    private int level;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, ManPool manPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.manPool = manPool;
        this.worldBounds = worldBounds;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (state) {
            case DESCENT:
                reloadTimer = 0f;
                if (getTop() <= worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                    reloadTimer = reloadInterval;
                }
                break;
            case FIGHT:
                if (getTop() < worldBounds.getBottom()) {
                    destroyed = true;
                }
                break;
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
            int hp,
            int level
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
        this.level = level;
        this.v.set(descentV);
        state = State.DESCENT;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        if (level > 1){
            Man man = manPool.obtain();
            man.pos.set(this.pos);
        }

    }

}
