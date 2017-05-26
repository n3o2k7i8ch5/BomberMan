package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Klasa GraphicsContainer jest to klasa przetrzymująca wszystkie obrazki, wykorzysytwane do gry.
 *
 */
public class GraphicsContainer {

    Main main;

    static final String[] BOMB_PATH = {"res/drawables/bomb1.png","res/drawables/bomb2.png","res/drawables/bomb3.png"};
    public ArrayList<Image> bombImages = new ArrayList<>();

    static final String[] GRASS_DARK_PATH = {"res/drawables/grass.png"};
    public ArrayList<Image> grassDarkImages = new ArrayList<>();

    static final String[] GRASS_LIGHT_PATH = {"res/drawables/grass_light.png"};
    public ArrayList<Image> grassLightImages = new ArrayList<>();

    static final String[] PLAYER_UP_PATH = {"res/drawables/player_up.png"};
    public ArrayList<Image> playerUpImages = new ArrayList<>();

    static final String[] PLAYER_DOWN_PATH = {"res/drawables/player_down.png","res/drawables/player2.png","res/drawables/player3.png"};
    public ArrayList<Image> playerDownImages = new ArrayList<>();

    static final String[] PLAYER_LEFT_PATH = {"res/drawables/player_left.png"};
    public ArrayList<Image> playerLeftImages = new ArrayList<>();

    static final String[] PLAYER_RIGHT_PATH = {"res/drawables/player_right.png"};
    public ArrayList<Image> playerRightImages = new ArrayList<>();

    static final String[] ENEMY_PATH = {"res/drawables/enemy1.png","res/drawables/enemy2.png","res/drawables/enemy3.png","res/drawables/enemy4.png"};
    public ArrayList<Image> enemyImages = new ArrayList<>();

    static final String SOFT_WALL_PATH[] = {"res/drawables/wall_soft.png"};
    public ArrayList<Image> softWallImages = new ArrayList<>();

    static final String HARD_WALL_PATH[] = {"res/drawables/wall.png"};
    public ArrayList<Image> hardWallImages = new ArrayList<>();

    static final String EXPLOSION_PATH[] = {"res/drawables/explosion.png"};
    public ArrayList<Image> explosionImages = new ArrayList<>();

    public static String STRAIGHT_ENEMY_PATH[] = {"res/drawables/straightenemy.png", "res/drawables/straightenemy2.png"};
    public ArrayList<Image> straightEnemyPath = new ArrayList<>();

    public static String POWER_UP_FLAME_PATH[] = {"res/drawables/flame.png"};
    public ArrayList<Image> powerUpFlamePath = new ArrayList<>();

    public static String POWER_UP_SPEED_PATH[] = {"res/drawables/speed.png"};
    public ArrayList<Image> powerUpSpeedPath = new ArrayList<>();

    public static String POWER_UP_BOMB_PATH[] = {"res/drawables/bombup.png"};
    public ArrayList<Image> powerUpBombPath = new ArrayList<>();

    public static String LIVING_WALL_PATH[] = {"res/drawables/livingwall.png"};
    public ArrayList<Image>livingWallPath = new ArrayList<>();

    public static String FOREST_PATH[] = {"res/drawables/forest.png"};
    public ArrayList<Image>forestPath = new ArrayList<>();

    public static String FOREST_TRANSP_PATH[]={"res/drawables/forest_transp.png"};
    public ArrayList<Image>forestTranspPath = new ArrayList<>();

    GraphicsContainer(Main main){
        this.main = main;
        scaleAll();
    }

