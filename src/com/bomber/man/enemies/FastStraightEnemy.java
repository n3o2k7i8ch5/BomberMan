package com.bomber.man.enemies;


import com.bomber.man.GameFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.bomber.man.Object.direction.NULL;

/**
 * Created by Murspi on 21.03.2017.
 */
public class FastStraightEnemy extends StraightEnemy {

    static final int SPEED = 3;

    public FastStraightEnemy(GameFrame frame, int X, int Y) {
        super(frame, X, Y, SPEED);
    }

    @Override
    protected ArrayList<Image> getImageUpList() {
        return getMain().graphicsContainer.fastStraightEnemyUpImages;
    }

    @Override
    protected ArrayList<Image> getImageDownList() {
        return getMain().graphicsContainer.fastStraightEnemyDownImages;
    }

    @Override
    protected ArrayList<Image> getImageLeftList() {
        return getMain().graphicsContainer.fastStraightEnemyLeftImages;
    }

    @Override
    protected ArrayList<Image> getImageRightList() {
        return getMain().graphicsContainer.fastStraightEnemyRightImages;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.fastStraightEnemyRightImages;
    }

}
