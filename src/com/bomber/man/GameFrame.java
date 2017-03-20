package com.bomber.man;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import static com.bomber.man.Main.*;
import static com.bomber.man.Object.direction.NULL;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class GameFrame extends JPanel implements ActionListener {

    Player player;

    public static final int frame_time = 10;
    long time = 0;

    ArrayList<Bomb> bomb_list = new ArrayList<>();
    ArrayList<Object> tile_list = new ArrayList<>();
    ArrayList<Solid> solid_list = new ArrayList<>();
    ArrayList<Explosion> explosion_list = new ArrayList<>();
    ArrayList<Explosion> new_explosion_list = new ArrayList<>();
    ArrayList<Enemy> enemy_list = new ArrayList<>();

    static Bomb[][] bombs;
    static Object[][] tiles;
    static Solid[][] solids;
    static Explosion[][] explosions;

    int x_map_shift = 0;
    int y_map_shift = 0;

    private Timer timer;

    Main main;

    public GameFrame(Main main)
    {

        setPreferredSize(new Dimension(VISIB_MAP_SIZE*RESOLUTION, VISIB_MAP_SIZE*RESOLUTION));

        this.main = main;

        player = new Player(this, 6, 5, 3, 3, Player.PLAYER_PATH, Bomb.BOMB_PATH, Explosion.EXPLOSION_PATH);

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

        for(Object tile : tile_list)
            tile.draw(g2d);

        for(Solid solid : solid_list)
            solid.draw(g2d);

        for(Explosion explosion : explosion_list)
            explosion.draw(g2d);

        for(Enemy enemy : enemy_list)
            enemy.draw(g2d);

        player.draw(g2d);

        for(Bomb bomb : bomb_list)
            bomb.draw(g2d);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        main.countFPS(player);

        time++;

        player.update(time);

        for(Enemy enemy: enemy_list)
            enemy.update(time);

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
            if (explosion.tick())
                it.remove();
        }

        for(Explosion explosion :new_explosion_list)
            explosion_list.add(explosion);
        new_explosion_list.clear();

        repaint();
    }

    void addEnemy(int X,int Y) {
        Enemy enemy = new Enemy(this, X, Y,1, 1);
        enemy_list.add((enemy));
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
        new_explosion_list.add(explosion);
        explosions[X][Y] = explosion;
    }

    void addTile(int X, int Y, String path){
        tile_list.add(new Tile(this, X, Y, path));
    }

    void setMapApsSize(){
        bombs = new Bomb[ABS_W_MAP_SIZE][ABS_H_MAP_SIZE];
        tiles = new Object[ABS_W_MAP_SIZE][ABS_H_MAP_SIZE];
        solids = new Solid[ABS_W_MAP_SIZE][ABS_H_MAP_SIZE];
        explosions = new Explosion[ABS_W_MAP_SIZE][ABS_H_MAP_SIZE];
    }
}
