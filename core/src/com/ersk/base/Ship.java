package com.ersk.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.math.Rect;
import com.ersk.pool.BulletPool;
import com.ersk.pool.ExplosionPool;
import com.ersk.sprite.Bullet;
import com.ersk.sprite.Explosion;

public abstract class Ship extends Sprite {

    protected final Vector2 v0 = new Vector2();
    protected final Vector2 v = new Vector2();

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected Sound sound;
    protected float bulletHeight;
    protected int damage;

    protected int hp;

    protected float reloadInterval = 0f;
    protected float reloadTimer = 0f;

    protected float animateInterval = 0.05f;
    protected float animateTimer = animateInterval;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {  // стрельба по таймеру
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        animateTimer += delta; // переключение вида корабля
        if (animateTimer > animateInterval) {
            frame = 0;
        }
        pos.mulAdd(v, delta);  // изменение позиции корабля, движение
    }


    @Override
    public void destroy() { // взрыв, корабль убит
        boom();    // строка 80
        super.destroy(); // destroyed = true;
    }

    public void damage(int damage) { // для всех кораблей damage-урон, сколько единиц жизни отнимается
        hp -= damage;
        if (hp <= 0) { // если количество жизней <=0 то вызываем destroy(); строка 57
            destroy();
        }
        animateTimer = 0f; // таймер анимации =0,
        frame = 1; // второй фрейм корабля, когда он красный
    }

    public int getDamage() { // вернуть damage-урон в единицах
        return damage;
    }

    protected void shoot() { // стрельба
        sound.play(0.3f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }
    protected void boom() {  // взрыв
        Explosion explosion = explosionPool.obtain(); // спрайт взрыва
        explosion.set(pos, getHeight()); // устанавливаем в позицию корабля, с шириной равной высоте корабля
    }
}