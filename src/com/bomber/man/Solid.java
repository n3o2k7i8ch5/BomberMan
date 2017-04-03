package com.bomber.man;

/**
 * Created by Kisiel on 08.03.2017.
 */
public abstract class Solid extends Object {

    /**
     * Klasa Solid jest klasą abstrakcyjną.
     * @param frame instancja klasy GameFrame, w jakiej przechowywane są parametry i stan gry.
     * @param X pozycja obiektu liczona w ilości kratek.
     * @param Y pozycja obiektu liczona w ilości kratek.
     * @param isSoft  określa, czy ściana może zostać zniszczona przez wybuch bomby.
     */

    boolean isSoft;

    public Solid(GameFrame frame, int X, int Y, boolean isSoft) {
        super(frame, X, Y);
        this.isSoft = isSoft;

    }

}
