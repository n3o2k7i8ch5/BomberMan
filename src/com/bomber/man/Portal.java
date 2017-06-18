package com.bomber.man;

import com.bomber.man.listeners.PlayerColisionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Kisiel on 15.06.2017.
 */
public class Portal extends Object {

    boolean locked;

    public Portal(GameFrame frame, int X, int Y) {
        super(frame, X, Y);

        addPlayerColisionListener(new PlayerColisionListener(frame) {
            @Override
            public void onColision(Iterator<Object> it) {
                if (!locked)
                    if (touches(player(), 0.1)) {
                        frame.pause = true;
                        if(getMain().getCurrentLevel() == getMain().getLevelCount())
                            getMain().setGameState(Main.WINNER);
                        else {
                            getMain().infoBox.params = player().getParams();
                            getMain().setGameState(Main.NEXT_LEVEL);
                        }
                    }
            }
        });
        setAnimationDuration(250);

    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        if(locked)
            return getMain().graphicsContainer.portalLockedImages;
        else
            return getMain().graphicsContainer.portalImages;
    }
}
