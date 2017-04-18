package com.bomber.man;

import com.bomber.man.enemies.Enemy;
import com.bomber.man.power_ups.PowerUp;
import com.bomber.man.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import static com.bomber.man.Main.*;

public class GameFrame extends JPanel implements ActionListener {

    public Player player;

    public static final int frame_time = 10;
    long time = 0;

    int x_map_shift = 0;
    int y_map_shift = 0;

    public boolean pause = true;

    private Timer timer;

    public Main main;

    public ObjectManager objectManager;

    /**
     * Klasa GameFrame klasa przechowujaca aktualny stan gry.
     * @param main
     */
    public GameFrame(Main main)
    {
        //addPlayer(3,3);
        setPreferredSize(new Dimension(VISIB_MAP_SIZE*RESOLUTION, VISIB_MAP_SIZE*RESOLUTION));

        this.main = main;

        setFocusable(true);
        timer = new Timer(frame_time, this::actionPerformed);
        timer.setRepeats(true);
        timer.start();

        objectManager = new ObjectManager(this);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for(Tile tile : objectManager.tile_list)
            tile.draw(g2d);

        for(Solid solid : objectManager.solid_list)
            solid.draw(g2d);

        for(Explosion explosion : objectManager.explosion_list)
            explosion.draw(g2d);

        for(Enemy enemy : objectManager.enemy_list)
            enemy.draw(g2d);

        for(PowerUp powerUp : objectManager.powerup_list)
            powerUp.draw(g2d);

        if(player!=null)
            player.draw(g2d);

        for(Bomb bomb : objectManager.bomb_list)
            bomb.draw(g2d);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(pause)
            return;

        main.countFPS(player);

        time++;

        player.update(time);

        for(Enemy enemy : objectManager.enemy_list)
            enemy.update(time);

        for (Iterator<Bomb> it = objectManager.bomb_list.iterator(); it.hasNext(); ) {
            Bomb bomb = it.next();
            bomb.destruct_time -= frame_time;
            bomb.update(time);
            if (bomb.destruct_time == 0) {
                it.remove();
                objectManager.detonate(bomb);
            }
        }

        for (Iterator<Explosion> it = objectManager.explosion_list.iterator(); it.hasNext(); ) {
            Explosion explosion = it.next();
            if (explosion.tick()) {
                it.remove();
                objectManager.all_objects[explosion.X][explosion.Y].remove(explosion);
            }
        }

        objectManager.explosion_list.addAll(objectManager.new_explosion_list);
        objectManager.new_explosion_list.clear();

        repaint();
    }
}
