package com.bomber.man.enemies;

import com.bomber.man.GameFrame;
import com.bomber.man.MovingObject;
import com.bomber.man.Object;
import com.bomber.man.PlayerColisionListener;

import java.util.Iterator;

/**
 * Created by Kisiel on 28.03.2017.
 */
public abstract class Enemy extends MovingObject {
    public Enemy(GameFrame frame, int X, int Y, int speed) {
        super(frame, X, Y, speed);
        addPlayerColisionListener(new PlayerColisionListener(frame) {

            @Override
            public void onColision(Iterator<Object> it) {
                getMain().setGameState(-1);
            }
        });
    }
}