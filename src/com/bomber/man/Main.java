package com.bomber.man;

import com.bomber.man.tiles.Tile;
import sun.misc.IOUtils;

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

    public static GraphicsContainer graphicsContainer;

    static double w_scale_rate = 1;
    static double h_scale_rate = 1;

    public static void main(String[] args) {
        // write your code here

        Main main = new Main();
        graphicsContainer = new GraphicsContainer(main);
        main.setGameState(0, main);
    }

    void countFPS(Player player){
        if(System.currentTimeMillis() - CLOCK >= 1000) {
            setTitle("Bomber Man FPS = " + FPS + " x:" + player.x + " y:" + player.y);
            FPS = 0;
            CLOCK = System.currentTimeMillis();
        }else
            FPS++;

    }

    void setGameState(int gamestate)
    {
        setGameState(gamestate, this);
    }

    private void setGameState(int gamestate, Main main)
    {

        main.setTitle("Bomber Man");
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.gamestate = gamestate;
        if(gamestate == 0)
        {

           /* Menu menu = new Menu(main);
            JFrame framemenu = new JFrame();
            framemenu.setTitle("BomberMan - Menu");
            framemenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            framemenu.setSize(800,600);
            framemenu.setLocationRelativeTo(null);

            framemenu.setVisible(true);
            framemenu.add(menu);
            */
        }

        if(gamestate == -1)
        {
            gameFrame.player.speed = 0;
        }

        if(gamestate == 0) {
            gameFrame = new GameFrame(main);
            main.add(gameFrame);
            main.pack();
            main.setLocationRelativeTo(null);
            main.setVisible(true);

            try(BufferedReader br = new BufferedReader(new FileReader("map1"))) {

                Scanner in = new Scanner(new FileReader("map1"));
                String code = in.nextLine();

                main.loadMap(code, gameFrame);
                gameFrame.addEnemy(3, 3);
                updateStaticImages();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        main.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {

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
        frame.setMapApsSize();

        for(int i=2; i<items.length; i++){
            String elements[] = items[i].split(",");
            int X = Integer.parseInt(elements[0]);
            int Y = Integer.parseInt(elements[1]);

            switch (elements[2].charAt(0)) {
                case '1':
                    frame.addGrassDark(X, Y);
                    break;
                case '2':
                    frame.addGrassLight(X, Y);
                    break;
                case 'w':
                    frame.addHardWall(X, Y);
                    break;
                case 's':
                    frame.addSoftWall(X, Y);
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
