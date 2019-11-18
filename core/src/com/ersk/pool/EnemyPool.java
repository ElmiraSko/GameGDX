package com.ersk.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.ersk.base.SpritesPool;
import com.ersk.math.Rect;
import com.ersk.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private TextureAtlas atlas;
    private Rect worldBounds;
int count=0;
    public EnemyPool(TextureAtlas atlas, Rect worldBounds){ //пулл кораблей
         this.atlas = atlas;
         this.worldBounds = worldBounds;
     }


    @Override
    protected Enemy newObject() {
        count++;
        System.out.println("Кораблей стало " + count);
        return new Enemy(atlas, worldBounds);
    }

}
