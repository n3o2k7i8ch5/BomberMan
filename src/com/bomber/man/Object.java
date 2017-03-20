package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Kisiel on 07.03.2017.
 */
public abstract class Object extends Entity{

    public int x, y;
    public int X, Y;
    private String[] image_strings;

    Image current_image;
    int current_image_index;
    private ArrayList<Image> image_list = new ArrayList<>();
    GameFrame frame;

    public enum direction{UP, DOWN, RIGHT, LEFT, NULL}

    public Object(GameFrame frame, int X, int Y, String... image_strings){
        this.frame = frame;
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;
        this.image_strings = image_strings;

        loadImages();
        scaleImages();

        current_image = image_list.get(0);
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(current_image,
                (int)((x - frame.x_map_shift)*Main.w_scale_rate),
                (int)((y - frame.y_map_shift)*Main.h_scale_rate),
                null);
    }

    public void loadImages(){
        for(String string : image_strings)
            image_list.add(new ImageIcon(string).getImage());
    }

    public Main getMain(){
        return frame.main;
    }

    public void scaleImages(){
        for (int i = 0; i<image_list.size(); i++) {

            double w_scale = getMain().w_scale_rate;
            double h_scale = getMain().h_scale_rate;

            BufferedImage resizedImg = new BufferedImage((int) (Math.ceil(Main.RESOLUTION * w_scale)), (int) (Math.ceil(Main.RESOLUTION * h_scale)), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(image_list.get(i), 0, 0, (int) (Math.ceil(Main.RESOLUTION * w_scale)), (int) (Math.ceil(Main.RESOLUTION * h_scale)), null);
            g2.dispose();

            image_list.set(i, resizedImg);
        }
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
        double sub_time = time*frame.frame_time % ((double)1000/(double)image_list.size());
        if(previous_sub_time>sub_time)
            updateImage();
        previous_sub_time = sub_time;
    }

    private void updateImage(){
        current_image = image_list.get(current_image_index);
        current_image_index = (current_image_index+ 1)%image_list.size();
    }
}
