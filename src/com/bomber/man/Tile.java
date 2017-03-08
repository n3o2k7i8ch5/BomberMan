package com.bomber.man;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class Tile extends Entity {

    int x, y;
    Image image;

    static final String GRASS_PATH = "res/drawables/grass.png";

    public Tile(int x, int y, String image_string){
        this.x = x;
        this.y = y;

        ImageIcon ic = new ImageIcon(image_string);
        image = ic.getImage();
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(image,x,y,null);

    }
}
