package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;


public class Logo extends Sprite {

    public Vector2 posTouch;
    public Vector2 buffVector;
    public Vector2 v;
    public static float V_LEN = 0.01f;

    public Logo(TextureRegion region) {
        super(region);

        posTouch = new Vector2();
        buffVector = new Vector2();
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {

        posTouch.set(touch);
        v.set(posTouch.cpy().sub(pos));
        v.setLength(V_LEN);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }
}
