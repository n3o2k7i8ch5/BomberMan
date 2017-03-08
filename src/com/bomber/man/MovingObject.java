package com.bomber.man;

import java.awt.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class MovingObject extends Object {

    int old_x, old_y;
    int speed;
    enum direction{UP, DOWN, RIGHT, LEFT, NULL};
    direction direction;

    public MovingObject(int x, int y, Image image_set, int speed) {
        super(x, y, image_set);
        this.speed = speed;
    }

    public void onPositionChanged(){

    }
}
