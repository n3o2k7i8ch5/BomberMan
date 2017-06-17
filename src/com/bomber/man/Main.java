package com.bomber.man;

import com.bomber.man.client.Client;
import com.bomber.man.menu.Menu;
import com.bomber.man.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.util.Scanner;

public class Main extends JFrame{

    public static final int STATE_MENU = 0;
    public static final int STATE_PLAYER_DEAD = -1;
    public static final int NEXT_LEVEL = 1;

    static final int VISIB_MAP_SIZE = 13;
    static final int CENTER_MAP = (int)Math.floor(VISIB_MAP_SIZE/2);
    public static int ABS_H_MAP_SIZE = 20;
    public static int ABS_W_MAP_SIZE = 20;

    static int INFO_WIDTH = 300;
    static final int RESOLUTION = 64;

    int gamestate;

    private int current_level;
    private int level_count = -1;

    private GameFrame gameFrame;

    public static GraphicsContainer graphicsContainer;

    static double w_scale_rate = 1;
    static double h_scale_rate = 1;

    public InfoBox infoBox;
    static Main main;

    Client client;

    Thread downloadCurrentMap;
    Thread downloadNextMap;

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

        if(gamestate == STATE_MENU)
        {
            main.getContentPane().removeAll();

            main.setTitle("BomberMan - Menu");
            main.setSize(800,600);
            main.setResizable(false);
            main.setLocationRelativeTo(null);

            main.add(new Menu(main));

        }
        else if(gamestate == NEXT_LEVEL) {

            main.getContentPane().removeAll();

            main.setSize(VISIB_MAP_SIZE*RESOLUTION + INFO_WIDTH, VISIB_MAP_SIZE*RESOLUTION);
            main.setResizable(true);
            main.setLocationRelativeTo(null);
            FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            main.setLayout(flowLayout);
            gameFrame = new GameFrame(main);

            current_level++;
            client = new Client();
            if(client.error())
                setTitle("serverIP file error");

            downloadNextMap = nextMapDownloader(current_level);
            downloadCurrentMap = currentMapDownloader(current_level+1);
            Thread startLevel = levelStarter(current_level);

            downloadCurrentMap.start();
            try {
                downloadCurrentMap.join();
            } catch (InterruptedException e) {}

            startLevel.start();
            try {
                startLevel.join();
            } catch (InterruptedException e) {}

            main.add(gameFrame);

            if(infoBox == null)
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
            downloadNextMap.start();

        }
        else if(gamestate == STATE_PLAYER_DEAD)
        {
            gameFrame.player.speed = 0;
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
    static final char LIVING_WALL_KEY = 'l';
    static final char FAST_STRAIGHT_ENEMY_KEY = 'q';

    private void loadMap(String file_name) throws Exception{

        if(!new File(file_name).exists()) {
            setTitle("Game file error: " + file_name);
            return;
        }

        Scanner in = new Scanner(new FileReader(file_name));
        String code = in.nextLine();

        String[] items = code.split("!");
        ABS_W_MAP_SIZE = Integer.parseInt(items[0]);
        ABS_H_MAP_SIZE = Integer.parseInt(items[1]);

        gameFrame.objectManager = new ObjectManager(gameFrame);

        for(int i=2; i<items.length; i++){
            String elements[] = items[i].split(",");
            int X = Integer.parseInt(elements[0]);
            int Y = Integer.parseInt(elements[1]);

            switch (elements[2].charAt(0)) {
                case GRASS_KEY: //z pliku wczytuje pozycje ciemnej trawy
                    gameFrame.objectManager.addGrassDark(X, Y);
                    break;
                case GRASS_LIGHT_KEY:   //z pliku wczytuje pozycje jasnej trawy
                    gameFrame.objectManager.addGrassLight(X, Y);
                    break;
                case WALL_KEY:   //z pliku wczytuje niezniszczalną ściane
                    gameFrame.objectManager.addHardWall(X, Y);
                    break;
                case SOFT_WALL_KEY: // z pliku wczytuje ściane, którą można zniszczyć
                    gameFrame.objectManager.addSoftWall(X, Y);
                    break;
                case LIVING_WALL_KEY: // z pliku wczytuje ściane, którą można zniszczyć
                    gameFrame.objectManager.addLivingWall(X, Y);
                    break;
                case FAST_STRAIGHT_ENEMY_KEY: // z pliku wczytuje prostego potwora
                    gameFrame.objectManager.addFastStraightEnemy(X,Y);
                    break;
                case STRAIGHT_ENEMY_KEY: // z pliku wczytuje prostego potwora
                    gameFrame.objectManager.addStraightEnemy(X,Y);
                    break;
                case RANDOM_ENEMY_KEY: // z pliku wczytuje losowego potwora
                    gameFrame.objectManager.addRandomEnemy(X,Y);
                    break;
                case PLAYER_KEY: // z pliku wczytuje gracza
                    gameFrame.objectManager.addPlayer(X, Y);
                    break;
                case MAGNET_ENEMY_KEY: // z pliku wczytuje gracza
                    gameFrame.objectManager.addMagnetEnemy(X, Y);
                    break;
                case SMARTASS_ENEMY_KEY: // z pliku wczytuje gracza
                    gameFrame.objectManager.addSmartAssEnemy(X, Y);
                    break;
                case FOREST_KEY: // z pliku wczytuje gracza
                    gameFrame.objectManager.addForest(X, Y);
                    break;
            }
        }

        updateStaticImages();
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

    private Thread levelStarter(int level){
        return new Thread(() -> {
            try {
                loadMap("map" + Integer.toString(level));
                gameFrame.setPortal();
            } catch (Exception e) {e.printStackTrace();}
        });
    }

    private Thread currentMapDownloader(int level){
        return new Thread(() -> {
            if(level_count>level) {
                downloadNextMap.isAlive();
                downloadNextMap.interrupt();

                File map = new File("map" + Integer.toString(level));
                if (!map.exists()) {
                    client.downloadMap(level);
                }
            }
        });
    }

    private Thread nextMapDownloader(int level){
        return new Thread(() -> {
            if(level_count>level) {
                File map = new File("map" + Integer.toString(level));
                if (!map.exists()) {
                    client.downloadMap(level);
                }
            }
        });
    }

}
