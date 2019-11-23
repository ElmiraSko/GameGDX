package com.ersk.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Ship;
import com.ersk.math.Rect;
import com.ersk.pool.BulletPool;
import com.ersk.pool.ExplosionPool;


public class Enemy extends Ship {

    private enum State { DESCENT, FIGHT, GAME_OVER }

    private State state;

    private Vector2 descentV = new Vector2(0, -0.15f);

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
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
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                }
                break;
                //== ?
            case GAME_OVER:
                super.destroy();
                bulletPool.removeFreeObjects();
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
        //======================
        this.v.set(descentV);
        state = State.DESCENT;
    }

    public boolean isBulletCollision(Rect bullet) {  // пересечение спрайта корабля со спрайтом пули
        return !( // есле не ..
                bullet.getRight() < getLeft()     //  пуля левее корабля
                        || bullet.getLeft() > getRight()  //  пуля правее корабля
                        || bullet.getBottom() > getTop()  // пуля выше корабля
                        || bullet.getTop() < pos.y     // пуля ниже чем середина коробля
        );
    }
}
