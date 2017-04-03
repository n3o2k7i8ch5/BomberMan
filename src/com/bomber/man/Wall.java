package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 11.03.2017.
 */
public class Wall extends Solid {

    /**
     * Klasa Wall jest to ściana w naszej grze.
     * @param frame instancja klasy GameFrame, w jakiej przechowywane są parametry i stan gry.
     * @param x pozycja ściany, liczona w ilości kratek.
     * @param y pozycja ściany, liczona w ilości kratek.
     * @param isSoft określa, czy ściana może zostać zniszczona przez wybuch bomby.
     */

    public Wall(GameFrame frame, int x, int y, boolean isSoft) {
        super(frame, x, y, isSoft);
        updateImageList();
    }

    /**
     * Metoda pobierająca pobrazek softWall lub HardWall w zależności od wartości parametru isSoft.
     */
    @Override
    protected ArrayList<Image> getImageNullList() {
        if(isSoft)
            return getMain().graphicsContainer.softWallImages;
        else
            return getMain().graphicsContainer.hardWallImages;
    }
}
