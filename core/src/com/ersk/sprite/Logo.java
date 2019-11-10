package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;


public class Logo extends Sprite {

    public Vector2 posTouch;
    public Vector2 buffVector;
    public Vector2 v;
    public static float V_LEN = 0.01f;

    public Logo(TextureRegion region) {
        super(region);

        posTouch = new Vector2();
        buffVector = new Vector2(); // буфер
        v = new Vector2();     // вектор скорости
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {

        posTouch.set(touch);
        v.set(posTouch.cpy().sub(getPos()));
        v.setLength(V_LEN);                  // вектору v задали длину равную  V_LEN = 0.01f
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }
}
