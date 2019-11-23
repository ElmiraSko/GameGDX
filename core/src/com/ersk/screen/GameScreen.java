package com.ersk.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.BaseScreen;
import com.ersk.math.Rect;
import com.ersk.pool.BulletPool;
import com.ersk.pool.EnemyPool;
import com.ersk.pool.ExplosionPool;
import com.ersk.sprite.Background;
import com.ersk.sprite.Bullet;
import com.ersk.sprite.Enemy;
import com.ersk.sprite.Message;
import com.ersk.sprite.SpaceShip;
import com.ersk.sprite.Star;
import com.ersk.utils.EnemyEmitter;

import java.util.List;


public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    public boolean mainShipDestroyed = false;

    private Texture bg;
    private TextureAtlas atlas;

    private Music music;

    private Background background;
    private Star[] stars;
    private SpaceShip ship;
    private Message message;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        ship = new SpaceShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        music.setLooping(true);
        music.play();
        message = new Message(new TextureAtlas("textures/mainAtlas.tpack"));
    }

    @Override
    public void render(float delta) {
        update(delta);  // обновились
        checkCollisions(); // проверка на столкновения
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
        message.resize(worldBounds);
    }
    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        music.dispose();
        ship.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        enemyEmitter.dispose();
       //=
        super.dispose();
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

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }

        ship.update(delta);
   //     if (!mainShipDestroyed){
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        if (!mainShipDestroyed){ // если гл корабль не уничтожен
            enemyEmitter.generate(delta);  // продолжаем генерировать врагов
        }
        message.update(delta);
    }

    private void checkCollisions() {  // проверка пересечений спрайтов
        List<Enemy> enemyList = enemyPool.getActiveObjects(); // список врагов
        List<Bullet> bulletList = bulletPool.getActiveObjects();  // список пуль
        for (Enemy enemy : enemyList) {  // для каждого врага

            float minDist = enemy.getHalfWidth() + ship.getHalfWidth(); // минимальная дистанция

            if (ship.pos.dst(enemy.pos) < minDist) {  // если корабли ближе чем дистанция, т.е. спрайты пересеклись
                ship.damage(enemy.getDamage());  //enemy.getDamage() - урон пули конкретного корабля врага отнимаем у ship
                enemy.destroy();  // корабль врага убит, вызываем взрыв и отправляем его в список не активных через пулл
                mainShipDestroyed = ship.isDestroyed();
            }

            for (Bullet bullet : bulletList) {  // спрайт пули из пула, спрайты пуль врагов тоже могут пересекаться
                if (bullet.getOwner() != ship) { // если пули не принадлежат ship
                    continue; // продолжаем, но если наоборот, то ...enemy.isBulletCollision(bullet)
                }
                if (enemy.isBulletCollision(bullet)) { // если пересеклись, то
                    enemy.damage(bullet.getDamage());  // вычитаем жизни
                    bullet.destroy(); // отправляем в список не активных через пулл
                }
            }
        } // конец блока врага
        for (Bullet bullet : bulletList) {   //
            if (bullet.getOwner() == ship) {  //
                continue;
            }
            if (ship.isBulletCollision(bullet)) {
                ship.damage(bullet.getDamage());
                bullet.destroy();
                mainShipDestroyed = ship.isDestroyed(); // получаем значение destroyed глав корабля
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!ship.isDestroyed()) { // если корабль не убит, то рисуем
            ship.draw(batch);
        }else message.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }
}
