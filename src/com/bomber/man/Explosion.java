package com.bomber.man;

import com.bomber.man.enemies.SmartAssEnemy;
import com.bomber.man.listeners.PlayerColisionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static com.bomber.man.GameFrame.*;
import static com.bomber.man.Object.direction.*;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Explosion extends Object {

    static final int LIFE_TIME = 500;
    int fire_length;
    direction direction;
    private int life_time;
    private int PROPAG_DELAY;

    boolean leaveRandPowerUp = false;

    public Explosion(GameFrame frame, int X, int Y, int fire_length, direction direction) {
        super(frame, X, Y);

        this.life_time = LIFE_TIME;
        this.fire_length = fire_length;
        this.direction = direction;

        if(direction == NULL)
            PROPAG_DELAY = 0;
        else
            PROPAG_DELAY = 30;

        addPlayerColisionListener(new PlayerColisionListener(frame) {
            @Override
            public void onColision(Iterator<Object> it) {
                player().reduceLife();
            }
        });
    }

    private void tryPropataingUp(){
        if(Y==0)
            return;

        Object solid = upSolid();

        if(solid==null) {
            getObjectManager().addExplosion(X, Y - 1, fire_length - 1, UP);
        }else if(solid.softSolid){
            getObjectManager().addExplosion(X, Y - 1, 0, UP);
        }
    }

    private void tryPropataingDown(){
        if(Y==getMain().ABS_H_MAP_SIZE-1)
            return;

        Object solid = downSolid();

        if(solid==null) {
            getObjectManager().addExplosion(X, Y + 1, fire_length - 1, DOWN);
        }else if(solid.softSolid){
            getObjectManager().addExplosion(X, Y + 1, 0, DOWN);
        }
    }

    private void tryPropataingRight() {
        if(X==getMain().ABS_W_MAP_SIZE-1)
            return;

        Object solid = rightSolid();

        if(solid==null) {
            getObjectManager().addExplosion(X + 1, Y, fire_length - 1, RIGHT);
        }else if(solid.softSolid){
            getObjectManager().addExplosion(X + 1, Y, 0, RIGHT);
        }
    }

    private void tryPropataingLeft() {
        if(X==0)
            return;

        Object solid = leftSolid();

        if(solid==null) {
            getObjectManager().addExplosion(X - 1, Y, fire_length - 1, LEFT);
        }else if(solid.softSolid){
            getObjectManager().addExplosion(X - 1, Y, 0, LEFT);
        }
    }

    private void tryPropagating(){

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

    public void tick(Iterator<Explosion> it){
        if(PROPAG_DELAY>=0) {

            if(PROPAG_DELAY==0)
                tryPropagating();

            PROPAG_DELAY -= FRAME_TIME;

            return;
        }else {
            checkNearbyCollisions();
            life_time -= FRAME_TIME;

            if (life_time == 0) {
                it.remove();
                getObjectManager().all_objects[X][Y].remove(this);
                getObjectManager().removeSolid(X, Y);

                for (SmartAssEnemy enemy : getObjectManager().smartass_enemy_list)
                    enemy.explosionRemoved(X, Y);

                if(leaveRandPowerUp)
                    randomPowerUp(X, Y);
            }
        }
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.explosionImages;
    }

    private void randomPowerUp(int X, int Y){
        Random random = new Random();
        int r = Math.abs(random.nextInt())%70;
        if(r==0)
            getObjectManager().addFlameUp(X, Y);
        else if(r==1)
            getObjectManager().addSpeedUp(X, Y);
        else if(r==2)
            getObjectManager().addBombUp(X, Y);
        else if(r==3)
            getObjectManager().addLifeUp(X, Y);
        else if(r==4) {
            getObjectManager().addShieldUp(X, Y);
        }else if(r==5) {
            getObjectManager().addThrowBombUp(X, Y);
        }else if(r==6){
            int r1 = Math.abs(random.nextInt())%4;
            if(r1==0) {
                getObjectManager().addSlowDown(X, Y);
            }else if(r1==1) {
                getObjectManager().addInstantBomb(X, Y);
            }else if(r1==2) {
                getObjectManager().addFlameDown(X, Y);
            }else if(r1==3)
                getObjectManager().addDifferentDirection(X,Y);
        }
    }

    public void checkNearbyCollisions(){

        for (Iterator<Object> it = getSurroundingObjects().iterator(); it.hasNext(); ) {
            Object object = it.next();
            if (object.explosionColisionListener != null)
                object.explosionColisionListener.checkColision(this, it);
        }
    }
}
