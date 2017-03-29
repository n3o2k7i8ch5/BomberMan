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

    public StraightEnemy(GameFrame frame, int X, int Y, int speed) {
        super(frame, X, Y, speed);
    }

    @Override
    public void update(long time) {
        Random random = new Random();
        if(current_direction==NULL)
            new_direction = randomFreeDirection();

        if (!isDirectionFreeToGo(directionLeftTo(current_direction)) &&
                !isDirectionFreeToGo(directionRightTo(current_direction)) &&
                !isDirectionFreeToGo(current_direction)){

            if(isDirectionFreeToGo(directionReverse(current_direction)))
                new_direction = directionReverse(current_direction);
            else
                new_direction = NULL;
        }else{
            int r = random.nextInt()%3;

            if(r==0 && isDirectionFreeToGo(directionRightTo(current_direction)))
                new_direction= directionRightTo(current_direction);
            if(r==1 && isDirectionFreeToGo(directionLeftTo(current_direction)))
               new_direction = directionLeftTo(current_direction);
        }

        if(frame.player.X == X && frame.player.Y == Y)
            getMain().setGameState(-1);

        super.update(time);
    }

    @Override
    protected ArrayList<Image> getImageList() {
        return getMain().graphicsContainer.straightEnemyPath;
    }

}
