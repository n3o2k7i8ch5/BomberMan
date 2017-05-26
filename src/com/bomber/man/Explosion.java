package com.bomber.man;

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

        addPlayerColisionListener(new PlayerColisionListener(frame) {
            @Override
            public void onColision(Iterator<Object> it) {
                getMain().setGameState(-1);
            }
        });

        Bomb bomb = (Bomb) getObjectManager().hereObject(getObjectManager().bomb_list, this);
        if(bomb!=null)
            getObjectManager().detonate(bomb);

        //getObjectManager().enemy_list.removeIf(enemy -> enemy.X == X && enemy.Y == Y);
        getObjectManager().powerup_list.removeIf(powerUp -> powerUp.X == X && powerUp.Y == Y);
    }

    private void tryPropataingUp(){
        if(Y==0)
            return;

        Solid solid = upSolid();

        if(solid==null) {
            if(!getObjectManager().all_objects[X][Y-1].contains(Explosion.class))
                getObjectManager().addExplosion(X, Y - 1, fire_length - 1, UP);
        }else if(solid.isSoft){
            getObjectManager().addExplosion(X, Y - 1, 0, UP);
            getObjectManager().removeSolid(solid);
            randomPowerUp(solid.X, solid.Y);
        }
    }

    private void tryPropataingDown(){
        if(Y==getMain().ABS_H_MAP_SIZE-1)
            return;

        Solid solid = downSolid();

        if(solid==null) {
            if (!getObjectManager().all_objects[X][Y + 1].contains(Explosion.class))
                getObjectManager().addExplosion(X, Y + 1, fire_length - 1, DOWN);
        }else if(solid.isSoft){
            getObjectManager().addExplosion(X, Y + 1, 0, DOWN);
            getObjectManager().removeSolid(solid);
            randomPowerUp(solid.X, solid.Y);
        }
    }

    private void tryPropataingRight() {
        if(X==getMain().ABS_W_MAP_SIZE-1)
            return;

        Solid solid = rightSolid();

        if(solid==null) {
            if(!getObjectManager().all_objects[X+1][Y].contains(Explosion.class))
                getObjectManager().addExplosion(X + 1, Y, fire_length - 1, RIGHT);
        }else if(solid.isSoft){
            getObjectManager().addExplosion(X+1, Y, 0, RIGHT);
            getObjectManager().removeSolid(solid);
            randomPowerUp(solid.X, solid.Y);
        }
    }

    private void tryPropataingLeft() {
        if(X==0)
            return;

        Solid solid = leftSolid();

        if(solid==null) {
            if (!getObjectManager().all_objects[X - 1][Y].contains(Explosion.class))
                getObjectManager().addExplosion(X - 1, Y, fire_length - 1, LEFT);
        }else if(solid.isSoft){
            getObjectManager().addExplosion(X-1, Y, 0, LEFT);
            getObjectManager().removeSolid(solid);
            randomPowerUp(solid.X, solid.Y);
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
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.explosionImages;
    }

    private void randomPowerUp(int X, int Y){
        Random random = new Random();
        int r = random.nextInt()%10;
        if(r==0)
            getObjectManager().addFlameUp(X, Y);
        else if(r==1)
            getObjectManager().addSpeedUp(X, Y);
        else if(r==2)
            getObjectManager().addBombUp(X, Y);
    }

    public void checkNearbyCollisions(){

        if(X!=0 && Y!=0)
            for (Iterator<Object> it = getObjectManager().all_objects[X-1][Y-1].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }

        if(Y!=0)
            for (Iterator<Object> it = getObjectManager().all_objects[X][Y-1].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }

        if(X!=getMain().ABS_W_MAP_SIZE-1 && Y!=0)
            for (Iterator<Object> it = getObjectManager().all_objects[X+1][Y-1].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }

        if(X!=0)
            for (Iterator<Object> it = getObjectManager().all_objects[X-1][Y].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }

        for (Iterator<Object> it = getObjectManager().all_objects[X][Y].iterator(); it.hasNext(); ) {
            Object object = it.next();
            if (object.explosionColisionListener != null)
                object.explosionColisionListener.checkColision(this, it);
        }

        if(X!=getMain().ABS_W_MAP_SIZE-1)
            for (Iterator<Object> it = getObjectManager().all_objects[X+1][Y].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }

        if(X!=0 && Y!=getMain().ABS_H_MAP_SIZE-1)
            for (Iterator<Object> it = getObjectManager().all_objects[X-1][Y+1].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }

        if(Y!=getMain().ABS_H_MAP_SIZE-1)
            for (Iterator<Object> it = getObjectManager().all_objects[X][Y+1].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }

        if(X!=getMain().ABS_W_MAP_SIZE-1 && Y!=getMain().ABS_H_MAP_SIZE-1)
            for (Iterator<Object> it = getObjectManager().all_objects[X+1][Y+1].iterator(); it.hasNext(); ) {
                Object object = it.next();
                if (object.explosionColisionListener != null)
                    object.explosionColisionListener.checkColision(this, it);
            }
    }
}
