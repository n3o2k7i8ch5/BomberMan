package com.bomber.man;

/**
 * Created by Kisiel on 20.03.2017.
 */
public class Tile extends Object {

    static final String GRASS_PATH = "res/drawables/grass.png";
    static final String GRASS_LIGHT_PATH = "res/drawables/grass_light.png";

    public Tile(GameFrame frame, int X, int Y, String image_string) {
        super(frame, X, Y, image_string);
    }

}
