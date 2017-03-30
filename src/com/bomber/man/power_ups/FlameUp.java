package com.bomber.man.power_ups;

import com.bomber.man.GameFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 28.03.2017.
 */
public class FlameUp extends PowerUp {
    public FlameUp(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }


    @Override
    public void performBonus() {
        frame.player.increaseFlame();
    }

    @Override
    protected ArrayList<Image> getImageList() {
        return getMain().graphicsContainer.powerUpFlamePath;
    }
}
