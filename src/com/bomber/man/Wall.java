package com.bomber.man;

import com.bomber.man.listeners.ExplosionColisionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Wall extends Object {

    /**
     * Klasa Wall jest to ściana w naszej grze.
     * @param frame instancja klasy GameFrame, w jakiej przechowywane są parametry i stan gry.
     * @param x pozycja ściany, liczona w ilości kratek.
     * @param y pozycja ściany, liczona w ilości kratek.
     * @param softSolid określa, czy ściana może zostać zniszczona przez wybuch bomby.
     */

    public Wall(GameFrame frame, int x, int y, boolean softSolid) {
        super(frame, x, y, softSolid);
        updateImageList();

        Wall wall = this;

        addExplosionColisionListener(new ExplosionColisionListener() {
            @Override
            public void onColision(Explosion explosion, Iterator<Object> it) {
                explosion.leaveRandPowerUp = true;
                getObjectManager().removeSolid(wall);
            }
        });
    }

    /**
     * Metoda pobierająca pobrazek softWall lub HardWall w zależności od wartości parametru isSoft.
     */
    @Override
    protected ArrayList<Image> getImageNullList() {
        if(softSolid)
            return getMain().graphicsContainer.softWallImages;
        else
            return getMain().graphicsContainer.hardWallImages;
    }
}
