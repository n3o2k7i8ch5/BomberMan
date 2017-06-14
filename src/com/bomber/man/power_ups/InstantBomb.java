package com.bomber.man.power_ups;

import com.bomber.man.GameFrame;

import java.awt.*;
import java.util.ArrayList;

import static com.bomber.man.Object.direction.NULL;

/**
 * Created by Murspi on 30.03.2017.
 */

public class InstantBomb extends PowerUp {

    static final int INSTANT_BOMB_TIME = 15000;
    /**
     * PowerUp, który zwiększa maksymalną ilość bomb gracza do położenia.
     * @param frame okno w którym znajduje sie BombUp.
     * @param X pozycja obiektu BombUp na mapie, liczona w ilości kratek.
     * @param Y pozycja obiektu BombUp na mapie, liczona w ilości kratek.
     */
    public InstantBomb(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    /**
     *  Metoda zwiększająca maksymalną liczbę bomb gracza.
     */
    @Override
    public void performBonus() {
        frame.player.instantBomb(INSTANT_BOMB_TIME);
        getObjectManager().addBomb(frame.player.X, frame.player.Y, NULL);
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.curseUpImages;
    }
}
