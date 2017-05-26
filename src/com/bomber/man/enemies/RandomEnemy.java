package com.bomber.man.enemies;

import com.bomber.man.GameFrame;
import com.bomber.man.MovingObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.bomber.man.Object.direction.*;

/**
 * Created by Murspi on 10.03.2017.
 */
public class RandomEnemy extends Enemy {

    public RandomEnemy(GameFrame frame, int x, int y, int speed)
    {
        super(frame,x,y,speed);
    }

    @Override
    public void update(long time) {

        if(isAlignedX() && isAlignedY())
            new_dir = randomFreeDirection();

        super.update(time);

    }

    @Override
    protected ArrayList<Image> getImageUpList() {
        return getMain().graphicsContainer.enemyImages;
    }

    @Override
    protected ArrayList<Image> getImageDownList() {
        return getMain().graphicsContainer.enemyImages;
    }

    @Override
    protected ArrayList<Image> getImageLeftList() {
        return getMain().graphicsContainer.enemyImages;
    }

    @Override
    protected ArrayList<Image> getImageRightList() {
        return getMain().graphicsContainer.enemyImages;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.enemyImages;
    }


}
