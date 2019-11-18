package com.ersk.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.BaseScreen;
import com.ersk.math.Rect;
import com.ersk.pool.BulletPool;
import com.ersk.pool.EnemyPool;
import com.ersk.sprite.Background;
import com.ersk.sprite.SpaceShip;
import com.ersk.sprite.Star;


public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 30;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
    private SpaceShip ship;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Sound sound;
    private Music music;

    private final float reloadInterval = 3f; // промежуток между появлением
    private float reloadTimer = 0f; // счетчик


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/Stars.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.play(); // воспроизведение
        music.setVolume(1f);
        bulletPool = new BulletPool();

        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav")); // загрузка звука из файла
        ship = new SpaceShip(atlas, bulletPool, sound);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }

    private void update(float delta) {
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            enemyPool.obtain();
        }
        for (Star star : stars) {
            star.update(delta);
        }
        ship.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() { //  для отрисовки
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        ship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        ship.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        enemyPool = new EnemyPool(atlas, worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        ship.touchUp(touch, pointer);
        return false;
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        sound.dispose();
        music.dispose();
        super.dispose();
    }
}
