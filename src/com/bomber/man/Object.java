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

    ArrayList<Image> current_image_list = new ArrayList<>();
    private int current_image_index;

    protected direction current_dir = NULL;
    protected direction new_dir = NULL;

    protected GameFrame frame;

    public enum direction{UP, DOWN, RIGHT, LEFT, NULL}

    PlayerColisionListener playerColisionListener = null;

    public Object(GameFrame frame, int X, int Y){
        this.frame = frame;
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;

        updateImageList();
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(current_image_list.get(current_image_index),
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

    public boolean isAlignedY(){
        return y % Main.RESOLUTION == 0;
    }

    public boolean isAlignedX() {
        return x % Main.RESOLUTION == 0;
    }

    double previous_sub_time = 0;
    @Override
    protected void update(long time) {
        super.update(time);

        double sub_time = time*frame.frame_time % ((double)500/(double)current_image_list.size());
        updateImageList();
        if(previous_sub_time>sub_time) {
            updateImage();
        }
        previous_sub_time = sub_time;

    }

    public Solid leftSolid(){
        return getObjectManager().leftSolid(this);
    }

    public Solid rightSolid(){
        return getObjectManager().rightSolid(this);
    }

    public Solid upSolid(){
        return getObjectManager().upSolid(this);
    }

    public Solid downSolid(){
        return getObjectManager().downSolid(this);
    }

    /**
     * ustawia obecnie wyświetlaną klatkę animacji obiektu na kolejną.
     */
    protected void updateImage(){
        if(current_image_list.size()!=0)
            current_image_index = (current_image_index + 1)%current_image_list.size();
    }

    /**
     * @param direction kierunek wejściowy, od którego zwracany jest kierunek na prawo.
     * @return kierunek na prawo od kierunku podanego.
     */
    protected direction dirRightTo(direction direction)
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
    protected direction dirLeftTo(direction direction)
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
    protected direction dirReverse(direction direction)
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
    protected abstract ArrayList<Image> getImageNullList();

    protected void updateImageList(){
        if(current_image_list != getImageNullList()) {
            current_image_list = getImageNullList();
            updateImage();
        }
    }

    protected ObjectManager getObjectManager(){
        return frame.objectManager;
    }

    public void addPlayerColisionListener(PlayerColisionListener playerColisionListener){
        this.playerColisionListener = playerColisionListener;
        this.playerColisionListener.assignToObject(this);
    }

}
