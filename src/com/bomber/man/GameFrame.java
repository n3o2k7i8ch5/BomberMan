package com.bomber.man;

import com.bomber.man.enemies.Enemy;
import com.bomber.man.enemies.RandomEnemy;
import com.bomber.man.enemies.StraightEnemy;
import com.bomber.man.power_ups.BombUp;
import com.bomber.man.power_ups.FlameUp;
import com.bomber.man.power_ups.PowerUp;
import com.bomber.man.power_ups.SpeedUp;
import com.bomber.man.tiles.GrassDark;
import com.bomber.man.tiles.GrassLight;
import com.bomber.man.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import static com.bomber.man.Main.*;
import static com.bomber.man.Object.direction.NULL;

public class GameFrame extends JPanel implements ActionListener {

    public Player player;

    public static final int frame_time = 10;
    long time = 0;

    ArrayList<Bomb> bomb_list = new ArrayList<>();
    ArrayList<Tile> tile_list = new ArrayList<>();
    ArrayList<Solid> solid_list = new ArrayList<>();
    public ArrayList<Explosion> explosion_list = new ArrayList<>();
    ArrayList<Explosion> new_explosion_list = new ArrayList<>();
    ArrayList<Enemy> enemy_list = new ArrayList<>();
    ArrayList<PowerUp> powerup_list = new ArrayList<>();

    int x_map_shift = 0;
    int y_map_shift = 0;

    public boolean pause = true;

    private Timer timer;

    Main main;

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

    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for(Tile tile : tile_list)
            tile.draw(g2d);

        for(Solid solid : solid_list)
            solid.draw(g2d);

        for(Explosion explosion : explosion_list)
            explosion.draw(g2d);

        for(Enemy enemy : enemy_list)
            enemy.draw(g2d);

        for(PowerUp powerUp : powerup_list)
            powerUp.draw(g2d);

        if(player!=null)
            player.draw(g2d);

        for(Bomb bomb : bomb_list)
            bomb.draw(g2d);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(pause)
            return;

        main.countFPS(player);

        time++;

        player.update(time);

        for(Enemy enemy : enemy_list)
            enemy.update(time);

        for (Iterator<Bomb> it = bomb_list.iterator(); it.hasNext(); ) {
            Bomb bomb = it.next();
            bomb.destruct_time -= frame_time;
            bomb.update(time);
            if (bomb.destruct_time == 0) {
                it.remove();
                detonate(bomb);
            }
        }

        explosion_list.removeIf(Explosion::tick);
        explosion_list.addAll(new_explosion_list);
        new_explosion_list.clear();

        repaint();
    }

    /**
     * Metoda dodająca potwora poruszającego się całkowicie losowo.
     * @param X pozycja na której początkowo znajduje się potwór, wyrażona w ilości kratek.
     * @param Y pozycja na której początkowo znajduje się potwór, wyrażona w ilości kratek.
     */
    void addRandomEnemy(int X, int Y) {
        RandomEnemy randomEnemy = new RandomEnemy(this, X, Y,1);
        enemy_list.add((randomEnemy));
    }

    /**
     * Metoda dodająca ściane, której nie można zniszczyć bombą.
     * @param X pozycja ściany wyrażona w ilości kratek.
     * @param Y pozycja ściany wyrażona w ilości kratek.
     */
    void addHardWall(int X, int Y){
        Wall wall = new Wall(this, X, Y, false);
        solid_list.add(wall);
    }

    /**
     * Metoda dodająca ściane, którą można zniszczyć bombą.
     * @param X pozycja ściany wyrażona w ilości kratek.
     * @param Y pozycja ściany wyrażona w ilości kratek.
     */
    void addSoftWall(int X, int Y){
        Wall wall = new Wall(this, X, Y, true);
        solid_list.add(wall);
    }

    /**
     * Metoda usuwająca obiekt Solid.
     * @param solid obiekt do usunięcia.
     */
    void removeSolid(Solid solid){
        solid_list.remove(solid);
    }

    /**
     * Metoda dodająca bombę na planszę.
     * @param X pozycja bomby wyrażona w ilości kratek.
     * @param Y pozycja bomby wyrażona w ilości kratek.
     * @param fire_length długość eksplozji
     */
    void addBomb(int X, int Y, int fire_length){
        if(player.max_bombs > bomb_list.size()) {
            Bomb bomb = new Bomb(this, X, Y, fire_length);
            bomb_list.add(bomb);
        }
    }

    /**
     * Metoda służąca do detonacji bomby.
     * @param bomb
     */
    void detonate(Bomb bomb){
        bomb_list.remove(bomb);
        addExplosion(bomb.X, bomb.Y, bomb.fire_length, NULL);
    }

    /**
     * Metoda dodająca eksplozję na określoną kratkę.
     * @param X pozycja wybuchu wyrażona w ilości kratek.
     * @param Y pozycja wybuchu wyrażona w ilości kratek.
     * @param fire_length ilość kratek, jakie zostaną jeszcze zapełnione wybuchami w kierunku direction.
     * @param direction kierunek, w jakim wywołane zostaną kolejne, rekurencyjne wybuchy.
     */
    void addExplosion(int X, int Y, int fire_length, MovingObject.direction direction){
        if(X>=0 && X < ABS_W_MAP_SIZE && Y>=0 && Y< ABS_H_MAP_SIZE) {
            Explosion explosion = new Explosion(this, X, Y, fire_length, direction);
            new_explosion_list.add(explosion);
        }
    }

    /**
     * Metoda dodająca prostego potwora
     * @param X pozycja na której początkowo znajduje się potwór.
     * @param Y pozycja na której początkowo znajduje się potwór.
     */
    void addStraightEnemy(int X, int Y){
        StraightEnemy straightEnemy = new StraightEnemy(this, X, Y, 1);
        enemy_list.add(straightEnemy);
    }

    /**
     * Metoda dodająca bloczki z ciemną trawą.
     * @param X pozycja bloczka, wyrażona w ilości kratek.
     * @param Y pozycja bloczka, wyrażona w ilości kratek.
     */
    void addGrassDark(int X, int Y){
        tile_list.add(new GrassDark(this, X, Y));
    }

    /**
     * Metoda dodająca bloczki z jasną trawą.
     * @param X pozycja bloczka, wyrażona w ilości kratek.
     * @param Y pozycja bloczka, wyrażona w ilości kratek.
     */
    void addGrassLight(int X, int Y){tile_list.add(new GrassLight(this, X, Y)); }

    void addSpeedUp(int X, int Y){
        powerup_list.add(new SpeedUp(this, X, Y));
    }

    void addFlameUp(int X, int Y){
        powerup_list.add(new FlameUp(this, X, Y));
    }

    void addBombUp(int X, int Y){
        powerup_list.add(new BombUp(this, X, Y));
    }

    void addPlayer(int X, int Y){
        player = new Player(this, X, Y, 2, 3);
        addKeyListener(new KeyAdapt(player));
    }
}
