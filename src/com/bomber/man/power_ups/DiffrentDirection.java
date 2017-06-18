package com.bomber.man.power_ups;

import com.bomber.man.GameFrame;
import com.bomber.man.player.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Murspi on 18.06.2017.
 */
public class DiffrentDirection extends PowerUp {

    public DiffrentDirection(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    @Override
    public void performBonus() {
        frame.player.changeDirection(Player.CHANGE_DIRECTION_TIME);
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.curseUpImages;
    }
}
