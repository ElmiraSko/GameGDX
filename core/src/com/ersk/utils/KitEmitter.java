package com.ersk.utils;

import com.ersk.pool.ManPool;

public class KitEmitter { // генератор
    private int level;  // ссылка на уровень

    private ManPool kitPool;

    private float generateInterval = 10f;
    private float generateTimer;

    public KitEmitter(ManPool kitPool, int level){ // генератор знает уровни
        this.kitPool = kitPool;
        this.level = level;
    }
// генерация комплекта в зависимости от delta и level
    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer > generateInterval) {  // если
            generateTimer = 0;
            kitPool.obtain();
        }
    }
}
