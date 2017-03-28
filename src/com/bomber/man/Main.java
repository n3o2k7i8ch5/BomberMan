package com.bomber.man;

import com.bomber.man.tiles.Tile;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main extends JFrame{

    private static long CLOCK;
    private static int FPS = 0;

    static final int VISIB_MAP_SIZE = 13;
    static final int CENTER_MAP = (int)Math.floor(VISIB_MAP_SIZE/2);
    static int ABS_H_MAP_SIZE = 20;
    static int ABS_W_MAP_SIZE = 20;

    static final int RESOLUTION = 64;

    public int gamestate = 0;

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

    public void setGameState(int gamestate)
    {
        setGameState(gamestate, this);
    }

    private void setGameState(int gamestate, Main main)
    {

        main.setTitle("Bomber Man");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
            main.loadMap("20!20!0,0,1!0,1,2!0,2,1!0,3,2!0,4,1!0,5,2!0,6,1!0,7,2!0,8,1!0,9,2!0,10,1!0,11,2!0,12,1!0,13,2!0,14,1!0,15,2!0,16,1!0,17,2!0,18,1!0,19,2!1,0,2!1,1,1!1,2,2!1,3,1!1,4,2!1,5,1!1,6,2!1,7,1!1,8,2!1,9,1!1,10,2!1,11,1!1,12,2!1,13,1!1,14,2!1,15,1!1,16,2!1,17,1!1,18,2!1,19,1!2,0,1!2,1,2!2,2,1!2,3,2!2,4,1!2,5,2!2,6,1!2,7,2!2,8,1!2,9,2!2,10,1!2,11,2!2,12,1!2,13,2!2,14,1!2,15,2!2,16,1!2,17,2!2,18,1!2,19,2!3,0,2!3,1,1!3,2,2!3,3,1!3,4,2!3,5,1!3,6,2!3,7,1!3,8,2!3,9,1!3,10,2!3,11,1!3,12,2!3,13,1!3,14,2!3,15,1!3,16,2!3,17,1!3,18,2!3,19,1!4,0,1!4,1,2!4,2,1!4,3,2!4,4,1!4,5,2!4,6,1!4,7,2!4,8,1!4,9,2!4,10,1!4,11,2!4,12,1!4,13,2!4,14,1!4,15,2!4,16,1!4,17,2!4,18,1!4,19,2!5,0,2!5,1,1!5,2,2!5,3,1!5,4,2!5,5,1!5,6,2!5,7,1!5,8,2!5,9,1!5,10,2!5,11,1!5,12,2!5,13,1!5,14,2!5,15,1!5,16,2!5,17,1!5,18,2!5,19,1!6,0,1!6,1,2!6,2,1!6,3,2!6,4,1!6,5,2!6,6,1!6,7,2!6,8,1!6,9,2!6,10,1!6,11,2!6,12,1!6,13,2!6,14,1!6,15,2!6,16,1!6,17,2!6,18,1!6,19,2!7,0,2!7,1,1!7,2,2!7,3,1!7,4,2!7,5,1!7,6,2!7,7,1!7,8,2!7,9,1!7,10,2!7,11,1!7,12,2!7,13,1!7,14,2!7,15,1!7,16,2!7,17,1!7,18,2!7,19,1!8,0,1!8,1,2!8,2,1!8,3,2!8,4,1!8,5,2!8,6,1!8,7,2!8,8,1!8,9,2!8,10,1!8,11,2!8,12,1!8,13,2!8,14,1!8,15,2!8,16,1!8,17,2!8,18,1!8,19,2!9,0,2!9,1,1!9,2,2!9,3,1!9,4,2!9,5,1!9,6,2!9,7,1!9,8,2!9,9,1!9,10,2!9,11,1!9,12,2!9,13,1!9,14,2!9,15,1!9,16,2!9,17,1!9,18,2!9,19,1!10,0,1!10,1,2!10,2,1!10,3,2!10,4,1!10,5,2!10,6,1!10,7,2!10,8,1!10,9,2!10,10,1!10,11,2!10,12,1!10,13,2!10,14,1!10,15,2!10,16,1!10,17,2!10,18,1!10,19,2!11,0,2!11,1,1!11,2,2!11,3,1!11,4,2!11,5,1!11,6,2!11,7,1!11,8,2!11,9,1!11,10,2!11,11,1!11,12,2!11,13,1!11,14,2!11,15,1!11,16,2!11,17,1!11,18,2!11,19,1!12,0,1!12,1,2!12,2,1!12,3,2!12,4,1!12,5,2!12,6,1!12,7,2!12,8,1!12,9,2!12,10,1!12,11,2!12,12,1!12,13,2!12,14,1!12,15,2!12,16,1!12,17,2!12,18,1!12,19,2!13,0,2!13,1,1!13,2,2!13,3,1!13,4,2!13,5,1!13,6,2!13,7,1!13,8,2!13,10,2!13,11,1!13,12,2!13,13,1!13,14,2!13,15,1!13,16,2!13,17,1!13,18,2!13,19,1!14,0,1!14,1,2!14,2,1!14,3,2!14,4,1!14,5,2!14,6,1!14,7,2!14,8,1!14,9,2!14,10,1!14,11,2!14,12,1!14,13,2!14,14,1!14,15,2!14,16,1!14,17,2!14,18,1!14,19,2!15,0,2!15,1,1!15,2,2!15,3,1!15,4,2!15,5,1!15,6,2!15,7,1!15,8,2!15,9,1!15,10,2!15,11,1!15,12,2!15,13,1!15,14,2!15,15,1!15,16,2!15,17,1!15,18,2!15,19,1!16,0,1!16,1,2!16,2,1!16,3,2!16,4,1!16,5,2!16,6,1!16,7,2!16,8,1!16,9,2!16,10,1!16,11,2!16,12,1!16,13,2!16,14,1!16,15,2!16,16,1!16,17,2!16,18,1!16,19,2!17,0,2!17,1,1!17,2,2!17,3,1!17,4,2!17,5,1!17,6,2!17,7,1!17,8,2!17,9,1!17,10,2!17,11,1!17,12,2!17,13,1!17,14,2!17,15,1!17,16,2!17,17,1!17,18,2!17,19,1!18,0,1!18,1,2!18,2,1!18,3,2!18,4,1!18,5,2!18,6,1!18,7,2!18,8,1!18,9,2!18,10,1!18,11,2!18,12,1!18,13,2!18,14,1!18,15,2!18,16,1!18,17,2!18,18,1!18,19,2!19,0,2!19,1,1!19,2,2!19,3,1!19,4,2!19,5,1!19,6,2!19,7,1!19,8,2!19,9,1!19,10,2!19,11,1!19,12,2!19,13,1!19,14,2!19,15,1!19,16,2!19,17,1!19,18,2!19,19,1!0,0,w!0,2,w!0,4,w!0,6,w!0,8,w!0,10,w!0,12,w!0,14,w!0,16,w!0,18,w!2,0,w!2,2,w!2,4,w!2,6,w!2,8,w!2,10,w!2,12,w!2,14,w!2,16,w!2,18,w!4,0,w!4,2,w!4,4,w!4,6,w!4,8,w!4,10,w!4,12,w!4,14,w!4,16,w!4,18,w!6,0,w!6,2,w!6,4,w!6,6,w!6,8,w!6,10,w!6,12,w!6,14,w!6,16,w!6,18,w!8,0,w!8,2,w!8,4,w!8,6,w!8,8,w!8,10,w!8,12,w!8,14,w!8,16,w!8,18,w!10,0,w!10,2,w!10,4,w!10,6,w!10,8,w!10,10,w!10,12,w!10,14,w!10,16,w!10,18,w!12,0,w!12,2,w!12,4,w!12,6,w!12,8,w!12,10,w!12,12,w!12,14,w!12,16,w!12,18,w!14,0,w!14,2,w!14,4,w!14,6,w!14,8,w!14,10,w!14,12,w!14,14,w!14,16,w!14,18,w!16,0,w!16,2,w!16,4,w!16,6,w!16,8,w!16,10,w!16,12,w!16,14,w!16,16,w!16,18,w!18,0,w!18,2,w!18,4,w!18,6,w!18,8,w!18,10,w!18,12,w!18,14,w!18,16,w!18,18,w!7,4,s!7,5,s!7,6,s!7,7,s!7,8,s!7,9,s!6,9,s!5,9,s!4,9,s!3,9,s!2,9,s!1,9,s!7,3,s!7,2,s!7,1,s!13,5,s!13,6,s!11,6,s!11,7,s!13,7,s!13,8,s!13,9,1!13,9,s!13,10,s!13,11,s!12,11,s!11,11,s!10,11,s!9,11,s!8,11,s!7,11,s!6,11,s!5,11,s!4,11,s!3,11,s!3,12,s!3,13,s!3,14,s!",
                    gameFrame);
            gameFrame.addEnemy(3, 3);
            updateStaticImages();
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

    public void loadMap(String code, GameFrame frame){
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
