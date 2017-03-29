package com.bomber.man.power_ups;

import com.bomber.man.GameFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 28.03.2017.
 */
public class SpeedUp extends PowerUp {
    public SpeedUp(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    @Override
    public void performBonus() {
        frame.player.increaseSpeed();
    }

    @Override
    protected ArrayList<Image> getImageList() {
        return getMain().graphicsContainer.powerUpSpeedPath;
    }
}
