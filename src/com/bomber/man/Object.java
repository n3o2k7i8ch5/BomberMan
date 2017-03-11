package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class Object extends Entity{

    public double x, y;
    public int X, Y;
    public Image image;
    GameFrame frame;

    static final String GRASS_PATH = "res/drawables/grass.png";

    public Object(GameFrame frame, int X, int Y, String image_string){
        this.frame = frame;
        this.X = X;
        this.Y = Y;
        this.x = X*Main.RESOLUTION;
        this.y = Y*Main.RESOLUTION;

        ImageIcon ic = new ImageIcon(image_string);
        image = ic.getImage();
        scaleImage();
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(image,
                (int)((x - frame.x_map_shift)*Main.w_scale_rate),
                (int)((y - frame.y_map_shift)*Main.h_scale_rate),
                null);
    }

    public Main getMain(){
        return frame.main;
    }

    public void scaleImage(){
        double w_scale = getMain().w_scale_rate;
        double h_scale = getMain().h_scale_rate;

        BufferedImage resizedImg = new BufferedImage((int)(Math.ceil(Main.RESOLUTION*w_scale)), (int)(Math.ceil(Main.RESOLUTION*h_scale)), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, (int)(Math.ceil(Main.RESOLUTION*w_scale)), (int)(Math.ceil(Main.RESOLUTION*h_scale)), null);
        g2.dispose();

        image = resizedImg;
    }
}
