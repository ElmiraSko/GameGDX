package com.ersk.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class SpaceShip extends Sprite {

    public Vector2 posTouch;
    public Vector2 buffVector;
    public static float V_LEN = 0.01f;
    private Vector2 v = new Vector2();
    private boolean flag = false; // если false, то работает keyDown, если true, то touchDown
    private Rect worldBounds;

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
        if (flag){
            buffVector.set(posTouch);
            if (buffVector.sub(pos).len() > V_LEN) {
                pos.add(v);
            } else pos.set(posTouch);
        }else {
            pos.add(v);
        }
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
        flag = true;
        // в векторе posTouch меняется только значение по х
        posTouch.set(touch.x, worldBounds.getBottom() + 0.15f);
        v.set(posTouch.cpy().sub(pos));
        v.setLength(V_LEN);  // вектору v задали длину равную  V_LEN = 0.01f
        return false;
    }

    public boolean keyDown(int keycode){
        flag = false;
        v.setLength(0f); // обнулили

        if (keycode == Input.Keys.LEFT){
            v.set(-V_LEN, 0);
        }
        if (keycode == Input.Keys.RIGHT){
            v.set(V_LEN, 0);
        }
        return false;
    }

    public boolean keyUp(int keycode){
        v.set(0, 0); // обнуляем вектор скорости
        return false;
    }
}
