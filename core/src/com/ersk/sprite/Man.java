package com.ersk.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ersk.base.Sprite;
import com.ersk.math.Rect;

public class Man extends Sprite {
    private Vector2 v = new Vector2(0f, -0.04f);

    public Man(TextureRegion region){
        super(region);
        setHeightProportion(0.05f);
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
    }

    public boolean isShipCollision(Rect ship) {
        return !(
                ship.getRight() < getLeft()
                        || ship.getLeft() > getRight()
                        || ship.getBottom() > getTop()
                        || ship.getTop() < pos.y
        );
    }
// дополнительные жизни
    public int bonusHP(int level){
        return level*5;
    }

}
