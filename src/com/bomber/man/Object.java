package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;

import static com.bomber.man.Object.direction.*;
import static com.bomber.man.Object.direction.NULL;
import static com.bomber.man.Object.direction.RIGHT;

/**
 * Created by Daniel on 07.03.2017.
 */
public abstract class Object extends Entity{

    public int x, y;
    public int X, Y;

    Image current_image;
    int current_image_index;
    protected GameFrame frame;

    public enum direction{UP, DOWN, RIGHT, LEFT, NULL}

    public Object(GameFrame frame, int X, int Y){
        this.frame = frame;
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;

        current_image = getImageList().get(0);
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(current_image,
                (int)((x - frame.x_map_shift)*Main.w_scale_rate),
                (int)((y - frame.y_map_shift)*Main.h_scale_rate),
                null);
    }

    /**
     * @return instancję klasy Main, która przechowuje aktualną grę.
     */
    public Main getMain(){
        return frame.main;
    }

    /**
     * Zwraca Obiekt z podanej listy, o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @param list lista, w której wyszukiwany jest Obiekt o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @return Obiekt przechowywany w podanej liście o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @return null, jeżeli podana lista nie przechowuje Obiektu o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     */
    public Object hereObject(ArrayList list){

        boolean isObject = false;

        ArrayList<Object> arrayList = (ArrayList<Object>) list;
        for(Object object : arrayList) {
            isObject = object.X == X && object.Y == Y;

            if(isObject)
                return object;
        }
        return null;
    }

    /**
     * Zwraca Obiekt z podanej listy, na lewo od Obiektu, z którego wywoływana jest metoda.
     * @param list lista, w której wyszukiwany jest Obiekt na lewo od Obiektu, z którego wywoływana jest metoda.
     * @return Obiekt przechowywany w podanej liście na lewo od Obiektu, którego wywoływana jest metoda.
     * @return null, jeżeli podana lista nie przechowuje Obiektu na lewo od Obiektu, z którego wywoływana jest metoda.
     */
    public Object leftObject(ArrayList list){

        boolean isObject = false;

        ArrayList<Object> arrayList = (ArrayList<Object>) list;
        for(Object object : arrayList) {
            if (X - 1 >= 0) {
                if (isAlignedY())
                    isObject = object.X == X - 1 && object.Y == Y;
                else if (Y + 1 < Main.ABS_H_MAP_SIZE)
                    isObject = object.X == X - 1 && object.Y == Y || object.X == X - 1 && object.Y == Y + 1;
            }

            if(isObject)
                return object;
        }
        return null;
    }

    /**
     * Zwraca Obiekt z podanej listy, na prawo od Obiektu, z którego wywoływana jest metoda.
     * @param list lista, w której wyszukiwany jest Obiekt na prawo od Obiektu, z którego wywoływana jest metoda.
     * @return Obiekt przechowywany w podanej liście na prawo od Obiektu, którego wywoływana jest metoda.
     * @return null, jeżeli podana lista nie przechowuje Obiektu na prawo od Obiektu, z którego wywoływana jest metoda.
     */
    public Object rightObject(ArrayList list){

        boolean isObject = false;

        ArrayList<Object> arrayList = (ArrayList<Object>) list;
        for(Object object : arrayList) {
            if (X + 1 < Main.ABS_W_MAP_SIZE) {
                if (isAlignedY())
                    isObject = object.X == X + 1 && object.Y == Y;
                else if (Y + 1 < Main.ABS_H_MAP_SIZE)
                    isObject = object.X == X + 1 && object.Y == Y || object.X == X + 1 && object.Y == Y + 1;
            }

            if(isObject)
                return object;
        }
        return null;
    }

