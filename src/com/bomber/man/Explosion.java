package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static com.bomber.man.GameFrame.*;
import static com.bomber.man.Object.direction.*;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Explosion extends Object {

    static final int LIFE_TIME = 500;
    int fire_length;
    direction direction;
    int life_time;
    int delay;

    public Explosion(GameFrame frame, int X, int Y, int fire_length, direction direction) {
        super(frame, X, Y);

        this.life_time = LIFE_TIME;
        this.fire_length = fire_length;
        this.direction = direction;

        if(direction == NULL)
            delay = 0;
        else
            delay = 30;

        if(frame.player.X == X && frame.player.Y == Y)
            getMain().setGameState(-1);

        if(bombs[X][Y] != null)
            frame.detonate(bombs[X][Y]);

        for (Iterator<Enemy> it = frame.enemy_list.iterator(); it.hasNext(); ) {
            Enemy enemy = it.next();
            if(enemy.X == X && enemy.Y == Y )
                it.remove();
        }
    }

    public void tryPropataingUp(){
        if(Y==0)
            return;

        if(frame.solids[X][Y-1] == null)
            frame.addExplosion(X, Y-1, fire_length-1, UP);
        else if(frame.solids[X][Y-1].isSoft) {
            frame.addExplosion(X, Y - 1, 0, UP);
            frame.removeSolid(frame.solids[X][Y-1]);
        }
    }

    public void tryPropataingDown(){
        if(Y==getMain().ABS_H_MAP_SIZE-1)
            return;

        if(frame.solids[X][Y+1] == null)
            frame.addExplosion(X, Y+1, fire_length-1, DOWN);
        else if(frame.solids[X][Y+1].isSoft) {
            frame.addExplosion(X, Y + 1, 0, DOWN);
            frame.removeSolid(frame.solids[X][Y+1]);
        }
    }

    public void tryPropataingRight() {
        if(X==getMain().ABS_W_MAP_SIZE-1)
            return;

        if(frame.solids[X+1][Y] == null)
            frame.addExplosion(X+1, Y, fire_length-1, RIGHT);
        else if(frame.solids[X+1][Y].isSoft) {
            frame.addExplosion(X+1, Y, 0, RIGHT);
            frame.removeSolid(frame.solids[X+1][Y]);
        }
    }

    public void tryPropataingLeft() {
        if(X==0)
            return;

        if(frame.solids[X-1][Y] == null)
            frame.addExplosion(X-1, Y, fire_length-1, LEFT);
        else if(frame.solids[X-1][Y].isSoft) {
            frame.addExplosion(X-1, Y, 0, LEFT);
            frame.removeSolid(frame.solids[X-1][Y]);
        }
    }

    public void tryPropagating(){

        if(fire_length==0)
            return;

        if (direction == NULL) {
            tryPropataingUp();
            tryPropataingDown();
            tryPropataingRight();
            tryPropataingLeft();

        }else if(direction == UP){
            tryPropataingUp();
        }else if(direction == DOWN){
            tryPropataingDown();
        }else if(direction == RIGHT){
            tryPropataingRight();
        }else if(direction == LEFT){
            tryPropataingLeft();
        }
    }


    public boolean tick(){
        if(delay==0)
            tryPropagating();
        if(delay>=0)
            delay-=frame_time;

        if(life_time == 0)
            return true;

        life_time -= frame_time;

        return false;
    }

    @Override
    protected ArrayList<Image> getImageList() {
        return getMain().graphicsContainer.explosionImages;
    }
}
