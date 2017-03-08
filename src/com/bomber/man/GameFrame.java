package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.bomber.man.Main.MAP_SIZE;
import static com.bomber.man.Main.RESOLUTION;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class GameFrame extends JPanel implements ActionListener {

    Player player;

    static Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];

    Main main;

    Timer timer;

    public GameFrame(Main main)
    {
        this.main = main;

        player = new Player(0, 0, null, 4);

        for(int i = 0; i< MAP_SIZE; i++)
            for(int j = 0; j< MAP_SIZE; j++)
                if((i+j)%2==0)
                    tiles[i][j] = new Tile(i*RESOLUTION, j*RESOLUTION, Tile.GRASS_PATH);
                else
                    tiles[i][j] = new Tile(i*RESOLUTION, j*RESOLUTION, null);

        setFocusable(true);
        addKeyListener(new KeyAdapt(player));
        timer = new Timer(10, this::actionPerformed);
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
                tiles[i][j].draw(g2d);
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        main.countFPS();
        player.update();

        if(player.x != player.old_x || player.y != player.old_y)
            player.onPositionChanged();

        repaint();
    }
}
