package com.bomber.man;

import java.awt.*;
import java.lang.*;

/**
 * Podstawowa klasa bazowa, będąca dziedziczona przez klasy pochodne.
 */
public abstract class Entity{

    void handleEvents(Event e){}

    /**
     * Funkcja wywoływana przed wyświetleniem kolejnek klatki gry.
     * @param time jest liczbą określajacą ilość dotychczasowych wywołań funkcji.
     */
    protected void update(long time){}

    /**
     * Funkcja rysująca obiekt w oknie programu.
     */
    public void draw(Graphics2D g2d) {}
}
