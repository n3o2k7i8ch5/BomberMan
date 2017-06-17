package com.bomber.man.enemies;

import com.bomber.man.*;
import com.bomber.man.Object;
import com.bomber.man.listeners.ExplosionColisionListener;
import com.bomber.man.listeners.PlayerColisionListener;

import java.util.Iterator;

/**
 * Created by Kisiel on 28.03.2017.
 */
public abstract class Enemy extends MovingObject {

    int points;

    public Enemy(GameFrame frame, int X, int Y, double speed, int points) {
        super(frame, X, Y, speed);
        this.points = points;

        Enemy enemy = this;

        addPlayerColisionListener(new PlayerColisionListener(frame) {

            @Override
            public void onColision(Iterator<Object> it)
            {
                player().reduceLife();
            }
        });

        addExplosionColisionListener(new ExplosionColisionListener() {
            @Override
            public void onColision(Explosion explosion, Iterator<Object> it) {
                getObjectManager().removeEnemy(enemy);
                getMain().infoBox.points += points;
            }
        });
    }
}