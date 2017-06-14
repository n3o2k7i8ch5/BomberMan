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
    public ArrayList<Image> straightEnemyImages = new ArrayList<>();

    public static String POWER_UP_FLAME_PATH[] = {"res/drawables/flame.png"};
    public ArrayList<Image> powerUpFlameImages = new ArrayList<>();

    public static String POWER_UP_SPEED_PATH[] = {"res/drawables/speed.png"};
    public ArrayList<Image> powerUpSpeedImages = new ArrayList<>();

    public static String POWER_UP_BOMB_PATH[] = {"res/drawables/bombup.png"};
    public ArrayList<Image> powerUpBombImages = new ArrayList<>();

    public static String LIVING_WALL_PATH[] = {"res/drawables/livingwall.png"};
    public ArrayList<Image> livingWallImages = new ArrayList<>();

    public static String FOREST_PATH[] = {"res/drawables/forest.png"};
    public ArrayList<Image> forestImages = new ArrayList<>();

    public static String FOREST_TRANSP_PATH[]={"res/drawables/forest_transp.png"};
    public ArrayList<Image> forestTranspImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_UP_PATH[]={"res/drawables/fast_straight_enemy_up.png"};
    public ArrayList<Image> fastStraightEnemyUpImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_DOWN_PATH[]={"res/drawables/fast_straight_enemy_down.png"};
    public ArrayList<Image> fastStraightEnemyDownImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_LEFT_PATH[]={"res/drawables/fast_straight_enemy_left.png"};
    public ArrayList<Image> fastStraightEnemyLeftImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_RIGHT_PATH[]={"res/drawables/fast_straight_enemy_right.png"};
    public ArrayList<Image> fastStraightEnemyRightImages = new ArrayList<>();

    public static String MAGNET_ENEMY_PATH[]={"res/drawables/magnet_enemy_1.png", "res/drawables/magnet_enemy_2.png", "res/drawables/magnet_enemy_3.png", "res/drawables/magnet_enemy_2.png", "res/drawables/magnet_enemy_1.png"};
    public ArrayList<Image> magnetEnemyImages = new ArrayList<>();

    public static String SMARTASS_ENEMY_PATH[]={"res/drawables/smartass_enemy.png"};
    public ArrayList<Image> smartAssEnemyImages = new ArrayList<>();

    public static String CURSE_UP_PATH[]={"res/drawables/curseup.png"};
    public ArrayList<Image> curseUpImages = new ArrayList<>();

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
            straightEnemyImages.clear();
            straightEnemyImages.addAll(images);
        } else if (paths.equals(POWER_UP_FLAME_PATH)){
            powerUpFlameImages.clear();
            powerUpFlameImages.addAll(images);
        } else if (paths.equals(POWER_UP_SPEED_PATH)){
            powerUpSpeedImages.clear();
            powerUpSpeedImages.addAll(images);
        }else if(paths.equals(POWER_UP_BOMB_PATH)){
            powerUpBombImages.clear();
            powerUpBombImages.addAll(images);
        }else if(paths.equals(LIVING_WALL_PATH)){
            livingWallImages.clear();
            livingWallImages.addAll(images);
        }else if(paths.equals(FOREST_PATH)){
            forestImages.clear();
            forestImages.addAll(images);
        }else if(paths.equals(FOREST_TRANSP_PATH)){
            forestTranspImages.clear();
            forestTranspImages.addAll(images);
        }else if(paths.equals(FAST_STRAIGHT_ENEMY_UP_PATH)){
            fastStraightEnemyUpImages.clear();
            fastStraightEnemyUpImages.addAll(images);
        }else if(paths.equals(FAST_STRAIGHT_ENEMY_DOWN_PATH)) {
            fastStraightEnemyDownImages.clear();
            fastStraightEnemyDownImages.addAll(images);
        }else if(paths.equals(FAST_STRAIGHT_ENEMY_RIGHT_PATH)) {
            fastStraightEnemyRightImages.clear();
            fastStraightEnemyRightImages.addAll(images);
        }else if(paths.equals(FAST_STRAIGHT_ENEMY_LEFT_PATH)) {
            fastStraightEnemyLeftImages.clear();
            fastStraightEnemyLeftImages.addAll(images);
        }else if(paths.equals(MAGNET_ENEMY_PATH)) {
            magnetEnemyImages.clear();
            magnetEnemyImages.addAll(images);
        }else if(paths.equals(SMARTASS_ENEMY_PATH)) {
            smartAssEnemyImages.clear();
            smartAssEnemyImages.addAll(images);
        }else if(paths.equals(CURSE_UP_PATH)) {
            curseUpImages.clear();
            curseUpImages.addAll(images);
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
        loadScaleImages(FAST_STRAIGHT_ENEMY_UP_PATH);
        loadScaleImages(FAST_STRAIGHT_ENEMY_DOWN_PATH);
        loadScaleImages(FAST_STRAIGHT_ENEMY_LEFT_PATH);
        loadScaleImages(FAST_STRAIGHT_ENEMY_RIGHT_PATH);
        loadScaleImages(MAGNET_ENEMY_PATH);
        loadScaleImages(SMARTASS_ENEMY_PATH);
        loadScaleImages(CURSE_UP_PATH);

    }
}
