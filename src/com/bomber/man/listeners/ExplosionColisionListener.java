package com.bomber.man.listeners;

import com.bomber.man.Explosion;
import com.bomber.man.Object;

import java.util.Iterator;

/**
 * Created by Kisiel on 17.04.2017.
 */
public abstract class ExplosionColisionListener {

    private Object object;

    public void checkColision(Explosion explosion, Iterator<Object> it){

        if(explosion.touches(object, 0.9))
            onColision(explosion, it);
    }

    public void assignToObject(Object object){
        this.object = object;
    }

    public abstract void onColision(Explosion explosion, Iterator<Object> it);
}
