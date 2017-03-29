package com.bomber.man;

import java.awt.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public abstract class Entity {

    void handleEvents(Event e){}

    public void update(long time){}

    public void draw(Graphics2D g2d) {}
}
