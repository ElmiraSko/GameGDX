package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class SpaceShip extends Sprite {

    private Vector2 v = new Vector2(); // вектор скорости
    private Rect worldBounds;

    private TextureRegion[] textureRegions;


    public SpaceShip(TextureAtlas atlas, int count) { //конструктор спрайта корабля
        super(atlas.findRegion("main_ship")); // atlas - атлас, откуда берется изображение
        setHeightProportion(0.2f);                 // высота спрайта
        getShipRegions(regions[frame], count);  // получаем массив из count картинок из полученного TextureRegion
    }

    private TextureRegion[] getShipRegions(TextureRegion region, int count){
        textureRegions = new TextureRegion[count];
        if (count >= 1){
            for (int i = 0; i < count; i++)
                textureRegions[i] = new TextureRegion(region, i*region.getRegionWidth()/2, 0, region.getRegionWidth()/2, region.getRegionHeight());
        }
        return textureRegions;
    }
    private TextureRegion getShipForIndex(int index){ // получение корабля по его индексу из массива
        return textureRegions[index];
    }

    public void draw(SpriteBatch batch, int index) {  // дополнительно указываем, какой карабль нужно отобразить
        batch.draw(
                getShipForIndex(index), // текстура корабля
                getLeft(), getBottom(),   // левый нижний угол ?
                halfWidth, halfHeight,
                getWidth()/2, getHeight(),     //  ширина и высота спрайта
                scale, scale,
                angle
        );
    }

    @Override
    public void resize(Rect worldBounds) { // ?
        this.worldBounds = worldBounds;  // задади ?
        pos.set(0.07f, worldBounds.getBottom() + 0.15f);
    }

    @Override
    public void update(float delta) {  // обновление свойств спрайта
        pos.add(v); // позиция спрайта, его центр, изменяем его значения прибавлением вектора v
        checkBounds(); // метод проверяет пересечение с границей экрана
    }

    private void checkBounds() {  // если спрайт вышел за границу экрана
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }
}
