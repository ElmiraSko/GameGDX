package com.ersk.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.BaseScreen;

public class MenuScreen extends BaseScreen { // объект этого класса - экран
    private Texture img;
    private Vector2 pos, posTouch;
    private Vector2 buffVector;
    private Vector2 v; // вектор скорости
    private static float V_LEN = 2.5f; // скорость движения
    private boolean flag; // если true - touchDown, если false - keyDown)

    @Override
    public void show() {
        super.show();
        img = new Texture("orange.png");
        pos = new Vector2(); // позиция картинки (левый нижний угол)
        posTouch = new Vector2(); // координаты точки touch
        buffVector = new Vector2();
        v = new Vector2();
        flag = false; //

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        if (flag){ // если touch
            buffVector.set(posTouch); // при каждом кадре в buffVector записываются координаты точки клика
            if (buffVector.sub(pos).len() > V_LEN) {
                pos.add(v); // меняем позицию картинки
            }else pos.set(posTouch);
        }else pos.add(v);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        flag = true;
        posTouch.set(screenX, Gdx.graphics.getHeight()-screenY); // вектор posTouch содержит координаты точки касания
        v.set(posTouch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        flag = false;
        v.setLength(0f); // обнулили
        if (keycode == Input.Keys.UP){
            v.set(0, V_LEN);
        }
        if (keycode == Input.Keys.DOWN){
            v.set(0, -V_LEN);
        }
        if (keycode == Input.Keys.LEFT){
            v.set(-V_LEN, 0);
        }
        if (keycode == Input.Keys.RIGHT){
            v.set(V_LEN, 0);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        super.keyUp(keycode);
        v.set(0, 0); // обнуляем вектор скорости
        return false;
    }
}
