package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static com.bomber.man.Main.ABS_MAP_SIZE;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.Main.VISIB_MAP_SIZE;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class GameFrame extends JPanel implements ActionListener {

    Player player;

    ArrayList<Bomb> bomb_list = new ArrayList<>();
    ArrayList<Object> tile_list = new ArrayList<>();
    ArrayList<Wall> solid_list = new ArrayList<>();
    static Bomb[][] bombs = new Bomb[ABS_MAP_SIZE][ABS_MAP_SIZE];
    static Object[][] tiles = new Object[ABS_MAP_SIZE][ABS_MAP_SIZE];
    static Wall[][] solids = new Wall[ABS_MAP_SIZE][ABS_MAP_SIZE];

    int x_map_shift = 0;
    int y_map_shift = 0;

    private Timer timer;

    Main main;

    public GameFrame(Main main)
    {

        setPreferredSize(new Dimension(VISIB_MAP_SIZE*RESOLUTION, VISIB_MAP_SIZE*RESOLUTION));

        this.main = main;

        player = new Player(this, 5, 5, Player.PLAYER_PATH, 2, 4);

        for(int i = 0; i< ABS_MAP_SIZE; i++)
            for(int j = 0; j< ABS_MAP_SIZE; j++)
                if((i+j)%2==0)
                    tile_list.add(new Object(this, i, j, Object.GRASS_PATH));

        addWall(3, 4);
        addWall(3, 5);
        addWall(3, 6);
        addWall(3, 8); addWall(4, 8);



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

        for(int i=0; i<bomb_list.size(); i++)
            bomb_list.get(i).draw(g2d);

    }

    private void paintMap(Graphics2D g2d){

        for(Object tile : tile_list)
            tile.draw(g2d);

        for(Wall wall : solid_list)
            wall.draw(g2d);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        main.countFPS(player);

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
        Wall wall = new Wall(this, X, Y);
        solid_list.add(wall);
        solids[X][Y] = wall;
    }

    void addBomb(int X, int Y){
        Bomb bomb = new Bomb(this, X, Y);
        bomb_list.add(bomb);
        bombs[X][Y] = bomb;
    }

}
