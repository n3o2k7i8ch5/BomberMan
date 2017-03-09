package com.bomber.man;

import java.awt.*;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class Wall extends Object {

    static String WALL_PATH = "res/drawables/wall.png";

    public Wall(int x, int y) {
        super(x, y, WALL_PATH);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image,x,y,null);
    }
}