    /**
     * Zwraca Obiekt z podanej listy, na górę od Obiektu, z którego wywoływana jest metoda.
     * @param list lista, w której wyszukiwany jest Obiekt na górę od Obiektu, z którego wywoływana jest metoda.
     * @return Obiekt przechowywany w podanej liście na górę od Obiektu, którego wywoływana jest metoda.
     * @return null, jeżeli podana lista nie przechowuje Obiektu na górę od Obiektu, z którego wywoływana jest metoda.
     */
    public Object upObject(ArrayList list){

        boolean isObject = false;

        ArrayList<Object> arrayList = (ArrayList<Object>) list;
        for(Object object : arrayList) {
            if (Y-1>=0) {
                if (isAlignedX())
                    isObject = object.X == X && object.Y == Y - 1;
                else if (X + 1 < getMain().ABS_W_MAP_SIZE)
                    isObject = object.X == X && object.Y == Y - 1 || object.X == X + 1 && object.Y == Y - 1;
            }

            if(isObject)
                return object;
        }
        return null;
    }

    /**
     * Zwraca Obiekt z podanej listy, na dół od Obiektu, z którego wywoływana jest metoda.
     * @param list lista, w której wyszukiwany jest Obiekt na dół od Obiektu, z którego wywoływana jest metoda.
     * @return Obiekt przechowywany w podanej liście na dół od Obiektu, którego wywoływana jest metoda.
     * @return null, jeżeli podana lista nie przechowuje Obiektu na dół od Obiektu, z którego wywoływana jest metoda.
     */
    public Object downObject(ArrayList list){

        boolean isObject = false;

        ArrayList<Object> arrayList = (ArrayList<Object>) list;
        for(Object object : arrayList) {
            if (Y+1<Main.ABS_H_MAP_SIZE) {
                if (isAlignedX())
                    isObject = object.X == X && object.Y == Y + 1;
                else if (X + 1 < Main.ABS_W_MAP_SIZE)
                    isObject = object.X == X && object.Y == Y + 1 || object.X == X + 1 && object.Y == Y + 1;
            }

            if(isObject)
                return object;
        }
        return null;
    }

    protected boolean isAlignedY(){
        return y % Main.RESOLUTION == 0;
    }

    protected boolean isAlignedX() {
        return x % Main.RESOLUTION == 0;
    }

    double previous_sub_time = 0;
    @Override
    public void update(long time) {
        super.update(time);
        double sub_time = time*frame.frame_time % ((double)1000/(double)getImageList().size());
        if(previous_sub_time>sub_time)
            updateImage();
        previous_sub_time = sub_time;
    }

    /**
     * ustawia obecnie wyświetlaną klatkę animacji obiektu na kolejną.
     */
    public void updateImage(){
        current_image = getImageList().get(current_image_index);
        current_image_index = (current_image_index+ 1)%getImageList().size();
    }

    /**
     * @param direction kierunek wejściowy, od którego zwracany jest kierunek na prawo.
     * @return kierunek na prawo od kierunku podanego.
     */
    protected direction directionRightTo(direction direction)
    {
        switch (direction){
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            default:
                return NULL;
        }
    }

    /**
     * @param direction kierunek wejściowy, od którego zwracany jest kierunek na lewo.
     * @return kierunek na prawo od kierunku podanego.
     */
    protected direction directionLeftTo(direction direction)
    {
        switch (direction){
            case DOWN:
                return RIGHT;
            case RIGHT:
                return UP;
            case UP:
                return LEFT;
            case LEFT:
                return DOWN;
            default:
                return NULL;
        }
    }

    /**
     * @param direction kierunek wejściowy, od którego zwracany jest kierunek przeciwny.
     * @return kierunek przeciwny od kierunku podanego.
     */
    protected direction directionReverse(direction direction)
    {
        switch (direction){
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
            default:
                return NULL;
        }
    }

    /**
     * funkcja abstrakcyjna zwracająca listę kolejnych klatek animacji instancji Obiektu.
     * @return listę kolejnych klatek animacji Obiektu.
     */
    protected abstract ArrayList<Image> getImageList();
}
