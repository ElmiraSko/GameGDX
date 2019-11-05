package com.ersk.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img;
    private Vector2 pos;
    private Vector2 v1, v2;
    float x, y, dis;
    int count;

    @Override
    public void show() {
        super.show();
        img = new Texture("orange.png");
        pos = new Vector2();
        v1 = new Vector2(); // вектор скорости для touchDown
        v2 = new Vector2(); // вектор скорости для стрелок
        x = 0; // вспомогательные переменные
        y = 0;
        dis = 0;
        count = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        // проверка, что бы не выходить за границу экрана
        if (pos.x >= 0 && (pos.x + img.getWidth()) <= Gdx.graphics.getWidth() &&
                pos.y >= 0 && (pos.y + img.getHeight()) <= Gdx.graphics.getHeight()){

            // движение вызываемое нажатием на экран, (по левому нижнему углу)
            if (count < dis) {
                pos.add(v1); // меняем позицию картинки
                count++; // считаем шаги или сколько раз должны прибавить v, чтобы изменить позицию картинки

                if (pos.x < 0 || (pos.x + img.getWidth()) > Gdx.graphics.getWidth() ||
                        pos.y < 0 || (pos.y + img.getHeight()) > Gdx.graphics.getHeight()){
                    pos.sub(v1);
                    count = (int)dis; // если вышли за границы экрана, прекращаем движение. (Появляется погрешность в координатах)
                }
            }

            // движение при помощи стрелок
            pos.add(v2);
            if (pos.x < 0 || (pos.x + img.getWidth()) > Gdx.graphics.getWidth() ||
                    pos.y < 0 || (pos.y + img.getHeight()) > Gdx.graphics.getHeight()){
                pos.sub(v2);
            }
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        count = 0; // обнуляем счетчик шагов
        v1.set(0, 0);
        x = screenX-pos.x;  // разность координат по х
        y = (Gdx.graphics.getHeight()-screenY)-pos.y;  // разность координат по у
        if (x < (Gdx.graphics.getWidth()-img.getWidth()) && y < (Gdx.graphics.getHeight()-img.getHeight())){
            dis = (float) Math.sqrt(x*x + y*y); // расстояние
            v1.set(x/dis, y/dis);  // задаем скорость движения
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        v1.set(0, 0); // обнуляем вектор скорости для touchDown
        if (keycode == Input.Keys.UP){
            if ((pos.y+img.getHeight()) < Gdx.graphics.getHeight()) {
                v2.set(0, 2f);
            }
        }
        if (keycode == Input.Keys.DOWN){
            if (pos.y >= 0) {
                v2.set(0, -2f);
            }
        }
        if (keycode == Input.Keys.LEFT){
            if (pos.x >= 0) {
                v2.set(-2f, 0);
            }
        }
        if (keycode == Input.Keys.RIGHT){
            if ((pos.x + img.getWidth()) <= Gdx.graphics.getWidth()){
                v2.set(2f, 0);
            }
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println(pos);
        v2.set(0,0);

        return super.keyUp(keycode);
    }
}
