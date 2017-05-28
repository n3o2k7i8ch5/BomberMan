package com.bomber.man.power_ups;

import com.bomber.man.Explosion;
import com.bomber.man.GameFrame;
import com.bomber.man.Object;
import com.bomber.man.listeners.ExplosionColisionListener;
import com.bomber.man.listeners.PlayerColisionListener;

import java.util.Iterator;

/**
 * Created by Murspi on 22.03.2017.
 */
public abstract class PowerUp extends Object {

    PowerUp powerUp;

    public PowerUp(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
        powerUp = this;
        addPlayerColisionListener(new PlayerColisionListener(frame) {
            @Override
            public void onColision(Iterator<Object> it) {
                performBonus();
                it.remove();
                getObjectManager().powerup_list.remove(powerUp);
                getObjectManager().all_objects[powerUp.X][powerUp.Y].remove(powerUp);
            }
        });

        addExplosionColisionListener(new ExplosionColisionListener() {
            @Override
            public void onColision(Explosion explosion, Iterator<Object> it) {
                it.remove();
                getObjectManager().powerup_list.remove(powerUp);
                getObjectManager().all_objects[powerUp.X][powerUp.Y].remove(powerUp);
            }
        });
    }

    /**
     * Metoda abstrakcyjna wywoływania podczas zebrania PowerUpa przez gracza.
     */
    public abstract void performBonus();
}

