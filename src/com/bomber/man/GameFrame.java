package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.bomber.man.Main.MAP_SIZE;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class GameFrame extends JPanel implements ActionListener {

    Player player;

    static Object[][] tiles = new Object[MAP_SIZE][MAP_SIZE];
    static Wall[][] solids = new Wall[MAP_SIZE][MAP_SIZE];

    Main main;

    Timer timer;

    public GameFrame(Main main)
    {
        this.main = main;

        player = new Player(0, 0, Player.PLAYER_PATH, 2, this);

        for(int i = 0; i< MAP_SIZE; i++)
            for(int j = 0; j< MAP_SIZE; j++)
                if((i+j)%2==0)
                    tiles[i][j] = new Object(i, j, Object.GRASS_PATH);

        addWall(3, 4);
        addWall(3, 5);
        addWall(3, 6);
        addWall(3, 8);
        addWall(3, 9);

        setFocusable(true);
        addKeyListener(new KeyAdapt(player));
        timer = new Timer(20, this::actionPerformed);
        timer.setRepeats(true);
        timer.start();

    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        paintMap(g2d);
        player.draw(g2d);
    }

    public void paintMap(Graphics2D g2d){

        for(int i = 0; i< MAP_SIZE; i++)
            for(int j = 0; j< MAP_SIZE; j++){
                if(tiles[i][j]!=null)
                    tiles[i][j].draw(g2d);
                if(solids[i][j]!=null)
                    solids[i][j].draw(g2d);
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        main.countFPS(player);

        player.update();

        if(player.x != player.old_x || player.y != player.old_y) {
            player.beforePositionChanged();
            player.update();
            player.onPositionChanged();
        }
        else
            player.update();

        repaint();
    }

    void addWall(int X, int Y){
        solids[X][Y] = new Wall(X, Y);

    }
}
