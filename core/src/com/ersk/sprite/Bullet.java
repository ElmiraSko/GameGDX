package com.ersk.sprite;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;

    private final Vector2 v = new Vector2();
    private int damage;  // размер урона от пули
    private Sprite owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(
            Sprite owner, // владелец пули
            TextureRegion region, // регион для отрисовки
            Vector2 pos0,  //  начальная розиция пули
            Vector2 v0,  // скорость
            float height,    // размер пули в виде ее высоты
            Rect worldBounds,  //
            int damage    //
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        this.worldBounds = worldBounds;
        this.damage = damage;
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);              // чтоб двигалась
        if (isOutside(worldBounds)) {
            destroy();    // если вышла за границы экрана, то неактивна
        }
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}

