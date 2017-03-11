package com.bomber.man;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Bomb extends Solid {

    static final String BOMB_PATH = "res/drawables/bomb.png";
    static final int BOMB_DESTRUCT_TIME = 4000;
    int destruct_time;
    int fire_length;

    public Bomb(GameFrame gameFrame, int X, int Y, int destruct_time, int fire_length) {
        super(gameFrame, X, Y, BOMB_PATH, true);
        this.destruct_time = destruct_time;
        this.fire_length = fire_length;
    }

    public Bomb(GameFrame gameFrame, int X, int Y, int fire_length) {
        super(gameFrame, X, Y, BOMB_PATH, true);
        this.destruct_time = BOMB_DESTRUCT_TIME;
        this.fire_length = fire_length;
    }
}
