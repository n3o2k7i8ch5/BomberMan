package com.bomber.man;

/**
 * Created by Kisiel on 08.03.2017.
 */
public abstract class Solid extends Object {

    boolean isSoft;

    public Solid(GameFrame frame, int x, int y, boolean isSoft) {
        super(frame, x, y);
        this.isSoft = isSoft;
    }

}
