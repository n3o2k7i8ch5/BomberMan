package com.bomber.man;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Bomb extends Object {

    static final String PLAYER_PATH = "res/drawables/player.png";

    public Bomb(GameFrame gameFrame, int X, int Y) {
        super(gameFrame, X, Y, PLAYER_PATH);
    }
}
