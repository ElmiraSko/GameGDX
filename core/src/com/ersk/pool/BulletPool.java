package com.ersk.pool;

import com.ersk.math.SpritesPool;
import com.ersk.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}