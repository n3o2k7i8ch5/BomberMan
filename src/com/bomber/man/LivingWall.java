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
public class LivingWall extends Solid {

    public LivingWall(GameFrame frame, int X, int Y, boolean isSoft) {
        super(frame, X, Y, isSoft);
        for(SmartAssEnemy enemy : getObjectManager().smartass_enemy_list)
            enemy.checkSafety();
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.livingWallImages;
    }

    protected void update(long time) {
        super.update(time);
        if(time%1000 == 0) {
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

        ArrayList<Object> objects = getSurroundingObjects();

        for(Object object : objects)
            if(object.touches(X, Y, 1) &&
                    (MovingObject.class.isInstance(object)
                    || Solid.class.isInstance(object)
                    || PowerUp.class.isInstance(object)))
                return false;
        return true;
    }
}
