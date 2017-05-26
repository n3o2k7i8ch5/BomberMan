package com.bomber.man.enemies;


import com.bomber.man.GameFrame;
import com.bomber.man.MovingObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import static com.bomber.man.Object.direction.*;
/**
 * Created by Murspi on 21.03.2017.
 */
public class StraightEnemy extends Enemy {

    static final int SPEED = 1;

    public StraightEnemy(GameFrame frame, int X, int Y) {
        super(frame, X, Y, SPEED);
    }

    protected StraightEnemy(GameFrame frame, int X, int Y, int speed) {
        super(frame, X, Y, speed);
    }

    @Override
    public void updateStep(long time) {

        if(current_dir==NULL)
            new_dir = randomFreeDirection();

        else if (!isDirFreeToGo(dirLeftTo(current_dir)) &&
                !isDirFreeToGo(dirRightTo(current_dir)) &&
                !isDirFreeToGo(current_dir)){

            if(isDirFreeToGo(dirReverse(current_dir)))
                new_dir = dirReverse(current_dir);
            else
                new_dir = NULL;
        }else {

            ArrayList<direction> free_dirs = new ArrayList<>();
            if(isDirFreeToGo(current_dir))
                free_dirs.add(current_dir);

            if(isDirFreeToGo(dirRightTo(current_dir)))
                free_dirs.add(dirRightTo(current_dir));

            if(isDirFreeToGo(dirLeftTo(current_dir)))
                free_dirs.add(dirLeftTo(current_dir));

            if(free_dirs.size()==0)
                new_dir = NULL;
            else{
                int r = Math.abs(new Random().nextInt() % free_dirs.size());
                new_dir = free_dirs.get(r);
            }
        }

        if(frame.player.X == X && frame.player.Y == Y)
            getMain().setGameState(-1);

        super.updateStep(time);
    }

    @Override
    protected ArrayList<Image> getImageUpList() {
        return getMain().graphicsContainer.straightEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageDownList() {
        return getMain().graphicsContainer.straightEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageLeftList() {
        return getMain().graphicsContainer.straightEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageRightList() {
        return getMain().graphicsContainer.straightEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.straightEnemyImages;
    }

}
