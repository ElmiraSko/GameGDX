package com.ersk.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> { // пул объектов

    protected final List<T> activeObjects = new ArrayList<>(); // активные

    protected final List<T> freeObjects = new ArrayList<>(); // неактивные объекты

    protected abstract T newObject(); // создание объекта типа Т

    public T obtain() {  // вытаскиваем объект из пула
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        System.out.println("active/free : " + activeObjects.size() + "/" + freeObjects.size());
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (Sprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.update(delta);
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (Sprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.draw(batch);
            }
        }
    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()) {
                activeObjects.remove(sprite);
                freeObjects.add(sprite);
                i--;
                sprite.flushDestroy();
                System.out.println("active/free : " + activeObjects.size() + "/" + freeObjects.size());
            }
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }
}

