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

    static final String[] BOMB_PATH = {"/drawables/bomb/bomb_1.png","/drawables/bomb/bomb_2.png","/drawables/bomb/bomb_3.png", "/drawables/bomb/bomb_4.png", "/drawables/bomb/bomb_5.png"};
    public ArrayList<Image> bombImages = new ArrayList<>();

    static final String[] GRASS_DARK_PATH = {"/drawables/grass.png"};
    public ArrayList<Image> grassDarkImages = new ArrayList<>();

    static final String[] GRASS_LIGHT_PATH = {"/drawables/grass_light.png"};
    public ArrayList<Image> grassLightImages = new ArrayList<>();

    static final String[] PLAYER_UP_PATH = {"/drawables/player/player_up_1.png", "/drawables/player/player_up_2.png", "/drawables/player/player_up_1.png", "/drawables/player/player_up_3.png"};
    public ArrayList<Image> playerUpImages = new ArrayList<>();

    static final String[] PLAYER_DOWN_PATH = {"/drawables/player/player_down_1.png", "/drawables/player/player_down_2.png", "/drawables/player/player_down_1.png", "/drawables/player/player_down_3.png"};
    public ArrayList<Image> playerDownImages = new ArrayList<>();

    static final String[] PLAYER_LEFT_PATH = {"/drawables/player/player_left_1.png", "/drawables/player/player_left_2.png", "/drawables/player/player_left_1.png", "/drawables/player/player_left_3.png"};
    public ArrayList<Image> playerLeftImages = new ArrayList<>();

    static final String[] PLAYER_RIGHT_PATH = {"/drawables/player/player_right_1.png", "/drawables/player/player_right_2.png", "/drawables/player/player_right_1.png", "/drawables/player/player_right_3.png"};
    public ArrayList<Image> playerRightImages = new ArrayList<>();

    static final String[] GRAVE_PATH = {"/drawables/player/grave.png"};
    public ArrayList<Image> graveImages = new ArrayList<>();

    static final String[] RANDOM_ENEMY_UP_PATH = {"/drawables/random_enemy/random_up.png"};
    public ArrayList<Image> randomEnemyUpImages = new ArrayList<>();

    static final String[] RANDOM_ENEMY_DOWN_PATH = {"/drawables/random_enemy/random_down.png"};
    public ArrayList<Image> randomEnemyDownImages = new ArrayList<>();

    static final String[] RANDOM_ENEMY_LEFT_PATH = {"/drawables/random_enemy/random_left.png"};
    public ArrayList<Image> randomEnemyLeftImages = new ArrayList<>();

    static final String[] RANDOM_ENEMY_RIGHT_PATH = {"/drawables/random_enemy/random_right.png"};
    public ArrayList<Image> randomEnemyRightImages = new ArrayList<>();

    static final String SOFT_WALL_PATH[] = {"/drawables/wall_soft.png"};
    public ArrayList<Image> softWallImages = new ArrayList<>();

    static final String HARD_WALL_PATH[] = {"/drawables/wall.png"};
    public ArrayList<Image> hardWallImages = new ArrayList<>();

    static final String EXPLOSION_PATH[] = {"/drawables/explosion/explosion_1.png", "/drawables/explosion/explosion_2.png", "/drawables/explosion/explosion_3.png", "/drawables/explosion/explosion_4.png", "/drawables/explosion/explosion_5.png", "/drawables/explosion/explosion_6.png", "/drawables/explosion/explosion_7.png", "/drawables/explosion/explosion_8.png", "/drawables/explosion/explosion_9.png", "/drawables/explosion/explosion_10.png"};
    public ArrayList<Image> explosionImages = new ArrayList<>();

    public static String STRAIGHT_ENEMY_LEFT_PATH[] = {"/drawables/straight_enemy/straight_enemy_left.png"};
    public ArrayList<Image> straightEnemyLeftImages = new ArrayList<>();

    public static String STRAIGHT_ENEMY_RIGHT_PATH[] = {"/drawables/straight_enemy/straight_enemy_right.png"};
    public ArrayList<Image> straightEnemyRightImages = new ArrayList<>();

    public static String POWER_UP_FLAME_PATH[] = {"/drawables/power_up/flame_up.png"};
    public ArrayList<Image> powerUpFlameImages = new ArrayList<>();

    public static String POWER_UP_SPEED_PATH[] = {"/drawables/power_up/speed_up.png"};
    public ArrayList<Image> powerUpSpeedImages = new ArrayList<>();

    public static String POWER_UP_BOMB_PATH[] = {"/drawables/power_up/bomb_up.png"};
    public ArrayList<Image> powerUpBombImages = new ArrayList<>();

    public static String POWER_UP_LIFE_PATH[] = {"/drawables/power_up/life_up.png"};
    public ArrayList<Image> powerUpLifeImages = new ArrayList<>();

    public static String POWER_UP_SHIELD_PATH[] = {"/drawables/power_up/shield_up.png"};
    public ArrayList<Image> powerUpShieldImages = new ArrayList<>();

    public static String POWER_UP_THROW_BOMB_PATH[] = {"/drawables/power_up/throw_bomb_up/throw_bomb_up_1.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_2.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_3.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_4.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_5.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_6.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_7.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_8.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_9.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_10.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_11.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_12.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_13.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_14.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_15.png",
            "/drawables/power_up/throw_bomb_up/throw_bomb_up_16.png"};
    public ArrayList<Image> powerUpThrowBombImages = new ArrayList<>();

    public static String LIVING_WALL_PATH[] = {"/drawables/livingwall.png"};
    public ArrayList<Image> livingWallImages = new ArrayList<>();

    public static String FOREST_PATH[] = {"/drawables/forest.png"};
    public ArrayList<Image> forestImages = new ArrayList<>();

    public static String FOREST_TRANSP_PATH[]={"/drawables/forest_transp.png"};
    public ArrayList<Image> forestTranspImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_UP_PATH[]={"/drawables/fast_straight_enemy_up.png"};
    public ArrayList<Image> fastStraightEnemyUpImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_DOWN_PATH[]={"/drawables/fast_straight_enemy_down.png"};
    public ArrayList<Image> fastStraightEnemyDownImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_LEFT_PATH[]={"/drawables/fast_straight_enemy_left.png"};
    public ArrayList<Image> fastStraightEnemyLeftImages = new ArrayList<>();

    public static String FAST_STRAIGHT_ENEMY_RIGHT_PATH[]={"/drawables/fast_straight_enemy_right.png"};
    public ArrayList<Image> fastStraightEnemyRightImages = new ArrayList<>();

    public static String MAGNET_ENEMY_PATH[]={"/drawables/magnet_enemy_1.png", "/drawables/magnet_enemy_2.png", "/drawables/magnet_enemy_3.png", "/drawables/magnet_enemy_2.png", "/drawables/magnet_enemy_1.png"};
    public ArrayList<Image> magnetEnemyImages = new ArrayList<>();

    public static String SMARTASS_ENEMY_PATH[]={"/drawables/smartass_enemy.png"};
    public ArrayList<Image> smartAssEnemyImages = new ArrayList<>();

    public static String CURSE_UP_PATH[]={"/drawables/power_up/curse_up.png"};
    public ArrayList<Image> curseUpImages = new ArrayList<>();

    public static String PORTAL_PATH[]={"/drawables/portal_1.png", "/drawables/portal_2.png", "/drawables/portal_3.png", "/drawables/portal_4.png", "/drawables/portal_5.png", "/drawables/portal_6.png", "/drawables/portal_7.png", "/drawables/portal_8.png", "/drawables/portal_9.png"};
    public ArrayList<Image> portalImages = new ArrayList<>();

    public static String PORTAL_LOCKED_PATH[]={"/drawables/portal_locked.png"};
    public ArrayList<Image> portalLockedImages = new ArrayList<>();

    public ArrayList<Image> emptyImages = new ArrayList<>();

    GraphicsContainer(Main main){
        this.main = main;
        emptyImages.add(null);
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

            ImageIcon imageIcon = new ImageIcon(Main.class.getResource(paths[i]));
            g2.drawImage(imageIcon.getImage(), 0, 0, (int) (Math.ceil(Main.RESOLUTION * w_scale)), (int) (Math.ceil(Main.RESOLUTION * h_scale)), null);
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
        } else if (paths.equals(GRAVE_PATH)) {
            graveImages.clear();
            graveImages.addAll(images);
        } else if (paths.equals(RANDOM_ENEMY_UP_PATH)) {
            randomEnemyUpImages.clear();
            randomEnemyUpImages.addAll(images);
        } else if (paths.equals(RANDOM_ENEMY_DOWN_PATH)) {
            randomEnemyDownImages.clear();
            randomEnemyDownImages.addAll(images);
        } else if (paths.equals(RANDOM_ENEMY_LEFT_PATH)) {
            randomEnemyLeftImages.clear();
            randomEnemyLeftImages.addAll(images);
        } else if (paths.equals(RANDOM_ENEMY_RIGHT_PATH)) {
            randomEnemyRightImages.clear();
            randomEnemyRightImages.addAll(images);
        } else if (paths.equals(HARD_WALL_PATH)) {
            hardWallImages.clear();
            hardWallImages.addAll(images);
        } else if (paths.equals(SOFT_WALL_PATH)) {
            softWallImages.clear();
            softWallImages.addAll(images);
        } else if (paths.equals(EXPLOSION_PATH)) {
            explosionImages.clear();
            explosionImages.addAll(images);
        } else if (paths.equals(STRAIGHT_ENEMY_LEFT_PATH)){
            straightEnemyLeftImages.clear();
            straightEnemyLeftImages.addAll(images);
        } else if (paths.equals(STRAIGHT_ENEMY_RIGHT_PATH)){
            straightEnemyRightImages.clear();
            straightEnemyRightImages.addAll(images);
        } else if (paths.equals(POWER_UP_FLAME_PATH)){
            powerUpFlameImages.clear();
            powerUpFlameImages.addAll(images);
        } else if (paths.equals(POWER_UP_SPEED_PATH)){
            powerUpSpeedImages.clear();
            powerUpSpeedImages.addAll(images);
        }else if(paths.equals(POWER_UP_BOMB_PATH)){
            powerUpBombImages.clear();
            powerUpBombImages.addAll(images);
        }else if(paths.equals(POWER_UP_LIFE_PATH)) {
            powerUpLifeImages.clear();
            powerUpLifeImages.addAll(images);
        }else if(paths.equals(POWER_UP_SHIELD_PATH)){
            powerUpShieldImages.clear();
            powerUpShieldImages.addAll(images);
        }else if(paths.equals(POWER_UP_THROW_BOMB_PATH)){
            powerUpThrowBombImages.clear();
            powerUpThrowBombImages.addAll(images);
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
        }else if(paths.equals(PORTAL_PATH)){
            portalImages.clear();
            portalImages.addAll(images);
        }else if(paths.equals(PORTAL_LOCKED_PATH)){
            portalLockedImages.clear();
            portalLockedImages.addAll(images);
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
        loadScaleImages(GRAVE_PATH);
        loadScaleImages(RANDOM_ENEMY_UP_PATH);
        loadScaleImages(RANDOM_ENEMY_DOWN_PATH);
        loadScaleImages(RANDOM_ENEMY_LEFT_PATH);
        loadScaleImages(RANDOM_ENEMY_RIGHT_PATH);
        loadScaleImages(HARD_WALL_PATH);
        loadScaleImages(SOFT_WALL_PATH);
        loadScaleImages(EXPLOSION_PATH);
        loadScaleImages(STRAIGHT_ENEMY_LEFT_PATH);
        loadScaleImages(STRAIGHT_ENEMY_RIGHT_PATH);
        loadScaleImages(POWER_UP_FLAME_PATH);
        loadScaleImages(POWER_UP_SPEED_PATH);
        loadScaleImages(POWER_UP_BOMB_PATH);
        loadScaleImages(POWER_UP_LIFE_PATH);
        loadScaleImages(POWER_UP_SHIELD_PATH);
        loadScaleImages(POWER_UP_THROW_BOMB_PATH);
        loadScaleImages(FOREST_PATH);
        loadScaleImages(FOREST_TRANSP_PATH);
        loadScaleImages(FAST_STRAIGHT_ENEMY_UP_PATH);
        loadScaleImages(FAST_STRAIGHT_ENEMY_DOWN_PATH);
        loadScaleImages(FAST_STRAIGHT_ENEMY_LEFT_PATH);
        loadScaleImages(FAST_STRAIGHT_ENEMY_RIGHT_PATH);
        loadScaleImages(MAGNET_ENEMY_PATH);
        loadScaleImages(SMARTASS_ENEMY_PATH);
        loadScaleImages(CURSE_UP_PATH);
        loadScaleImages(PORTAL_PATH);
        loadScaleImages(PORTAL_LOCKED_PATH);

    }
}
