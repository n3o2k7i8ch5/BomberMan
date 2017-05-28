package com.bomber.man;

import com.bomber.man.listeners.ExplosionColisionListener;
import com.bomber.man.listeners.PlayerColisionListener;

import java.awt.*;
import java.util.ArrayList;

import static com.bomber.man.Main.ABS_H_MAP_SIZE;
import static com.bomber.man.Main.ABS_W_MAP_SIZE;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.Object.direction.*;
import static com.bomber.man.Object.direction.NULL;
import static com.bomber.man.Object.direction.RIGHT;

/**
 * Created by Daniel on 07.03.2017.
 */
public abstract class Object extends Entity{

    boolean solid;
    boolean softSolid;

    protected /*int*/ double x, y;
    int x(){return (int)x;}
    int y(){return (int)y;}

    public int X, Y;

    ArrayList<Image> current_image_list = new ArrayList<>();
    protected int current_image_index;

    protected direction current_dir = NULL;
    protected direction new_dir = NULL;

    protected GameFrame frame;

    public enum direction{UP, DOWN, RIGHT, LEFT, NULL}

    PlayerColisionListener playerColisionListener = null;
    ExplosionColisionListener explosionColisionListener = null;

    public Object(GameFrame frame, int X, int Y){
        this.frame = frame;
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;
        this.solid = false;

        updateImageList();
    }

