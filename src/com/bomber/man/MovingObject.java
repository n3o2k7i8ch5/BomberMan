package com.bomber.man;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import static com.bomber.man.Main.ABS_H_MAP_SIZE;
import static com.bomber.man.Main.ABS_W_MAP_SIZE;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.Object.direction.*;

/**
 * Klasa MovingObject dziedziczy po klasie Object. Jest klasą abstrakcyjną, po której dziedziczą wszystkie klasy poruszajace się po mapie.
 */
public abstract class MovingObject extends Object {

    private /*int*/ double old_x, old_y;
    public int old_x(){return (int)old_x;}
    public int old_y(){return (int)old_y;}

    protected int old_X, old_Y;
    protected double speed;

    int align_factor;
    private double element;
    /**
     * Konstruktor klasy.
     *
     * @param frame instancja klasy GameFrame, w jakiej przechowywane są parametry i stan gry.
     * @param X     pozycja obiektu liczona w ilości kratek.
     * @param Y     pozycja obiektu liczona w ilości kratek.
     * @param speed prędkość porszania się obieku.
     */

    public MovingObject(GameFrame frame, int X, int Y, double speed) {
        super(frame, X, Y);
        this.speed = speed;
        this.align_factor = 1;
        this.old_x = x;
        this.old_y = y;
        element = ((double) RESOLUTION / (double) align_factor);
    }

    public MovingObject(GameFrame frame, int X, int Y, double speed, int align_factor, boolean softSolid) {
        super(frame, X, Y, softSolid);
        this.speed = speed;
        this.align_factor = 1;
        this.old_x = x;
        this.old_y = y;
        element = ((double) RESOLUTION / (double) align_factor);
    }

    public MovingObject(GameFrame frame, int X, int Y, double speed, int align_factor) {
        super(frame, X, Y);
        this.speed = speed;
        this.align_factor = align_factor;
        this.old_x = x;
        this.old_y = y;
        element = ((double) RESOLUTION / (double) align_factor);
    }

    /**
     * Metoda wywoływana przed tym, jak obiekt zmieni położenie.
     */
    public void beforePositionChanged() {
        old_x = x;
        old_y = y;
        old_X = (old_x() / RESOLUTION);
        old_Y = (old_y() / RESOLUTION);
    }

    /**
     * Metoda, która wywoływana po tym, jak obiekt zmienił położenie.
     */
    public void onPositionChanged() {
        X = (x() / RESOLUTION);
        Y = (y() / RESOLUTION);
        if (X != old_X || Y != old_Y)
            onTilePositionChanged();
    }

    public void onTilePositionChanged() {
        frame.objectManager.all_objects[old_X][old_Y].remove(this);
        frame.objectManager.all_objects[X][Y].add(this);
    }

    protected void updateStep(long time){
        updateCurrentDir();
    }

    @Override
    protected void update(long time) {
        super.update(time);

        for (int i = 0; i < speed; i++) {
            updateStep(time);
            if (i + 1 > speed)
                moveBy(speed - (int) speed);
            else
                moveBy(1);
        }

    }

    public void moveBy(double distance){
        if (current_dir == UP) {
            beforePositionChanged();
                y -= distance;
            onPositionChanged();
        } else if (current_dir == DOWN) {
            beforePositionChanged();
                y += distance;
            onPositionChanged();
        } else if (current_dir == LEFT) {
            beforePositionChanged();
                x -= distance;
            onPositionChanged();
        } else if (current_dir == RIGHT) {
            beforePositionChanged();
                x += distance;
            onPositionChanged();
        }
    }

    /**
     * Metoda aktualizująca kierunek ruchu w momencie, kiedy obiekt znajduje na odpowiedniej części kratki.
     */
    private void updateCurrentDir() {

        int x_tmp = (int)(x % element);
        int y_tmp = (int)(y % element);

        if(new_dir==dirReverse(current_dir))
            current_dir = new_dir;

        if ((current_dir == RIGHT && x_tmp == 0) ||
                (current_dir == LEFT && x_tmp == 0) ||
                (current_dir == UP && y_tmp == 0) ||
                (current_dir == DOWN && y_tmp == 0) ||
                current_dir == NULL) {
            current_dir = new_dir;
        }
    }

    /**
     * Metoda zwracająca losowy, wolny do przemieszczenia się kierunek ruchu.
     *
     * @return kierunek ruchu
     */
    protected direction randomFreeDirection() {

        ArrayList<direction> free_dirs = new ArrayList<>();
        if (isDirFreeToGo(UP))
            free_dirs.add(UP);

        if (isDirFreeToGo(DOWN))
            free_dirs.add(DOWN);

        if (isDirFreeToGo(RIGHT))
            free_dirs.add(RIGHT);

        if (isDirFreeToGo(LEFT))
            free_dirs.add(LEFT);

        if (free_dirs.size() == 0)
            return NULL;
        else {
            int r = Math.abs(new Random().nextInt()) % free_dirs.size();
            return free_dirs.get(r);
        }
    }

    /**
     * Metoda zmieniająca listę przechowującą elementy animacji, w zależności od kierunku ruchu.
     */
    @Override
    protected void updateImageList() {
        switch (current_dir) {
            case UP:
                if (!current_image_list.equals(getImageUpList())) {
                    current_image_list = getImageUpList();
                    updateImage();
                }
                break;
            case DOWN:
                if (!current_image_list.equals(getImageDownList())) {
                    current_image_list = getImageDownList();
                    updateImage();
                }
                break;
            case RIGHT:
                if (!current_image_list.equals(getImageRightList())) {
                    current_image_list = getImageRightList();
                    updateImage();
                }
                break;
            case LEFT:
                if (!current_image_list.equals(getImageLeftList())) {
                    current_image_list = getImageLeftList();
                    updateImage();
                }
                break;
            case NULL:
                if (!current_image_list.equals(getImageNullList())) {
                    current_image_list = getImageNullList();
                    updateImage();
                }
                break;
        }
    }

    protected abstract ArrayList<Image> getImageUpList();

    protected abstract ArrayList<Image> getImageDownList();

    protected abstract ArrayList<Image> getImageLeftList();

    protected abstract ArrayList<Image> getImageRightList();
}
