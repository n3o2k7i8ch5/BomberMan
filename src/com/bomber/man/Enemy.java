package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.bomber.man.Object.direction.*;

/**
 * Created by Murspi on 10.03.2017.
 */
public class Enemy extends MovingObject{

    Enemy(GameFrame frame, int x, int y, int speed,int align_factor)
    {
        super(frame,x,y,speed,align_factor);
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
