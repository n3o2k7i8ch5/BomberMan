package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;


public class Bomb extends Solid {
    /**
     * @param destruct_time czas po jakim bomba zostanie zdetonowana.
     * @param fire_length długość wybuchu wyrażona w ilości kratek.
     */
    static final int BOMB_DESTRUCT_TIME = 3000;
    int destruct_time;
    int fire_length;

    /**
     * Klasa Bomb wybuchająca po określonym czasie i niszcząca wszystkie zniszczalne obiekty
     * @param gameFrame okno gry
     * @param X współrzędna X bomby na mapie, określająca ilość kratek.
     * @param Y współrzędna y bomby na mapie, określająca ilość kratek.
     * @param destruct_time czas po jakim bomba zostanie zdetonowana.
     * @param fire_length długość wybuchu wyrażona w ilości kratek.
     */
    public Bomb(GameFrame gameFrame, int X, int Y, int destruct_time, int fire_length) {
        super(gameFrame, X, Y, true);
        this.destruct_time = destruct_time;
        this.fire_length = fire_length;
    }

    /**
     * Klasa Bomb wybuchająca po określonym czasie i niszcząca wszystkie zniszczalne obiekty
     * @param gameFrame okno gry
     * @param X współrzędna X bomby na mapie, określająca ilość kratek.
     * @param Y współrzędna y bomby na mapie, określająca ilość kratek.
     * @param fire_length długość wybuchu wyrażona w ilości kratek.
     */
    public Bomb(GameFrame gameFrame, int X, int Y, int fire_length) {
        super(gameFrame, X, Y, true);
        this.destruct_time = BOMB_DESTRUCT_TIME;
        this.fire_length = fire_length;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.bombImages;
    }
}
