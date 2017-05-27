package com.bomber.man.forest;

import com.bomber.man.GameFrame;
import com.bomber.man.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 27.03.2017.
 */
public class Forest extends Tile {

    boolean transp = false;

    public Forest(GameFrame frame, int X, int Y) {
        super(frame, X, Y);
    }

    public void setTransp(boolean transp) {
        this.transp = transp;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        if(transp)
            return getMain().graphicsContainer.forestTranspImages;
        else
            return getMain().graphicsContainer.forestImages;
    }
}
