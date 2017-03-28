package com.bomber.man;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 07.03.2017.
 */
public abstract class Object extends Entity{

    public int x, y;
    public int X, Y;

    Image current_image;
    int current_image_index;
    GameFrame frame;

    public enum direction{UP, DOWN, RIGHT, LEFT, NULL}

    public Object(GameFrame frame, int X, int Y){
        this.frame = frame;
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;

        //scaleImages();

        current_image = getImageList().get(0);
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(current_image,
                (int)((x - frame.x_map_shift)*Main.w_scale_rate),
                (int)((y - frame.y_map_shift)*Main.h_scale_rate),
                null);
    }

    public Main getMain(){
        return frame.main;
    }

    public boolean isLeftObject(Object[][] map){
        if(X-1>0){
            if (isAlignedY())
                return map[X - 1][Y] != null;
            else if (Y + 1 < Main.ABS_H_MAP_SIZE)
                return map[X - 1][Y] != null || map[X - 1][Y + 1] != null;
        }
        return true;
    }

    public boolean isRightObject(Object[][] map){
        if(X + 1 < Main.ABS_W_MAP_SIZE) {
            if (isAlignedY())
                return map[X + 1][Y] != null;
            else if (Y + 1 < Main.ABS_H_MAP_SIZE)
                return map[X + 1][Y] != null || map[X + 1][Y + 1] != null;
        }
        return true;
    }

    public boolean isUpObject(Object[][] map){

        if(Y-1>0) {
            if (isAlignedX())
                return map[X][Y - 1] != null;
            else if (X + 1 < Main.ABS_W_MAP_SIZE)
                return map[X][Y - 1] != null || map[X + 1][Y - 1] != null;
        }
        return true;
    }

    public boolean isDownObject(Object[][] map){

        if(Y+1<Main.ABS_H_MAP_SIZE) {
            if (isAlignedX())
                return map[X][Y + 1] != null;
            else if (X + 1 < Main.ABS_W_MAP_SIZE)
                return map[X][Y + 1] != null || map[X + 1][Y + 1] != null;
        }
        return true;
    }

    protected boolean isAlignedY(){
        return y % Main.RESOLUTION == 0;
    }

    protected boolean isAlignedX() {
        return x % Main.RESOLUTION == 0;
    }

    double previous_sub_time = 0;
    @Override
    void update(long time) {
        super.update(time);
        double sub_time = time*frame.frame_time % ((double)1000/(double)getImageList().size());
        if(previous_sub_time>sub_time)
            updateImage();
        previous_sub_time = sub_time;
    }

    public void updateImage(){
        current_image = getImageList().get(current_image_index);
        current_image_index = (current_image_index+ 1)%getImageList().size();
    }

    protected abstract ArrayList<Image> getImageList();
}
