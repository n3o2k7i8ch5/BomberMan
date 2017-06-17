package com.bomber.man;

import com.bomber.man.enemies.SmartAssEnemy;
import com.bomber.man.power_ups.PowerUp;

import java.awt.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kisiel on 26.05.2017.
 */
public class LivingWall extends Wall {

    public LivingWall(GameFrame frame, int X, int Y) {
        super(frame, X, Y, true);
        for(SmartAssEnemy enemy : getObjectManager().smartass_enemy_list)
            enemy.checkSafety();
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.livingWallImages;
    }

    protected void update(long time) {
        super.update(time);
        if(time%500 == 0) {
            if(canExpand(X, Y-1) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X, Y-1);

            if(canExpand(X, Y+1) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X, Y+1);

            if(canExpand(X-1, Y) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X-1, Y);

            if(canExpand(X+1, Y) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X+1, Y);
        }
    }

    private boolean canExpand(int X, int Y){

        ArrayList<Object> objects = getObjectManager().getSurroundingObjects(X, Y);

        for(Object object : objects)
            if(object.touches(X, Y, 1) &&
                    (object instanceof MovingObject
                    || object.solid
                    || object instanceof PowerUp))
                return false;

        return true;
    }
}
