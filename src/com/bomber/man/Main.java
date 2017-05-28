package com.bomber.man;

import com.bomber.man.menu.Menu;
import com.bomber.man.tiles.Tile;
import com.sun.org.apache.regexp.internal.RE;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.*;
import java.util.Scanner;

public class Main extends JFrame{

    static final int VISIB_MAP_SIZE = 13;
    static final int CENTER_MAP = (int)Math.floor(VISIB_MAP_SIZE/2);
    public static int ABS_H_MAP_SIZE = 20;
    public static int ABS_W_MAP_SIZE = 20;

    public static int INFO_WIDTH = 300;

    static final int RESOLUTION = 64;

    int gamestate = 0;

    private GameFrame gameFrame;
    private Menu menuFrame;

    public static GraphicsContainer graphicsContainer;

    static double w_scale_rate = 1;
    static double h_scale_rate = 1;

    public InfoBox infoBox;

    public static void main(String[] args) {
        // write your code here

        Main main = new Main();
        graphicsContainer = new GraphicsContainer(main);
        main.setGameState(1, main);
    }


    private static long CLOCK;
    private static int FPS = 0;
    void countFPS(Player player){
        if(System.currentTimeMillis() - CLOCK >= 1000) {
            setTitle("Bomber Man FPS = " + FPS);
            FPS = 0;
            CLOCK = System.currentTimeMillis();
        }else{
            FPS++;
        }
    }

    public void setGameState(int gamestate)
    {
        setGameState(gamestate, this);
    }

    /**
     * Metoda ustawiająca dany stan gry. Gdy stan gry jest równy 0 to jest menu gdy stan gry jest 1 to jest gra gdy stan gry wynosi -1 to gameover
     * @param gamestate stan gry
     * @param main
     */
    private void setGameState(int gamestate, Main main)
    {

        main.setTitle("Bomber Man");
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.gamestate = gamestate;
        if(gamestate == 0)
        {
            menuFrame = new Menu(main);
            main.add(menuFrame);
            main.setTitle("BomberMan - Menu");
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setSize(800,600);
            main.setLocationRelativeTo(null);
            main.setResizable(false);
            main.setVisible(true);
        }
        else if(gamestate == -1)
        {
            gameFrame.player.speed = 0;
        }
        else if(gamestate == 1) {
            main.setSize(new Dimension(VISIB_MAP_SIZE*RESOLUTION + INFO_WIDTH, VISIB_MAP_SIZE*RESOLUTION));

            FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            main.setLayout(flowLayout);
            main.setLocationRelativeTo(null);

            gameFrame = new GameFrame(main);

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

            main.setVisible(true);

            gameFrame.pause = false;
        }

    }

    private void loadMap(String code, GameFrame frame){
        String[] items = code.split("!");
        ABS_W_MAP_SIZE = Integer.parseInt(items[0]);
        ABS_H_MAP_SIZE = Integer.parseInt(items[1]);

        for(int i=2; i<items.length; i++){
            String elements[] = items[i].split(",");
            int X = Integer.parseInt(elements[0]);
            int Y = Integer.parseInt(elements[1]);

            switch (elements[2].charAt(0)) {
                case '1': //z pliku wczytuje pozycje ciemnej trawy
                    frame.objectManager.addGrassDark(X, Y);
                    break;
                case '2':   //z pliku wczytuje pozycje jasnej trawy
                    frame.objectManager.addGrassLight(X, Y);
                    break;
                case 'w':   //z pliku wczytuje niezniszczalną ściane
                    frame.objectManager.addHardWall(X, Y);
                    break;
                case 's': // z pliku wczytuje ściane, którą można zniszczyć
                    frame.objectManager.addSoftWall(X, Y);
                    break;
                case 'r': // z pliku wczytuje prostego potwora
                    frame.objectManager.addStraightEnemy(X,Y);
                    break;
                case 'e': // z pliku wczytuje losowego potwora
                    frame.objectManager.addRandomEnemy(X,Y);
                    break;
                case 'p': // z pliku wczytuje gracza
                    frame.objectManager.addPlayer(X, Y);
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
