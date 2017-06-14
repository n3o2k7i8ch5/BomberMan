package com.bomber.man;

import com.bomber.man.menu.Menu;
import com.bomber.man.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JFrame{

    public static final int STATE_MENU = 0;
    public static final int STATE_PLAYER_DEAD = -1;
    public static final int STATE_GAME = 1;

    static final int VISIB_MAP_SIZE = 13;
    static final int CENTER_MAP = (int)Math.floor(VISIB_MAP_SIZE/2);
    public static int ABS_H_MAP_SIZE = 20;
    public static int ABS_W_MAP_SIZE = 20;

    static int INFO_WIDTH = 300;
    static final int RESOLUTION = 64;

    int gamestate;

    private GameFrame gameFrame;

    public static GraphicsContainer graphicsContainer;

    static double w_scale_rate = 1;
    static double h_scale_rate = 1;

    public InfoBox infoBox;
    static Main main;

    public static void main(String[] args) {

        main = new Main();
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        graphicsContainer = new GraphicsContainer(main);
        main.setGameState(STATE_MENU);
    }

    private static long CLOCK;
    private static int FPS = 0;
    void countFPS(){
        if(System.currentTimeMillis() - CLOCK >= 1000) {
            setTitle("Bomber Man FPS = " + FPS);
            FPS = 0;
            CLOCK = System.currentTimeMillis();
        }else{
            FPS++;
        }
    }

    /**
     * Metoda ustawiająca dany stan gry. Gdy stan gry jest równy 0 to jest menu gdy stan gry jest 1 to jest gra gdy stan gry wynosi -1 to gameover
     * @param gamestate stan gry
     */
    public void setGameState(int gamestate)
    {
        this.gamestate = gamestate;
        main.getContentPane().removeAll();

        if(gamestate == STATE_MENU)
        {
            main.setTitle("BomberMan - Menu");
            main.setSize(800,600);
            main.setResizable(false);
            main.setLocationRelativeTo(null);

            main.add(new Menu(main));

        }
        else if(gamestate == STATE_PLAYER_DEAD)
        {
            gameFrame.player.speed = 0;
        }
        else if(gamestate == STATE_GAME) {

            main.setTitle("Bomber Man");
            main.setSize(VISIB_MAP_SIZE*RESOLUTION + INFO_WIDTH, VISIB_MAP_SIZE*RESOLUTION);
            main.setResizable(true);
            main.setLocationRelativeTo(null);

            FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            main.setLayout(flowLayout);

            gameFrame = new GameFrame(main);
            gameFrame.setFocusable(true);
            gameFrame.setVisible(true);

            Scanner in = null;
            try {
                in = new Scanner(new FileReader("map1"));
                String code = in.nextLine();
                main.loadMap(code, gameFrame);
                updateStaticImages();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            main.add(gameFrame);

            infoBox = new InfoBox(gameFrame);
            main.add(infoBox);

            main.addWindowStateListener(event -> resize());
            main.addComponentListener(new ComponentListener() {
                public void componentResized(ComponentEvent e) {
                    resize();
                }
                @Override
                public void componentMoved(ComponentEvent e) {}
                @Override
                public void componentShown(ComponentEvent e) {}
                @Override
                public void componentHidden(ComponentEvent e) {}
            });

            gameFrame.pause = false;
        }
        main.setVisible(true);

    }

    static final char GRASS_KEY = '1';
    static final char GRASS_LIGHT_KEY = '2';
    static final char WALL_KEY = 'w';
    static final char SOFT_WALL_KEY = 's';
    static final char RANDOM_ENEMY_KEY = 'r';
    static final char STRAIGHT_ENEMY_KEY = 'e';
    static final char MAGNET_ENEMY_KEY = 'm';
    static final char SMARTASS_ENEMY_KEY = 'x';
    static final char PLAYER_KEY = 'p';
    static final char FOREST_KEY = 'f';

    private void loadMap(String code, GameFrame frame){
        String[] items = code.split("!");
        ABS_W_MAP_SIZE = Integer.parseInt(items[0]);
        ABS_H_MAP_SIZE = Integer.parseInt(items[1]);

        for(int i=2; i<items.length; i++){
            String elements[] = items[i].split(",");
            int X = Integer.parseInt(elements[0]);
            int Y = Integer.parseInt(elements[1]);

            switch (elements[2].charAt(0)) {
                case GRASS_KEY: //z pliku wczytuje pozycje ciemnej trawy
                    frame.objectManager.addGrassDark(X, Y);
                    break;
                case GRASS_LIGHT_KEY:   //z pliku wczytuje pozycje jasnej trawy
                    frame.objectManager.addGrassLight(X, Y);
                    break;
                case WALL_KEY:   //z pliku wczytuje niezniszczalną ściane
                    frame.objectManager.addHardWall(X, Y);
                    break;
                case SOFT_WALL_KEY: // z pliku wczytuje ściane, którą można zniszczyć
                    frame.objectManager.addSoftWall(X, Y);
                    break;
                case STRAIGHT_ENEMY_KEY: // z pliku wczytuje prostego potwora
                    frame.objectManager.addStraightEnemy(X,Y);
                    break;
                case RANDOM_ENEMY_KEY: // z pliku wczytuje losowego potwora
                    frame.objectManager.addRandomEnemy(X,Y);
                    break;
                case PLAYER_KEY: // z pliku wczytuje gracza
                    frame.objectManager.addPlayer(X, Y);
                    break;
                case MAGNET_ENEMY_KEY: // z pliku wczytuje gracza
                    frame.objectManager.addMagnetEnemy(X, Y);
                    break;
                case SMARTASS_ENEMY_KEY: // z pliku wczytuje gracza
                    frame.objectManager.addSmartAssEnemy(X, Y);
                    break;
                case FOREST_KEY: // z pliku wczytuje gracza
                    frame.objectManager.addForest(X, Y);
                    break;
            }
        }
    }

    private void resize(){
        if(gameFrame == null)
            return;
        int init_win_size = VISIB_MAP_SIZE*RESOLUTION;
        double win_width = getBounds().getWidth();
        double win_height = getBounds().getHeight();

        w_scale_rate = (win_width - INFO_WIDTH)/(init_win_size);
        h_scale_rate = win_height/init_win_size;
        graphicsContainer.scaleAll();
        updateStaticImages();

        gameFrame.setPreferredSize(new Dimension((int)win_width - INFO_WIDTH, (int)win_height));
    }

    private void updateStaticImages(){

        for(Tile tile: gameFrame.objectManager.tile_list)
            tile.updateImage();

        for(Object solid: gameFrame.objectManager.solid_list)
            solid.updateImage();
    }

}
