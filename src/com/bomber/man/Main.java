package com.bomber.man;

import javax.swing.*;

public class Main extends JFrame{

    private static long CLOCK;
    private static int FPS = 0;

    static final int MAP_SIZE = 16;
    static final int RESOLUTION = 64;

    public static void main(String[] args) {
        // write your code here

        Main main = new Main();
        main.setSize(RESOLUTION*MAP_SIZE, RESOLUTION*MAP_SIZE);
        main.setTitle("Bomber Man");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        main.add(new GameFrame(main));
        main.setVisible(true);
    }

    private void buildJFrame(){


    }

    void countFPS(){
        if(System.currentTimeMillis() - CLOCK >= 1000) {
            setTitle("Bomber Man FPS = " + FPS);
            FPS = 0;
            CLOCK = System.currentTimeMillis();
        }else{
            FPS++;
        }
    }

}
