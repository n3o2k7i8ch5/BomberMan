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

        Random random = new Random();

        int a = random.nextInt() % 4;

        if (a == 0 && isDirectionFreeToGo(UP))
            new_direction = UP;
        else if(a==1 && isDirectionFreeToGo(DOWN))
            new_direction = DOWN;
        else if(a==2 && isDirectionFreeToGo(RIGHT))
            new_direction = RIGHT;
        else if(a==3 && isDirectionFreeToGo(LEFT))
            new_direction = LEFT;
        else
            new_direction = NULL;

        if(frame.player.X == X && frame.player.Y == Y)
            getMain().setGameState(-1);

        super.update(time);

    }

    @Override
    protected ArrayList<Image> getImageList() {
        return getMain().graphicsContainer.enemyImages;
    }

}