    public Object(GameFrame frame, int X, int Y, boolean softSolid){
        this.frame = frame;
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;
        this.solid = true;
        this.softSolid = softSolid;

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
    protected Player player(){return frame.player;}

    public boolean isAlignedY(){
        return y() % Main.RESOLUTION == 0;
    }

    public boolean isAlignedX() {
        return x() % Main.RESOLUTION == 0;
    }

    double previous_sub_time = 0;
    @Override
    protected void update(long time) {
        super.update(time);

        double sub_time = time*frame.FRAME_TIME % ((double)500/(double)current_image_list.size());
        updateImageList();
        if(previous_sub_time>sub_time) {
            updateImage();
        }
        previous_sub_time = sub_time;
    }

    public Object upSolid(){
        return getObjectManager().upSolid(this);
    }
    public Object downSolid(){
        return getObjectManager().downSolid(this);
    }
    public Object leftSolid(){
        return getObjectManager().leftSolid(this);
    }
    public Object rightSolid(){
        return getObjectManager().rightSolid(this);
    }

    public Object upLeftSolid(){
        return getObjectManager().upLeftSolid(this);
    }
    public Object upRightSolid(){
        return getObjectManager().upRightSolid(this);
    }
    public Object downLeftSolid(){
        return getObjectManager().downLeftSolid(this);
    }
    public Object downRightSolid(){
        return getObjectManager().downRightSolid(this);
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
     * Metoda sprawdzająca czy wybrany kierunek jest możliwy do przejścia.
     *
     * @param direction kierunek sprawdzany.
     */
    public boolean isDirFreeToGo(direction direction) {
        if (direction == UP && y() > 0)
            return !(upSolid() != null && upSolid().neighboursY(this)) &&
                    !(upRightSolid() != null && upRightSolid().neighboursY(this) && upRightSolid().touchesX(this, 1)) &&
                    !(upLeftSolid() != null && upLeftSolid().neighboursY(this) && upLeftSolid().touchesX(this, 1));
        else if (direction == DOWN && y() < ABS_H_MAP_SIZE * RESOLUTION)
            return !(downSolid() != null && downSolid().neighboursY(this)) &&
                    !(downRightSolid() != null && downRightSolid().neighboursY(this) && downRightSolid().touchesX(this, 1)) &&
                    !(downLeftSolid() != null && downLeftSolid().neighboursY(this) && downLeftSolid().touchesX(this, 1));
        else if (direction == LEFT && x() > 0)
            return !(leftSolid() != null && leftSolid().neighboursX(this)) &&
                    !(downLeftSolid() != null && downLeftSolid().neighboursX(this) && downLeftSolid().touchesY(this, 1)) &&
                    !(upLeftSolid() != null && upLeftSolid().neighboursX(this) && upLeftSolid().touchesY(this, 1));
        else if (direction == RIGHT && x() < ABS_W_MAP_SIZE * RESOLUTION)
            return !(rightSolid() != null && rightSolid().neighboursX(this)) &&
                    !(downRightSolid() != null && downRightSolid().neighboursX(this) && downRightSolid().touchesY(this, 1)) &&
                    !(upRightSolid() != null && upRightSolid().neighboursX(this) && upRightSolid().touchesY(this, 1));
        else if (direction == NULL)
            return true;
        else
            return false;
    }

    public boolean isDirFreeFrom(Object o, direction direction) {
        if (direction == UP && y() > 0)
            return !(o.X==X && o.Y==Y-1 && o.neighboursY(this)) &&
                    !(o.X==X+1 && o.Y==Y-1 && o.neighboursY(this) && o.touchesX(this, 1)) &&
                    !(o.X==X-1 && o.Y==Y-1 && o.neighboursY(this) && o.touchesX(this, 1));
        else if (direction == DOWN && y() < ABS_H_MAP_SIZE * RESOLUTION)
            return !(o.X==X && o.Y==Y+1 && o.neighboursY(this)) &&
                    !(o.X==X+1 && o.Y==Y+1 && o.neighboursY(this) && o.touchesX(this, 1)) &&
                    !(o.X==X-1 && o.Y==Y+1 && o.neighboursY(this) && o.touchesX(this, 1));
        else if (direction == LEFT && x() > 0)
            return !(o.X==X-1 && o.Y==Y && o.neighboursX(this)) &&
                    !(o.X==X-1 && o.Y==Y+1 && o.neighboursX(this) && o.touchesY(this, 1)) &&
                    !(o.X==X-1 && o.Y==Y-1 && o.neighboursX(this) && o.touchesY(this, 1));
        else if (direction == RIGHT && x() < ABS_W_MAP_SIZE * RESOLUTION)
            return !(o.X==X+1 && o.Y==Y && o.neighboursX(this)) &&
                    !(o.X==X+1 && o.Y==Y+1 && o.neighboursX(this) && o.touchesY(this, 1)) &&
                    !(o.X==X+1 && o.Y==Y-1 && o.neighboursX(this) && o.touchesY(this, 1));
        else if (direction == NULL)
            return true;
        else
            return false;
    }

    public boolean touchesX(Object object, double accuracy){
        return Math.abs(x() - object.x()) < RESOLUTION * accuracy;
    }
    public boolean touchesX(int X, double accuracy){
        return Math.abs(x() - (X * RESOLUTION)) < RESOLUTION * accuracy;
    }
    public boolean touchesY(Object object, double accuracy){
        return Math.abs(y() - object.y()) < RESOLUTION * accuracy;
    }
    public boolean touchesY(int Y, double accuracy){
        return Math.abs(y() - (Y * RESOLUTION)) < RESOLUTION * accuracy;
    }
    public boolean touches(Object object, double accuracy){
        return touchesX(object, accuracy) && touchesY(object, accuracy);
    }

    public boolean touches(int X, int Y, double accuracy){
        return touchesX(X, accuracy) && touchesY(Y, accuracy);
    }

    public boolean neighboursX(Object object){
        return Math.abs(x() - object.x()) == RESOLUTION;
    }
    public boolean neighboursX(int X) {
        return Math.abs(x() - (X * RESOLUTION)) == RESOLUTION;
    }
    public boolean neighboursY(Object object){
        return Math.abs(y() - object.y()) == RESOLUTION;
    }
    public boolean neighboursY(int Y){
        return Math.abs(y() - (Y * RESOLUTION)) == RESOLUTION;
    }
    public boolean neighbours(Object object){
        return (neighboursX(object) && touchesY(object, 1)) ||
                (touchesX(object, 1) && neighboursY(object));
    }
    public boolean neighbours(int X, int Y){
        return (neighboursX(X) && touchesY(Y, 1)) ||
                (touchesX(X, 1) && neighboursY(Y));
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

    public void addExplosionColisionListener(ExplosionColisionListener explosionColisionListener){
        this.explosionColisionListener = explosionColisionListener;
        this.explosionColisionListener.assignToObject(this);
    }

    public ArrayList<Object> getSurroundingObjects(){

        ArrayList<Object> objects = new ArrayList<>();

        if(X!=0 && Y!=0)
            objects.addAll(getObjectManager().all_objects[X-1][Y-1]);

        if(Y!=0)
            objects.addAll(getObjectManager().all_objects[X][Y-1]);

        if(X!=getMain().ABS_W_MAP_SIZE-1 && Y!=0)
            objects.addAll(getObjectManager().all_objects[X+1][Y-1]);

        if(X!=0)
            objects.addAll(getObjectManager().all_objects[X-1][Y]);

        objects.addAll(getObjectManager().all_objects[X][Y]);

        if(X!=getMain().ABS_W_MAP_SIZE-1)
            objects.addAll(getObjectManager().all_objects[X+1][Y]);

        if(X!=0 && Y!=getMain().ABS_H_MAP_SIZE-1)
            objects.addAll(getObjectManager().all_objects[X-1][Y+1]);

        if(Y!=getMain().ABS_H_MAP_SIZE-1)
            objects.addAll(getObjectManager().all_objects[X][Y+1]);

        if(X!=getMain().ABS_W_MAP_SIZE-1 && Y!=getMain().ABS_H_MAP_SIZE-1)
            objects.addAll(getObjectManager().all_objects[X+1][Y+1]);

        return objects;
    }
}
