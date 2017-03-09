package com.bomber.man;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.*;

import static com.bomber.man.MovingObject.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class Player extends MovingObject {

    static final String PLAYER_PATH = "res/drawables/player.png";

    direction key1_pressed = NULL;
    direction key2_pressed = NULL;

    Player(int x, int y, String image_string, int speed, GameFrame gameFrame) {
        super(x, y, image_string, speed, gameFrame);
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP)
        {
            if(key1_pressed==NULL) {
                key1_pressed = UP;
                //new_direction=UP;
            }else if(key2_pressed==NULL && key1_pressed!=UP) {
                key2_pressed = UP;
                //new_direction=UP;
            }

        }
        else if(key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN)
        {
            if(key1_pressed==NULL) {
                key1_pressed = DOWN;
                //new_direction = DOWN;
            }else if(key2_pressed==NULL  && key1_pressed!=DOWN) {
                key2_pressed = DOWN;
                //new_direction = DOWN;
            }
        }
        else if(key==KeyEvent.VK_A || key==KeyEvent.VK_LEFT)
        {
            if(key1_pressed==NULL) {
                key1_pressed = LEFT;
                //new_direction=LEFT;
            }else if(key2_pressed==NULL  && key1_pressed!=LEFT) {
                key2_pressed = LEFT;
                //new_direction=LEFT;
            }

        }
        else if(key==KeyEvent.VK_D || key==KeyEvent.VK_RIGHT) {
            if (key1_pressed == NULL){
                key1_pressed = RIGHT;
                //new_direction=RIGHT;
            }else if(key2_pressed==NULL && key1_pressed!=RIGHT) {
                key2_pressed = RIGHT;
                //new_direction=RIGHT;
            }
        }
    }

    public void keyRealised(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP)
        {
            if(key1_pressed==UP) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==UP)
                key2_pressed = NULL;
        }
        else if(key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN)
        {
            if(key1_pressed==DOWN) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==DOWN)
                key2_pressed = NULL;
        }
        else if(key==KeyEvent.VK_A || key==KeyEvent.VK_LEFT)
        {
            if(key1_pressed==LEFT) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==LEFT)
                key2_pressed = NULL;
        }
        else if(key==KeyEvent.VK_D || key==KeyEvent.VK_RIGHT)
        {
            if(key1_pressed==RIGHT) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==RIGHT)
                key2_pressed = NULL;
        }

        //new_direction = key1_pressed;

    }

    @Override
    void update() {

        if(key2_pressed!=NULL && isDirectionFreeToGo(key2_pressed))
            new_direction = key2_pressed;
        else if(key1_pressed!=NULL && isDirectionFreeToGo(key1_pressed))
            new_direction = key1_pressed;
        else
            new_direction = NULL;

        if(x % (Main.RESOLUTION/4) == 0 && y % (Main.RESOLUTION/4) == 0)
            if(new_direction != current_direction)
                current_direction = new_direction;

        if(current_direction==UP)
            y -= speed;
        else if (current_direction==DOWN)
            y += speed;
        else if(current_direction==LEFT)
            x -= speed;
        else if (current_direction==RIGHT)
            x += speed;

        super.update();

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image,x,y,null);
    }
}