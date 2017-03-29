package com.bomber.man.power_ups;

import com.bomber.man.GameFrame;
import com.bomber.man.Object;

/**
 * Created by Murspi on 22.03.2017.
 */
public abstract class PowerUp extends Object {

    public PowerUp(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    public abstract void performBonus();
}

