package com.bomber.man;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Wall extends Solid {

    static final String SOFT_WALL_PATH = "res/drawables/wall_soft.png";
    static String HARD_WALL_PATH = "res/drawables/wall.png";

    public Wall(GameFrame frame, int x, int y, boolean isSoft) {
        super(frame, x, y, isSoft?SOFT_WALL_PATH:HARD_WALL_PATH, isSoft);

    }
}
