package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kisiel on 26.05.2017.
 */
public class LivingWall extends Solid {

    public LivingWall(GameFrame frame, int X, int Y, boolean isSoft) {
        super(frame, X, Y, isSoft);
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.livingWallPath;
    }

    protected void update(long time) {
        super.update(time);
        if(time%1000 == 0) {
            if(isDirFreeToGo(direction.UP) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X, Y-1);
            if(isDirFreeToGo(direction.DOWN) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X, Y+1);
            if(isDirFreeToGo(direction.LEFT) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X-1, Y);
            if(isDirFreeToGo(direction.RIGHT) && new Random().nextInt()%5==0)
                getObjectManager().addLivingWall(X+1, Y);
        }

    }
}
