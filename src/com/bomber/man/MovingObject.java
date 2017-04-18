package com.bomber.man;

import java.awt.*;
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

    private int old_x, old_y;
    protected int old_X, old_Y;
    int speed;
    int align_factor;
    private double element;
    private boolean is_element_int;

    /**
     * Konstruktor klasy.
     *
     * @param frame instancja klasy GameFrame, w jakiej przechowywane są parametry i stan gry.
     * @param X     pozycja obiektu liczona w ilości kratek.
     * @param Y     pozycja obiektu liczona w ilości kratek.
     * @param speed prędkość porszania się obieku.
     */
    public MovingObject(GameFrame frame, int X, int Y, int speed) {
        super(frame, X, Y);
        this.speed = speed;
        this.align_factor = 1;
        this.old_x = x;
        this.old_y = y;
        element = ((double) RESOLUTION / (double) align_factor);
        is_element_int = element == Math.floor(element);
    }

    public MovingObject(GameFrame frame, int X, int Y, int speed, int align_factor) {
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
        old_X = (old_x / RESOLUTION);
        old_Y = (old_y / RESOLUTION);
    }

    /**
     * Metoda, która wywoływana po tym, jak obiekt zmienił położenie.
     */
    public void onPositionChanged() {
        X = x / RESOLUTION;
        Y = y / RESOLUTION;
        if (X != old_X || Y != old_Y)
            onTilePositionChanged();
    }

    public void onTilePositionChanged() {
        frame.objectManager.all_objects[old_X][old_Y].remove(this);
        frame.objectManager.all_objects[X][Y].add(this);
    }

    @Override
    protected void update(long time) {
        super.update(time);

        if (is_element_int) {
            updateCurrentDir();
            if (current_dir == UP) {
                beforePositionChanged();
                y -= speed;
                onPositionChanged();
            } else if (current_dir == DOWN) {
                beforePositionChanged();
                y += speed;
                onPositionChanged();
            } else if (current_dir == LEFT) {
                beforePositionChanged();
                x -= speed;
                onPositionChanged();
            } else if (current_dir == RIGHT) {
                beforePositionChanged();
                x += speed;
                onPositionChanged();
            }
        } else {
            for (int i = 0; i < speed; i++) {
                updateCurrentDir();
                if (current_dir == UP) {
                    beforePositionChanged();
                    y--;
                    onPositionChanged();
                } else if (current_dir == DOWN) {
                    beforePositionChanged();
                    y++;
                    onPositionChanged();
                } else if (current_dir == LEFT) {
                    beforePositionChanged();
                    x--;
                    onPositionChanged();
                } else if (current_dir == RIGHT) {
                    beforePositionChanged();
                    x++;
                    onPositionChanged();
                }
            }
        }
    }

    /**
     * Metoda aktualizująca kierunek ruchu w momencie, kiedy obiekt znajduje na odpowiedniej części kratki.
     */
    private void updateCurrentDir() {
        if ((current_dir == RIGHT && x % element < old_x % element) ||
                (current_dir == LEFT && (x - 1) % element > (old_x - 1) % element) ||
                (current_dir == UP && (y - 1) % element > (old_y - 1) % element) ||
                (current_dir == DOWN && y % element < old_y % element) ||
                current_dir == NULL) {
            current_dir = new_dir;
        }
    }

    /**
     * Metoda sprawdzająca czy wybrany kierunek jest możliwy do przejścia.
     *
     * @param direction kierunek sprawdzany.
     */
    public boolean isDirFreeToGo(direction direction) {
        if (direction == UP && y > 0)
            return !isAlignedY() || upSolid() == null;
        else if (direction == DOWN && y < ABS_H_MAP_SIZE * RESOLUTION)
            return !isAlignedY() || downSolid() == null;
        else if (direction == LEFT && x > 0)
            return !isAlignedX() || leftSolid() == null;
        else if (direction == RIGHT && x < ABS_W_MAP_SIZE * RESOLUTION)
            return !isAlignedX() || rightSolid() == null;
        else if (direction == NULL)
            return true;
        else
            return false;
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
                if (current_image_list != getImageUpList()) {
                    current_image_list = getImageUpList();
                    updateImage();
                }
                break;
            case DOWN:
                if (current_image_list != getImageDownList()) {
                    current_image_list = getImageDownList();
                    updateImage();
                }
                break;
            case RIGHT:
                if (current_image_list != getImageRightList()) {
                    current_image_list = getImageRightList();
                    updateImage();
                }
                break;
            case LEFT:
                if (current_image_list != getImageLeftList()) {
                    current_image_list = getImageLeftList();
                    updateImage();
                }
                break;
            case NULL:
                if (current_image_list != getImageNullList()) {
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
