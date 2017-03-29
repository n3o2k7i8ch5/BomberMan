package com.bomber.man.enemies;

import com.bomber.man.GameFrame;
import com.bomber.man.MovingObject;

/**
 * Created by Kisiel on 28.03.2017.
 */
public abstract class Enemy extends MovingObject {

    public Enemy(GameFrame frame, int X, int Y, int speed) {
        super(frame, X, Y, speed);
    }
}
