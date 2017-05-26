package com.bomber.man.enemies;

import com.bomber.man.GameFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Murspi on 10.03.2017.
 */
public class MagnetEnemy extends Enemy {

    public MagnetEnemy(GameFrame frame, int x, int y, int speed)
    {
        super(frame,x,y,speed);
    }

    @Override
    public void updateStep(long time) {

        ArrayList<direction> directions = new ArrayList<>();

        if(isAlignedX() && isAlignedY()) {
            if (Y - frame.player.Y > 0 && isDirFreeToGo(direction.UP))
                directions.add(direction.UP);
            if (Y - frame.player.Y < 0 && isDirFreeToGo(direction.DOWN))
                directions.add(direction.DOWN);
            if (X - frame.player.X > 0 && isDirFreeToGo(direction.LEFT))
                directions.add(direction.LEFT);
            if (X - frame.player.X < 0 && isDirFreeToGo(direction.RIGHT))
                directions.add(direction.RIGHT);

            if(directions.size()!=0)
                new_dir = directions.get(Math.abs(new Random().nextInt())% directions.size());
            else
                new_dir = direction.NULL;
        }

        super.updateStep(time);

    }

    @Override
    protected ArrayList<Image> getImageUpList() {
        return getMain().graphicsContainer.magnetEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageDownList() {
        return getMain().graphicsContainer.magnetEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageLeftList() {
        return getMain().graphicsContainer.magnetEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageRightList() {
        return getMain().graphicsContainer.magnetEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.magnetEnemyImages;
    }


}
