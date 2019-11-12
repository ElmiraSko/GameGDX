package com.ersk.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.ersk.base.BaseScreen;
import com.ersk.math.Rect;
import com.ersk.sprite.Background;
import com.ersk.sprite.Logo;


public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture bg;

    private Logo logo;
    private Background background;

    @Override
    public void show() {
        super.show();

        img = new Texture("badlogic.jpg");
        bg = new Texture("textures/Stars.png");

        logo = new Logo(new TextureRegion(img));
        logo.setHeightProportion(0.3f);
        background = new Background(new TextureRegion(bg));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta){
        logo.update(delta);
    }
    public void draw(){
        Gdx.gl.glClearColor(1, 1, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        bg.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        logo.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        logo.touchUp(touch, pointer);
        return false;
    }
}