    public void loadScaleImages(String[] paths) {

        double w_scale = main.w_scale_rate;
        double h_scale = main.h_scale_rate;

        ArrayList<Image> images = new ArrayList<>();

        for (int i = 0; i < paths.length; i++) {

            BufferedImage resizedImg = new BufferedImage((int) (Math.ceil(Main.RESOLUTION * w_scale)), (int) (Math.ceil(Main.RESOLUTION * h_scale)), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(new ImageIcon(paths[i]).getImage(), 0, 0, (int) (Math.ceil(Main.RESOLUTION * w_scale)), (int) (Math.ceil(Main.RESOLUTION * h_scale)), null);
            g2.dispose();

            images.add(resizedImg);
        }

        if (paths.equals(BOMB_PATH)) {
            bombImages.clear();
            bombImages.addAll(images);
        } else if (paths.equals(GRASS_DARK_PATH)) {
            grassDarkImages.clear();
            grassDarkImages.addAll(images);
        } else if (paths.equals(GRASS_LIGHT_PATH)) {
            grassLightImages.clear();
            grassLightImages.addAll(images);
        } else if (paths.equals(PLAYER_UP_PATH)) {
            playerUpImages.clear();
            playerUpImages.addAll(images);
        } else if (paths.equals(PLAYER_DOWN_PATH)) {
            playerDownImages.clear();
            playerDownImages.addAll(images);
        } else if (paths.equals(PLAYER_LEFT_PATH)) {
            playerLeftImages.clear();
            playerLeftImages.addAll(images);
        } else if (paths.equals(PLAYER_RIGHT_PATH)) {
            playerRightImages.clear();
            playerRightImages.addAll(images);
        } else if (paths.equals(ENEMY_PATH)) {
            enemyImages.clear();
            enemyImages.addAll(images);
        } else if (paths.equals(HARD_WALL_PATH)) {
            hardWallImages.clear();
            hardWallImages.addAll(images);
        } else if (paths.equals(SOFT_WALL_PATH)) {
            softWallImages.clear();
            softWallImages.addAll(images);
        } else if (paths.equals(EXPLOSION_PATH)) {
            explosionImages.clear();
            explosionImages.addAll(images);
        } else if (paths.equals(STRAIGHT_ENEMY_PATH)){
            straightEnemyPath.clear();
            straightEnemyPath.addAll(images);
        } else if (paths.equals(POWER_UP_FLAME_PATH)){
            powerUpFlamePath.clear();
            powerUpFlamePath.addAll(images);
        } else if (paths.equals(POWER_UP_SPEED_PATH)){
            powerUpSpeedPath.clear();
            powerUpSpeedPath.addAll(images);
        }else if(paths.equals(POWER_UP_BOMB_PATH)){
            powerUpBombPath.clear();
            powerUpBombPath.addAll(images);
        }else if(paths.equals(LIVING_WALL_PATH)){
            livingWallPath.clear();
            livingWallPath.addAll(images);
        }else if(paths.equals(FOREST_PATH)){
            forestPath.clear();
            forestPath.addAll(images);
        }else if(paths.equals(FOREST_TRANSP_PATH)){
            forestTranspPath.clear();
            forestTranspPath.addAll(images);
        }
    }

    public void scaleAll(){
        loadScaleImages(LIVING_WALL_PATH);
        loadScaleImages(BOMB_PATH);
        loadScaleImages(GRASS_DARK_PATH);
        loadScaleImages(GRASS_LIGHT_PATH);
        loadScaleImages(PLAYER_UP_PATH);
        loadScaleImages(PLAYER_DOWN_PATH);
        loadScaleImages(PLAYER_LEFT_PATH);
        loadScaleImages(PLAYER_RIGHT_PATH);
        loadScaleImages(ENEMY_PATH);
        loadScaleImages(HARD_WALL_PATH);
        loadScaleImages(SOFT_WALL_PATH);
        loadScaleImages(EXPLOSION_PATH);
        loadScaleImages(STRAIGHT_ENEMY_PATH);
        loadScaleImages(POWER_UP_FLAME_PATH);
        loadScaleImages(POWER_UP_SPEED_PATH);
        loadScaleImages(POWER_UP_BOMB_PATH);
        loadScaleImages(FOREST_PATH);
        loadScaleImages(FOREST_TRANSP_PATH);

    }
}
