package com.bomber.man.power_ups;

import com.bomber.man.GameFrame;

import java.awt.*;
import java.util.ArrayList;

public class SpeedUp extends PowerUp {

    /**
     * PowerUp, który zwiększa prędkość gracza.
     * @param frame okno w jakm znajduje się SpeedUp.
     * @param X pozycja na mapie, liczona w ilości kratek.
     * @param Y pozycja na mapie, liczona w ilości kratek.
     */
    public SpeedUp(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    /**
     * Metoda zwiększająca prędkość gracza.
     */
    @Override
    public void performBonus() {
        frame.player.increaseSpeed();
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.powerUpSpeedPath;
    }
}
