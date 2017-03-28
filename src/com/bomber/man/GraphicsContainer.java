package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Kisiel on 27.03.2017.
 */
public class GraphicsContainer {

    Main main;

    static final String[] BOMB_PATH = {"res/drawables/bomb.png"};
    public ArrayList<Image> bombImages = new ArrayList<>();

    static final String[] GRASS_DARK_PATH = {"res/drawables/grass.png"};
    public ArrayList<Image> grassDarkImages = new ArrayList<>();

    static final String[] GRASS_LIGHT_PATH = {"res/drawables/grass_light.png"};
    public ArrayList<Image> grassLightImages = new ArrayList<>();

    static final String[] PLAYER_PATH = {"res/drawables/player.png", "res/drawables/bomb.png"};
    public ArrayList<Image> playerImages = new ArrayList<>();

    static final String[] ENEMY_PATH = {"res/drawables/enemy.png"};
    public ArrayList<Image> enemyImages = new ArrayList<>();

    static final String SOFT_WALL_PATH[] = {"res/drawables/wall_soft.png"};
    public ArrayList<Image> softWallImages = new ArrayList<>();

    static String HARD_WALL_PATH[] = {"res/drawables/wall.png"};
    public ArrayList<Image> hardWallImages = new ArrayList<>();

    static final String EXPLOSION_PATH[] = {"res/drawables/explosion.png"};
    public ArrayList<Image> explosionImages = new ArrayList<>();


    GraphicsContainer(Main main){
        this.main = main;
        scaleAll();
    }

    public void loadScaleImages(String[] paths){

        double w_scale = main.w_scale_rate;
        double h_scale = main.h_scale_rate;

        ArrayList<Image> images = new ArrayList<>();

        for (int i = 0; i<paths.length; i++) {

            BufferedImage resizedImg = new BufferedImage((int) (Math.ceil(Main.RESOLUTION * w_scale)), (int) (Math.ceil(Main.RESOLUTION * h_scale)), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(new ImageIcon(paths[i]).getImage(), 0, 0, (int) (Math.ceil(Main.RESOLUTION * w_scale)), (int) (Math.ceil(Main.RESOLUTION * h_scale)), null);
            g2.dispose();

            images.add(resizedImg);
        }

        if(paths.equals(BOMB_PATH)) {
            bombImages.clear();
            bombImages.addAll(images);
        }else if(paths.equals(GRASS_DARK_PATH)) {
            grassDarkImages.clear();
            grassDarkImages.addAll(images);
        }else if(paths.equals(GRASS_LIGHT_PATH)) {
            grassLightImages.clear();
            grassLightImages.addAll(images);
        }else if(paths.equals(PLAYER_PATH)) {
            playerImages.clear();
            playerImages.addAll(images);
        }else if(paths.equals(ENEMY_PATH)) {
            enemyImages.clear();
            enemyImages.addAll(images);
        }else if(paths.equals(HARD_WALL_PATH)) {
            hardWallImages.clear();
            hardWallImages.addAll(images);
        }else if(paths.equals(SOFT_WALL_PATH)) {
            softWallImages.clear();
            softWallImages.addAll(images);
        }else if(paths.equals(EXPLOSION_PATH)) {
            explosionImages.clear();
            explosionImages.addAll(images);
        }
    }

    public void scaleAll(){
        loadScaleImages(BOMB_PATH);
        loadScaleImages(GRASS_DARK_PATH);
        loadScaleImages(GRASS_LIGHT_PATH);
        loadScaleImages(PLAYER_PATH);
        loadScaleImages(ENEMY_PATH);
        loadScaleImages(HARD_WALL_PATH);
        loadScaleImages(SOFT_WALL_PATH);
        loadScaleImages(EXPLOSION_PATH);
    }
}
