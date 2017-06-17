package com.bomber.man;

import com.bomber.man.listeners.ExplosionColisionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Kisiel on 17.06.2017.
 */
public class InvisibleWall extends Wall {
    /**
     * Klasa Wall jest to ściana w naszej grze.
     *
     * @param frame     instancja klasy GameFrame, w jakiej przechowywane są parametry i stan gry.
     * @param x         pozycja ściany, liczona w ilości kratek.
     * @param y         pozycja ściany, liczona w ilości kratek.
     */

    public InvisibleWall(GameFrame frame, int x, int y, boolean leaveRandomPowerUp) {
        super(frame, x, y, false);

        if(leaveRandomPowerUp)
            addExplosionColisionListener(new ExplosionColisionListener() {
                @Override
                public void onColision(Explosion explosion, Iterator<Object> it) {
                    explosion.leaveRandPowerUp = true;
                }
            });

    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.emptyImages;
    }
}
