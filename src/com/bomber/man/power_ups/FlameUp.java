package com.bomber.man.power_ups;

import com.bomber.man.GameFrame;
import com.bomber.man.enemies.SmartAssEnemy;

import java.awt.*;
import java.util.ArrayList;

public class FlameUp extends PowerUp {

    /**
     * PowerUp, który zwiększa długość rażenia wybuchu bomby.
     * @param frame okno w którym znajduje sie BombUp.
     * @param X pozycja obiektu BombUp na mapie, określająca ilość kratek.
     * @param Y pozycja obiektu BombUp na mapie, określająca ilość kratek.
     */
    public FlameUp(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    /**
     * Metoda zwiększająca długość rażenia wybuchu bomby.
     */
    @Override
    public void performBonus() {
        frame.player.increaseFlame();
        for(SmartAssEnemy enemy : getObjectManager().smartass_enemy_list)
            enemy.checkSafety();
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.powerUpFlameImages;
    }
}
