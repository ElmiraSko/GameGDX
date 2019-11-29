package com.ersk.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.ersk.base.BaseScreen;
import com.ersk.base.Font;
import com.ersk.math.Rect;
import com.ersk.pool.BulletPool;
import com.ersk.pool.EnemyPool;
import com.ersk.pool.ExplosionPool;
import com.ersk.pool.ManPool;
import com.ersk.sprite.Background;
import com.ersk.sprite.Bullet;
import com.ersk.sprite.ButtonNewGame;
import com.ersk.sprite.Enemy;
import com.ersk.sprite.Man;
import com.ersk.sprite.MessageGameOver;
import com.ersk.sprite.Pause;
import com.ersk.sprite.SpaceShip;
import com.ersk.sprite.Star;
import com.ersk.utils.EnemyEmitter;

import java.util.List;


public class GameScreen extends BaseScreen {
    private Game game;

    private static final int STAR_COUNT = 64;
    private static final String SCORE = "Score: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Texture bg;
    private TextureAtlas atlas;
    private Texture manTexture;
    private Texture pauseTexture;


    private Music music;
    private Font font;

    private StringBuilder sbScore;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private int score;
    private int prevLevel = 1;
    private boolean secondPressed = false; // повторное нажатие на клавишу Keys.SPACE

    private Background background;
    private Star[] stars;
    private SpaceShip ship;
    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;
    private Pause pause;


    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private ManPool manPool;

    private EnemyEmitter enemyEmitter;

    private enum State { PLAYING, PAUSE, GAME_OVER }
    private State state;
    private State prevState;

    public GameScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        bg = new Texture("textures/Stars.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        manTexture = new Texture("textures/man.png");
        pauseTexture = new Texture("textures/pause.png");

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        manPool = new ManPool(new TextureRegion(manTexture)); // пул вражеских космонавтов
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool, manPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        ship = new SpaceShip(atlas, bulletPool, explosionPool);

        music.setLooping(true);
        music.play();

        font = new Font("fonts/font.fnt", "fonts/font.png");
        font.setFontSize(0.025f);
        sbScore = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();

        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        pause = new Pause(new TextureRegion(pauseTexture), this);

        state = State.PLAYING;
        prevState = State.PLAYING;

        ship.bindButton(pause); // передали кнопку главному кораблю
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
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
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        pause.resize(worldBounds); //

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
        manPool.dispose();
        pauseTexture.dispose();
        super.dispose();
    }

    public void startNewGame(){
        state = State.PLAYING;
        prevLevel = 1;
        score = 0;
        ship.startNewGame(worldBounds);

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        manPool.freeAllActiveSprites();
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (state != State.GAME_OVER){
                if(!secondPressed){ // пауза, если Keys.SPACE
                    pause();
                    secondPressed = true;
                } else {
                    resume();
                    secondPressed = false;
                }
            }
        } else {
            ship.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode != Input.Keys.SPACE) {
            ship.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            ship.touchDown(touch, pointer);
            pause.touchDown(touch, pointer);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer);
        } else if (state == State.PAUSE) {
            pause.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            ship.touchUp(touch, pointer);
            pause.touchUp(touch, pointer);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer);
        } else if (state == State.PAUSE) {
            pause.touchUp(touch, pointer);
        }
        return false;
    }

    @Override
    public void pause() {
        prevState = state;
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        state = prevState;
        music.play();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        if (state != State.PAUSE) {
            explosionPool.updateActiveSprites(delta);
            if (state == State.PLAYING){
                ship.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemyEmitter.generate(delta, score);
                manPool.updateActiveSprites(delta);
            }
        }
        if (prevLevel < enemyEmitter.getLevel()){ // если изменился уровень с 1 на ...
            prevLevel = enemyEmitter.getLevel();
            ship.setHp(ship.getHp() + 10);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING){
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<Man> manList = manPool.getActiveObjects();

        for (Enemy enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + ship.getHalfWidth();
            if (ship.pos.dst(enemy.pos) < minDist) {
                ship.damage(enemy.getDamage());
                enemy.destroy();
                score++;

                if (ship.isDestroyed()){
                    state = State.GAME_OVER;
                }
            }

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != ship) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    if (enemy.isDestroyed()){
                        score++;
                    }
                    bullet.destroy();
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == ship) {
                continue;
            }
            if (ship.isBulletCollision(bullet)) {
                ship.damage(bullet.getDamage());
                bullet.destroy();
                if (ship.isDestroyed()){
                    state = State.GAME_OVER;
                }
            }
        }

        for (Man man: manList) { // подбираем космонавта, получаем бонус
            if (man.isShipCollision(ship)){
                ship.setHp(ship.getHp() + man.bonusHP(enemyEmitter.getLevel()));
                man.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        manPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING || state == State.PAUSE) {
            ship.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            manPool.drawActiveSprites(batch);
            pause.draw(batch);
        } else if (state == State.GAME_OVER){
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
            }
        printInfo();
        batch.end();
    }

    private void printInfo(){
        float posY = worldBounds.getTop()- 0.01f;

        float ScorePosX = worldBounds.getLeft() + 0.01f;
        sbScore.setLength(0);
        font.draw(batch, sbScore.append(SCORE).append(score), ScorePosX, posY);

        float HpPosX = worldBounds.pos.x;
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(ship.getHp()), HpPosX, posY, Align.center);

        float LevelPosX = worldBounds.getRight() - 0.01f;
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), LevelPosX, posY, Align.right);
    }

}