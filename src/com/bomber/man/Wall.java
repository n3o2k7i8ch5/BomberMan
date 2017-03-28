package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Wall extends Solid {

    public Wall(GameFrame frame, int x, int y, boolean isSoft) {
        super(frame, x, y, isSoft);
    }

    @Override
    protected ArrayList<Image> getImageList() {
        if(isSoft)
            return getMain().graphicsContainer.softWallImages;
        else
            return getMain().graphicsContainer.hardWallImages;
    }
}
