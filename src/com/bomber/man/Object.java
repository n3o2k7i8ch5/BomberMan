package com.bomber.man;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class Object extends Entity{

    public int x, y;
    public int X, Y;
    public Image image;

    static final String GRASS_PATH = "res/drawables/grass.png";

    public Object(int X, int Y, String image_string){
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;

        ImageIcon ic = new ImageIcon(image_string);
        image = ic.getImage();
    }

    @Override
    void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, null);
    }
}
