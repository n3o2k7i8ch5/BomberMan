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

    static final int POINTS = 15;

    public RandomEnemy(GameFrame frame, int x, int y, int speed)
    {
        super(frame,x,y,speed, POINTS);
    }

    @Override
    public void updateStep(long time) {

        if(isAlignedX() && isAlignedY())
            new_dir = randomFreeDirection();

        super.updateStep(time);

    }

    @Override
    protected ArrayList<Image> getImageUpList() {
        return getMain().graphicsContainer.randomEnemyUpImages;
    }

    @Override
    protected ArrayList<Image> getImageDownList() {
        return getMain().graphicsContainer.randomEnemyDownImages;
    }

    @Override
    protected ArrayList<Image> getImageLeftList() {
        return getMain().graphicsContainer.randomEnemyLeftImages;
    }

    @Override
    protected ArrayList<Image> getImageRightList() {
        return getMain().graphicsContainer.randomEnemyRightImages;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.randomEnemyUpImages;
    }


}
