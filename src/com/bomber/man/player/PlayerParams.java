package com.bomber.man.player;

/**
 * Created by Kisiel on 18.06.2017.
 */
public class PlayerParams {

    int points;
    double speed;
    int lives;
    int max_bombs;
    int bombs_left;
    int max_throw_bombs;
    int throw_bombs_left;
    int flame_length;

    public PlayerParams(double speed, int lives, int max_bombs, int max_throw_bombs, int flame_length){
        this.speed = speed;
        this.lives = lives;
        this.max_bombs = max_bombs;
        this.max_throw_bombs = max_throw_bombs;
        this.flame_length = flame_length;
    }
}
