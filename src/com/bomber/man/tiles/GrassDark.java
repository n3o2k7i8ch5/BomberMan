package com.bomber.man.tiles;

import com.bomber.man.GameFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 27.03.2017.
 */
public class GrassDark extends Tile {
    public GrassDark(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.grassDarkImages;
    }
}
