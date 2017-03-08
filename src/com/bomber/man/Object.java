package com.bomber.man;

import java.awt.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class Object extends Entity{

    public int x, y;
    public Image image_set;

    public Object(int x, int y, Image image_set){
        this.x = x;
        this.y = y;
        this.image_set = image_set;
    }

    @Override
    void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image_set, x, y, null);
    }
}
