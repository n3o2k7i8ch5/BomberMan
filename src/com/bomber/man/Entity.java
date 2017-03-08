package com.bomber.man;

import java.awt.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public abstract class Entity {

    void handleEvents(Event e){}

    void update(){}

    public void draw(Graphics2D g2d) {}
}
