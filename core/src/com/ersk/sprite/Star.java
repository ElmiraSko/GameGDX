package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;
import com.ersk.math.Rnd;

public class Star extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {                //конструктор спрайта Star
        super(atlas.findRegion("star"));          //atlas - атлас, откуда берется изображение
        setHeightProportion(Rnd.nextFloat(0.01f, 0.0065f));  // высота звезды-спрайта
        float vy = Rnd.nextFloat(-0.005f, -0.001f);           // координата по х
        float vx = Rnd.nextFloat(-0.0005f, 0.0005f);        // координата по у
        v.set(vx, vy);                                        // скорость передвижения
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());  // вычисляем рандомно координаты
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);                // разместили центр спрайта в точке (posX, posY), возможно, здесь он появляется для дальнейшего движения
    }

    @Override
    public void update(float delta) {  // обновление свойств спрайта
        pos.add(v); // позиция спрайта
        checkBounds(); // метод проверяет пересечение с границей экрана
    }

    private void checkBounds() {  // если спрайт вышел за границу экрана
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}