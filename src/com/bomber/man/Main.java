package com.bomber.man;

import com.bomber.man.menu.Menu;
import com.bomber.man.tiles.Tile;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.util.Scanner;

public class Main extends JFrame{

    private static long CLOCK;
    private static int FPS = 0;

    static final int VISIB_MAP_SIZE = 13;
    static final int CENTER_MAP = (int)Math.floor(VISIB_MAP_SIZE/2);
    static int ABS_H_MAP_SIZE = 20;
    static int ABS_W_MAP_SIZE = 20;

    static final int RESOLUTION = 64;

    int gamestate = 0;

    private GameFrame gameFrame;
    private Menu menuFrame;

    public static GraphicsContainer graphicsContainer;

    static double w_scale_rate = 1;
    static double h_scale_rate = 1;

    public static void main(String[] args) {
        // write your code here

        Main main = new Main();
        graphicsContainer = new GraphicsContainer(main);
        main.setGameState(3, main);
    }

    void countFPS(Player player){
        if(System.currentTimeMillis() - CLOCK >= 1000) {
            setTitle("Bomber Man FPS = " + FPS + player.current_dir);
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
            gameFrame = new GameFrame(main);
            main.add(gameFrame);
            main.pack();
            main.setLocationRelativeTo(null);
            main.setVisible(true);

            Scanner in = null;
            try {
                in = new Scanner(new FileReader("map1"));
                String code = in.nextLine();

                main.loadMap(code, gameFrame);
                updateStaticImages();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            gameFrame.pause = false;
        }

        main.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {

                if(gameFrame == null)
                    return;

                int init_win_size = VISIB_MAP_SIZE*RESOLUTION;

                double win_width = gameFrame.getBounds().width;
                double win_height = gameFrame.getBounds().height;

                w_scale_rate = win_width/init_win_size;
                h_scale_rate = win_height/init_win_size;

                graphicsContainer.scaleAll();
                updateStaticImages();
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });


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
                    frame.addGrassDark(X, Y);
                    break;
                case '2':   //z pliku wczytuje pozycje jasnej trawy
                    frame.addGrassLight(X, Y);
                    break;
                case 'w':   //z pliku wczytuje niezniszczalną ściane
                    frame.addHardWall(X, Y);
                    break;
                case 's': // z pliku wczytuje ściane, którą można zniszczyć
                    frame.addSoftWall(X, Y);
                    break;
                case 'r': // z pliku wczytuje prostego potwora
                    frame.addStraightEnemy(X,Y);
                    break;
                case 'e': // z pliku wczytuje losowego potwora
                    frame.addRandomEnemy(X,Y);
                    break;
                case 'p': // z pliku wczytuje gracza
                    frame.addPlayer(X, Y);
                    break;

            }
        }
    }

    private void updateStaticImages(){

        for(Tile tile: gameFrame.tile_list)
            tile.updateImage();

        for(Solid solid: gameFrame.solid_list)
            solid.updateImage();
    }

}
