package com.bomber.man;

import java.util.Iterator;

/**
 * Created by Kisiel on 17.04.2017.
 */
public abstract class ExplosionColisionListener {

    private Object object;

    void checkColision(Explosion explosion, Iterator<Object> it){
        if(Math.abs(explosion.x - object.x) < Main.RESOLUTION && Math.abs(explosion.y - object.y) < Main.RESOLUTION)
            onColision(it);
    }

    void assignToObject(Object object){
        this.object = object;
    }

    public abstract void onColision(Iterator<Object> it);
}
