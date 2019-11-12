package com.ersk.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.BaseScreen;
import com.ersk.math.Rect;
import com.ersk.sprite.Background;
import com.ersk.sprite.SpaceShip;
import com.ersk.sprite.Star;


public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 30;

    private Texture bg;
    private SpaceShip ship;
    private TextureAtlas atlas;
    private Star[] stars;


    private Background background;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/Stars.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        // создаем спрайт корабля (указываем, что 2 изображения)
        ship = new SpaceShip(atlas, 2);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas); // создание звезд
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void draw() { //  для отрисовки
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch); // рисуем фон
        ship.draw(batch, 0);       // рисуем 1-й корабль
        for (Star star : stars) {         // рисуем звезды
            star.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {  //
        background.resize(worldBounds);
        ship.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    private void update(float delta) {  // обновление
        for (Star star : stars) {
            star.update(delta);
        }
    }
    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }


    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

}

