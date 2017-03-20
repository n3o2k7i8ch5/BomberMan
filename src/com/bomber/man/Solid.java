package com.bomber.man;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class Solid extends Object {

    boolean isSoft;

    public Solid(GameFrame frame, int x, int y, String image_path, boolean isSoft) {
        super(frame, x, y, image_path);
        this.isSoft = isSoft;
    }

}
