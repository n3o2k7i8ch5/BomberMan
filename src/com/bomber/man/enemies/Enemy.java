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

    public Enemy(GameFrame frame, int X, int Y, int speed) {
        super(frame, X, Y, speed);

        Enemy enemy = this;

        addPlayerColisionListener(new PlayerColisionListener(frame) {

            @Override
            public void onColision(Iterator<Object> it)
            {
                if(frame.player.lives>0)
                    frame.player.lives--;
                if(frame.player.lives == 0)
                    getMain().setGameState(-1);
            }
        });

        addExplosionColisionListener(new ExplosionColisionListener() {
            @Override
            public void onColision(Iterator<Object> it) {
                getObjectManager().removeEnemy(enemy);
            }
        });
    }
}