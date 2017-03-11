package com.bomber.man;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main extends JFrame{

    private static long CLOCK;
    private static int FPS = 0;

    static final int VISIB_MAP_SIZE = 13;
    static final int CENTER_MAP = (int)Math.floor(VISIB_MAP_SIZE/2);
    static final int ABS_MAP_SIZE = 20;

    static final int RESOLUTION = 64;

    static GameFrame gameFrame;

    public static double w_scale_rate = 1;
    public static double h_scale_rate = 1;

    public static void main(String[] args) {
        // write your code here

        Main main = new Main();
        main.setTitle("Bomber Man");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame = new GameFrame(main);
        main.add(gameFrame);
        main.pack();
        main.setLocationRelativeTo(null);
        main.setVisible(true);
        main.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {

                int init_win_size = VISIB_MAP_SIZE*RESOLUTION;

                double win_width = gameFrame.getBounds().width;
                double win_height = gameFrame.getBounds().height;

                w_scale_rate = win_width/init_win_size;
                h_scale_rate = win_height/init_win_size;

                gameFrame.player.scaleImage();

                for(Bomb bomb : gameFrame.bomb_list) {
                    bomb.scaleImage();
                }

                for(Object tile : gameFrame.tile_list) {
                    tile.scaleImage();
                }
                for(Wall wall : gameFrame.solid_list) {
                    wall.scaleImage();
                }

            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }

    void countFPS(Player player){
        if(System.currentTimeMillis() - CLOCK >= 1000) {
            //setTitle("Bomber Man FPS = " + FPS + "/ up: " + player.isUpSolid(player.gameFrame.solids) + "/ down:" + player.isDownSolid(player.gameFrame.solids) + "/ left:" + player.isLeftSolid(player.gameFrame.solids) + "/ right:" + player.isRightSolid(player.gameFrame.solids));
            FPS = 0;
            CLOCK = System.currentTimeMillis();
        }else{
            FPS++;
            setTitle("Bomber Man FPS = " + FPS + "X: " + player.X + ", Y: " + player.Y + "|scale w: " + w_scale_rate + "|scale h: " + h_scale_rate + " |x_shift = " + gameFrame.x_map_shift + " |y_shift = " + gameFrame.y_map_shift);
        }
    }

}
