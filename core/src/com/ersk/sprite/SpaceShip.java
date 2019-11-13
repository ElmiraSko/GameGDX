package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class SpaceShip extends Sprite {

    public Vector2 posTouch;
    public Vector2 buffVector;
    public static float V_LEN = 0.01f;
    private Vector2 v = new Vector2();
    private Rect worldBounds;

    private TextureRegion[] textureRegions;


    public SpaceShip(TextureAtlas atlas, int count, int index) {
        super(atlas.findRegion("main_ship"), count, index);
        setHeightProportion(0.2f);
        posTouch = new Vector2();
        buffVector = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        pos.set(0.07f, worldBounds.getBottom() + 0.15f);  //  позиция спрайта относительно экрана
    }

    @Override
    public void update(float delta) {  // обновление свойств спрайта
        buffVector.set(posTouch);
        if (buffVector.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else pos.set(posTouch);

        checkBounds(); // метод проверяет пересечение с границей экрана
    }

    private void checkBounds() {  // если спрайт вышел за границу экрана
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        posTouch.set(touch);                 // в вектор posTouch задали координаты клика
        v.set(posTouch.cpy().sub(pos));
        v.setLength(V_LEN);                  // вектору v задали длину равную  V_LEN = 0.01f
        return false;
    }
}
