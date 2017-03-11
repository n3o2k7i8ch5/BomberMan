package com.bomber.man;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class Wall extends Object {

    static String WALL_PATH = "res/drawables/wall.png";

    public Wall(GameFrame frame, int x, int y) {
        super(frame, x, y, WALL_PATH);
    }
}
