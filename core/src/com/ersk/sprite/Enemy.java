package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;
import com.ersk.math.Rnd;

public class Enemy extends Sprite {

    private Vector2 v = new Vector2();

    private Rect worldBounds;

    public Enemy(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("enemy1"), 1, 2, 2);
        this.worldBounds = worldBounds;
        v.set(0, Rnd.nextFloat(-0.003f, -0.001f)); // скорость корабля
        resize(worldBounds);
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(Rnd.nextFloat(0.1f, 0.15f));
        float posX = Rnd.nextFloat(worldBounds.getLeft() + this.getWidth(), worldBounds.getRight() - this.getWidth());
        float posY = worldBounds.getTop() + this.getHeight();
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.add(v);
        if (pos.y+getHalfHeight() < worldBounds.getBottom()) {
            destroy();
            float posX = Rnd.nextFloat(worldBounds.getLeft() + this.getWidth(), worldBounds.getRight() - this.getWidth());
            float posY = worldBounds.getTop() + this.getHeight();
            pos.set(posX, posY);
        }
    }
}
