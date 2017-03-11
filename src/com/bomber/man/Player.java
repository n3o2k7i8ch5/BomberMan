package com.bomber.man;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.*;

import static com.bomber.man.Main.CENTER_MAP;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.MovingObject.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class Player extends MovingObject {

    boolean move_player_x, move_player_y;

    static final String PLAYER_PATH = "res/drawables/player.png";

    private direction key1_pressed = NULL;
    private direction key2_pressed = NULL;

    Player(GameFrame frame, int x, int y, String image_string, double speed, int align_factor) {
        super(frame, x, y, image_string, speed, align_factor);
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP)
        {
            if(key1_pressed==NULL)
                key1_pressed = UP;
            else if(key2_pressed==NULL && key1_pressed!=UP)
                key2_pressed = UP;
        }
        else if(key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN)
        {
            if(key1_pressed==NULL)
                key1_pressed = DOWN;
            else if(key2_pressed==NULL  && key1_pressed!=DOWN)
                key2_pressed = DOWN;
        }
        else if(key==KeyEvent.VK_A || key==KeyEvent.VK_LEFT)
        {
            if(key1_pressed==NULL)
                key1_pressed = LEFT;
            else if(key2_pressed==NULL  && key1_pressed!=LEFT)
                key2_pressed = LEFT;
        }
        else if(key==KeyEvent.VK_D || key==KeyEvent.VK_RIGHT) {
            if (key1_pressed == NULL)
                key1_pressed = RIGHT;
            else if(key2_pressed==NULL && key1_pressed!=RIGHT)
                key2_pressed = RIGHT;
        }
        else if(key==KeyEvent.VK_SPACE){
            putBomb();

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

    }

    @Override
    void update() {

        if(key2_pressed==NULL){
            if(isDirectionFreeToGo(key1_pressed))
                new_direction = key1_pressed;
            else
                new_direction = NULL;
        }
        else
        {
            if(current_direction!=key2_pressed) {
                if (isDirectionFreeToGo(key2_pressed))
                    new_direction = key2_pressed;
                else if (isDirectionFreeToGo(key1_pressed))
                    new_direction = key1_pressed;
                else
                    new_direction = NULL;
            }else if(current_direction!=key1_pressed) {
                if (isDirectionFreeToGo(key1_pressed))
                    new_direction = key1_pressed;
                else if (isDirectionFreeToGo(key2_pressed))
                    new_direction = key2_pressed;
                else
                    new_direction = NULL;
            }
        }

        if(X + Main.CENTER_MAP < getMain().ABS_MAP_SIZE-1 && X >= Main.CENTER_MAP) {
            move_player_x = false;
            frame.x_map_shift = (int)x - CENTER_MAP*RESOLUTION;
        }else
            move_player_x = true;

        if(Y + Main.CENTER_MAP < getMain().ABS_MAP_SIZE-1 && Y >= Main.CENTER_MAP) {
            move_player_y = false;
            frame.y_map_shift = (int)y - CENTER_MAP*RESOLUTION;
        }else
            move_player_y = true;

        super.update();

    }

    public void putBomb(){

        int X, Y;

        if(x%getMain().RESOLUTION<getMain().RESOLUTION/2)
            X = this.X;
        else
            X = this.X + 1;

        if(y%getMain().RESOLUTION<getMain().RESOLUTION/2)
            Y = this.Y;
        else
            Y = this.Y + 1;

        frame.addBomb(X, Y);
    }
}