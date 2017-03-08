package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.*;

import static com.bomber.man.MovingObject.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class Player extends MovingObject {

    static final String PLAYER_PATH = "res/drawables/player.png";

    Player(int x, int y, Image image_set, int speed) {
        super(x, y, image_set, speed);

        this.x = x;
        this.y = y;

        ImageIcon ic = new ImageIcon(PLAYER_PATH);
        this.image_set = ic.getImage();

    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP)
        {
            if(direction!=LEFT && direction!=RIGHT)
                direction=UP;
        }
        else if(key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN)
        {
            if(direction!=LEFT && direction!=RIGHT)
                direction=DOWN;
        }
        else if(key==KeyEvent.VK_A || key==KeyEvent.VK_LEFT)
        {
            if(direction!=UP && direction!=DOWN)
                direction=LEFT;
        }
        else if(key==KeyEvent.VK_D || key==KeyEvent.VK_RIGHT)
        {
            if(direction!=UP && direction!=DOWN)
                direction=RIGHT;
        }
    }
/*
    public void keyRealised(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP ||
                key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN ||
                key==KeyEvent.VK_A || key==KeyEvent.VK_LEFT ||
                key==KeyEvent.VK_D || key==KeyEvent.VK_RIGHT)
            direction=NULL;
    }
*/
    @Override
    public void onPositionChanged() {
        super.onPositionChanged();
        if(x % Main.RESOLUTION == 0 && y % Main.RESOLUTION == 0)
            direction = NULL;

    }

    @Override
    void update() {
        super.update();

        if(direction==UP)
            y -= speed;
        else if (direction==DOWN)
            y += speed;
        if(direction==LEFT)
            x -= speed;
        else if (direction==RIGHT)
            x += speed;

    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(image_set,x,y,null);

    }
}
