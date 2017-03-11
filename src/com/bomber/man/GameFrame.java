package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import static com.bomber.man.Main.ABS_MAP_SIZE;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.Main.VISIB_MAP_SIZE;
import static com.bomber.man.Object.direction.NULL;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class GameFrame extends JPanel implements ActionListener {

    Player player;

    static final int frame_time = 10;

    ArrayList<Bomb> bomb_list = new ArrayList<>();
    ArrayList<Object> tile_list = new ArrayList<>();
    ArrayList<Solid> solid_list = new ArrayList<>();
    ArrayList<Explosion> explosion_list = new ArrayList<>();

    static Bomb[][] bombs = new Bomb[ABS_MAP_SIZE][ABS_MAP_SIZE];
    static Object[][] tiles = new Object[ABS_MAP_SIZE][ABS_MAP_SIZE];
    static Solid[][] solids = new Solid[ABS_MAP_SIZE][ABS_MAP_SIZE];
    static Explosion[][] explosions = new Explosion[ABS_MAP_SIZE][ABS_MAP_SIZE];

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
                else
                    tile_list.add(new Object(this, i, j, Object.GRASS_LIGHT_PATH));


        addSoftWall(3, 4);
        addSoftWall(3, 5);
        addSoftWall(3, 6);
        addSoftWall(3, 8); addSoftWall(4, 8);



        setFocusable(true);
        addKeyListener(new KeyAdapt(player));
        timer = new Timer(frame_time, this::actionPerformed);
        timer.setRepeats(true);
        timer.start();

    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        paintMap(g2d);
        player.draw(g2d);

        for(Bomb bomb : bomb_list)
            bomb.draw(g2d);

    }

    private void paintMap(Graphics2D g2d){

        for(Object tile : tile_list)
            tile.draw(g2d);

        for(Solid solid : solid_list)
            solid.draw(g2d);

        for(Explosion explosion : explosion_list)
            explosion.draw(g2d);

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

        for (Iterator<Bomb> it = bomb_list.iterator(); it.hasNext(); ) {
            Bomb bomb = it.next();
            bomb.destruct_time -= frame_time;
            if (bomb.destruct_time == 0) {
                it.remove();
                detonate(bomb);
            }
        }

        for (Iterator<Explosion> it = explosion_list.iterator(); it.hasNext(); ) {
            Explosion explosion = it.next();
            explosion.life_time -= frame_time;
            if (explosion.life_time == 0) {
                it.remove();
            }
        }

        repaint();
    }

    void addHardWall(int X, int Y){
        Wall wall = new Wall(this, X, Y, false);
        solid_list.add(wall);
        solids[X][Y] = wall;
    }

    void addSoftWall(int X, int Y){
        Wall wall = new Wall(this, X, Y, true);
        solid_list.add(wall);
        solids[X][Y] = wall;
    }

    void removeSolid(Solid solid){
        solid_list.remove(solid);
        solids[solid.X][solid.Y] = null;
    }

    void addBomb(int X, int Y, int fire_length){
        if(bombs[X][Y]!=null)
            return;

        Bomb bomb = new Bomb(this, X, Y, fire_length);
        bomb_list.add(bomb);
        bombs[X][Y] = bomb;
    }

    void detonate(Bomb bomb){
        bomb_list.remove(bomb);
        bombs[bomb.X][bomb.Y] = null;
        addExplosion(bomb.X, bomb.Y, bomb.fire_length, NULL);
    }

    void addExplosion(int X, int Y, int fire_length, MovingObject.direction direction){
        Explosion explosion = new Explosion(this, X, Y, fire_length, direction);
        explosion_list.add(explosion);
        explosions[X][Y] = explosion;
    }
}
