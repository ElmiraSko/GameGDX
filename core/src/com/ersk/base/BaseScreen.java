
package com.ersk.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;

    //  методы для событий
    @Override
    public void show() {
        batch = new SpriteBatch(); //
        Gdx.input.setInputProcessor(this); // задаем сам объект скрин как перехватчик событий
        System.out.println("show");
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) { // изменение размеров окна
        System.out.println("resize " + width + " , " + height);

    }

    @Override
    public void pause() { // сворачивание окна программы
        System.out.println("pause");

    }

    @Override
    public void resume() {  // разворачивание окна программы
        System.out.println("resume");
    }

    @Override
    public void hide() { // закрытие, выход из программы
        dispose();
        System.out.println("hide");
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }
    //=======
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp");
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        System.out.println("touchDown " + screenX + ", " + screenY);
//        System.out.println("touchDown " + screenX + ", " + (Gdx.graphics.getHeight()-screenY));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
