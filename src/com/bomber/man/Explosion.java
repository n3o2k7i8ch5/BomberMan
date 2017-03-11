package com.bomber.man;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.bomber.man.GameFrame.*;
import static com.bomber.man.Object.direction.*;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Explosion extends Object implements ActionListener {

    static final String EXPLOSION_PATH = "res/drawables/explosion.png";
    static final int LIFE_TIME = 500;
    int fire_length;
    direction direction;
    int life_time;
    int delay;

    private javax.swing.Timer timer;

    public Explosion(GameFrame frame, int X, int Y, int fire_length, direction direction) {
        super(frame, X, Y, EXPLOSION_PATH);

        this.life_time = LIFE_TIME;
        this.fire_length = fire_length;
        this.direction = direction;

        if(direction == NULL)
            delay = 0;
        else
            delay = 30;

        if(fire_length!=0) {
            timer = new javax.swing.Timer(delay, this::actionPerformed);
            timer.setRepeats(false);
            timer.start();
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
        if(Y==getMain().ABS_MAP_SIZE-1)
            return;

        if(frame.solids[X][Y+1] == null)
            frame.addExplosion(X, Y+1, fire_length-1, DOWN);
        else if(frame.solids[X][Y+1].isSoft) {
            frame.addExplosion(X, Y + 1, 0, DOWN);
            frame.removeSolid(frame.solids[X][Y+1]);
        }
    }

    public void tryPropataingRight() {
        if(X==getMain().ABS_MAP_SIZE-1)
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

    @Override
    public void actionPerformed(ActionEvent e) {

        if(bombs[X][Y] != null)
            frame.detonate(bombs[X][Y]);

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
}
