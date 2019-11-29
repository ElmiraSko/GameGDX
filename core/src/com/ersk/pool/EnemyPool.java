package com.ersk.pool;

import com.ersk.base.SpritesPool;
import com.ersk.math.Rect;
import com.ersk.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private Rect worldBounds;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private ManPool manPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool, ManPool manPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.manPool = manPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool,  manPool, worldBounds);
    }
}
