package com.bomber.man;

import com.bomber.man.enemies.Enemy;
import com.bomber.man.forest.Forest;
import com.bomber.man.power_ups.PowerUp;
import com.bomber.man.tiles.Tile;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static com.bomber.man.Main.*;

public class GameFrame extends JPanel implements ActionListener {

    public Player player;

    static final int FRAME_TIME = 10;
    public long time = 0;

    int x_map_shift = 0;
    int y_map_shift = 0;

    public boolean pause = true;

    private Timer timer;

    public Main main;

    ObjectManager objectManager;

    /**
     * Klasa GameFrame klasa przechowujaca aktualny stan gry.
     * @param main
     */
    public GameFrame(Main main)
    {
        setPreferredSize(new Dimension(VISIB_MAP_SIZE*RESOLUTION, VISIB_MAP_SIZE*RESOLUTION));
        setFocusable(true);
        setVisible(true);

        this.main = main;

        timer = new Timer(FRAME_TIME, this::actionPerformed);
        timer.setRepeats(true);
        timer.start();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        requestFocus();

        for(Tile tile : objectManager.tile_list)
            tile.draw(g2d);

        for(PowerUp powerUp : objectManager.powerup_list)
            powerUp.draw(g2d);

        if(objectManager.portal!=null)
            objectManager.portal.draw(g2d);

        if(player!=null && player.lives==0)
            player.draw(g2d);

        for(Object solid : objectManager.solid_list)
            solid.draw(g2d);

        for(Bomb bomb : objectManager.bomb_list)
            bomb.draw(g2d);

        for(Enemy enemy : objectManager.enemy_list)
            enemy.draw(g2d);

        if(player!=null && player.lives>0)
            player.draw(g2d);

        for(Explosion explosion : objectManager.explosion_list)
            explosion.draw(g2d);

        for(Forest forest : objectManager.forest_list)
            forest.draw(g2d);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timerTick();
    }

    private void timerTick(){
        if(pause)
            return;

        time++;

        for(PowerUp powerUp : objectManager.powerup_list)
            powerUp.update(time);

        if(player!=null && player.lives>0)
            player.update(time);

        for(LivingWall livingWall: new ArrayList<>(objectManager.living_wall_list))
            livingWall.update(time);

        for(Enemy enemy : objectManager.enemy_list)
            enemy.update(time);

        tickBombs();

        tickExplosions();

        checkPlayerInForestPresence();

        if(objectManager.portal!=null)
            objectManager.portal.update(time);

        for(Forest forest : objectManager.forest_list)
            forest.update(time);

        repaint();

        if(main.infoBox!= null)
            main.infoBox.update(time, player);

        if(objectManager.portal != null) {
            if (objectManager.enemy_list.size() == 0)
                objectManager.portal.locked = false;
            else
                objectManager.portal.locked = true;
        }

        if(player!=null)
            main.countFPS();
    }

    private void tickBombs(){

            for(int i=0; i<Bomb.SPEED; i++)
            {
                for(Bomb bomb : objectManager.bomb_list)
                    bomb.updateDir();

                for(Bomb bomb : objectManager.bomb_list)
                    bomb.moveBy(1);
            }

        for (Iterator<Bomb> it = objectManager.bomb_list.iterator(); it.hasNext(); ) {
            Bomb bomb = it.next();
            bomb.tick(it);
            bomb.update(time);
        }
    }

    private void tickExplosions(){

        for (Iterator<Explosion> it = objectManager.explosion_list.iterator(); it.hasNext(); ) {
            Explosion explosion = it.next();
            explosion.tick(it);
            explosion.update(time);
        }

        objectManager.explosion_list.addAll(objectManager.new_explosion_list);
        objectManager.new_explosion_list.clear();
    }

    private void checkPlayerInForestPresence(){
        for(Forest forest : objectManager.forest_list) {
            if(forest.touches(player, 1)) {
                for(Forest forest1 : objectManager.forest_list)
                    forest1.setTransp(true);
                return;
            }
        }

        for(Forest forest1 : objectManager.forest_list)
            forest1.setTransp(false);
    }

    public void setPortal(){

        ArrayList<Object> soft_walls = new ArrayList<>();

        for(Object wall: objectManager.solid_list)
            if(wall.softSolid && !(wall instanceof LivingWall))
                soft_walls.add(wall);

        if(soft_walls.size()>0) {
            int r = (Math.abs(new Random().nextInt())) % soft_walls.size();
            Object wall = soft_walls.get(r);
            objectManager.addPortal(wall.X, wall.Y);
        }
    }

}
