package com.bomber.man;

import com.bomber.man.enemies.*;
import com.bomber.man.forest.Forest;
import com.bomber.man.player.Player;
import com.bomber.man.power_ups.*;
import com.bomber.man.tiles.GrassDark;
import com.bomber.man.tiles.GrassLight;
import com.bomber.man.tiles.Tile;

import java.util.ArrayList;

import static com.bomber.man.Main.ABS_H_MAP_SIZE;
import static com.bomber.man.Main.ABS_W_MAP_SIZE;
import static com.bomber.man.Object.direction.NULL;

/**
 * Created by Kisiel on 17.04.2017.
 */
public class ObjectManager {

    private GameFrame frame;

    public ArrayList<Bomb> bomb_list = new ArrayList<>();
    public ArrayList<Tile> tile_list = new ArrayList<>();
    public ArrayList<Object> solid_list = new ArrayList<>();
    public ArrayList<LivingWall> living_wall_list = new ArrayList<>();
    public ArrayList<Explosion> explosion_list = new ArrayList<>();
    public ArrayList<Explosion> new_explosion_list = new ArrayList<>();
    public ArrayList<Enemy> enemy_list = new ArrayList<>();
    public ArrayList<SmartAssEnemy> smartass_enemy_list = new ArrayList<>();
    public ArrayList<PowerUp> powerup_list = new ArrayList<>();
    public ArrayList<Forest> forest_list = new ArrayList<>();
    public Portal portal;

    public ArrayList<Object> all_objects[][];
    public Object solids[][];

    ObjectManager(GameFrame frame){
        this.frame = frame;

        all_objects = new ArrayList[frame.main.ABS_W_MAP_SIZE][frame.main.ABS_H_MAP_SIZE];
        for(int i=0; i<frame.main.ABS_W_MAP_SIZE; i++)
            for(int j=0; j<frame.main.ABS_H_MAP_SIZE; j++)
                all_objects[i][j] = new ArrayList<>();

        solids = new Object[frame.main.ABS_W_MAP_SIZE][frame.main.ABS_H_MAP_SIZE];

    }

    /**
     * Metoda dodająca potwora poruszającego się całkowicie losowo.
     * @param X pozycja na której początkowo znajduje się potwór, wyrażona w ilości kratek.
     * @param Y pozycja na której początkowo znajduje się potwór, wyrażona w ilości kratek.
     */
    public void addRandomEnemy(int X, int Y) {
        RandomEnemy randomEnemy = new RandomEnemy(frame, X, Y,1);
        all_objects[X][Y].add(randomEnemy);
        enemy_list.add((randomEnemy));
    }

    /**
     * Metoda dodająca ściane, której nie można zniszczyć bombą.
     * @param X pozycja ściany wyrażona w ilości kratek.
     * @param Y pozycja ściany wyrażona w ilości kratek.
     */
    public void addHardWall(int X, int Y){
        Wall wall = new Wall(frame, X, Y, false);
        addSolid(wall);
    }

    /**
     * Metoda dodająca ściane, którą można zniszczyć bombą.
     * @param X pozycja ściany wyrażona w ilości kratek.
     * @param Y pozycja ściany wyrażona w ilości kratek.
     */
    public void addSoftWall(int X, int Y){
        Wall wall = new Wall(frame, X, Y, true);
        addSolid(wall);
    }

    private void addSolid(Object solid){

        all_objects[solid.X][solid.Y].add(solid);
        solids[solid.X][solid.Y] = solid;
        solid_list.add(solid);
    }

    public void addInvisibleWall(int X, int Y, boolean leaveRandomPowerUp){
        InvisibleWall invisibleWall = new InvisibleWall(frame, X, Y, leaveRandomPowerUp);
        addSolid(invisibleWall);
    }

    public boolean isThereSolid(int X, int Y){
        for(Object object: all_objects[X][Y])
            if(object.solid)
                return true;
        return false;
    }

    public void addLivingWall(int X, int Y){
        LivingWall livingWall = new LivingWall(frame, X, Y);
        addSolid(livingWall);
        living_wall_list.add(livingWall);
    }

    /**
     * Metoda usuwająca obiekt Solid.
     * @param solid obiekt do usunięcia.
     */
    public void removeSolid(Object solid){
        solid_list.remove(solid);

        solids[solid.X][solid.Y] = null;
        all_objects[solid.X][solid.Y].remove(solid);

        if(solid instanceof LivingWall)
            living_wall_list.remove(solid);

    }

    public void removeSolid(int X, int Y){
        Object solid = solids[X][Y];
        solids[X][Y] = null;
        solid_list.remove(solid);
        all_objects[X][Y].remove(solid);

        if(solid instanceof LivingWall)
            living_wall_list.remove(solid);

    }

    public void removeExplosion(int X, int Y){
        Explosion explosion = null;
        for(Explosion explosion_tmp: explosion_list)
            if(explosion_tmp.X == X && explosion_tmp.Y == Y){
                explosion = explosion_tmp;
                break;
            }

        all_objects[X][Y].remove(explosion);
        explosion_list.remove(explosion);
    }

    public void removeEnemy(Enemy enemy){
        enemy_list.remove(enemy);
        all_objects[enemy.X][enemy.Y].remove(enemy);
        if(SmartAssEnemy.class.isInstance(enemy))
            smartass_enemy_list.remove(enemy);
    }

    /**
     * Metoda dodająca bombę na planszę.
     * @param X pozycja bomby wyrażona w ilości kratek.
     * @param Y pozycja bomby wyrażona w ilości kratek.
     */
    public void addBomb(int X, int Y, Object.direction dir){
        Bomb bomb;
        if(dir==NULL)
            bomb = new Bomb(frame, X, Y);
        else
            bomb = new Bomb(frame, X, Y, dir);

        bomb_list.add(bomb);
        addSolid(bomb);
        for(SmartAssEnemy enemy : smartass_enemy_list)
            enemy.bombAdded();
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
            Explosion explosion = new Explosion(frame, X, Y, fire_length, direction);
            all_objects[X][Y].add(explosion);
            new_explosion_list.add(explosion);
        }
    }

    /**
     * Metoda dodająca prostego potwora
     * @param X pozycja na której początkowo znajduje się potwór.
     * @param Y pozycja na której początkowo znajduje się potwór.
     */
    void addStraightEnemy(int X, int Y){
        StraightEnemy straightEnemy = new StraightEnemy(frame, X, Y);
        all_objects[X][Y].add(straightEnemy);
        enemy_list.add(straightEnemy);
    }

    void addFastStraightEnemy(int X, int Y){
        FastStraightEnemy enemy = new FastStraightEnemy(frame, X, Y);
        all_objects[X][Y].add(enemy);
        enemy_list.add(enemy);
    }

    void addMagnetEnemy(int X, int Y){
        MagnetEnemy enemy = new MagnetEnemy(frame, X, Y);
        all_objects[X][Y].add(enemy);
        enemy_list.add(enemy);
    }

    void addSmartAssEnemy(int X, int Y){
        SmartAssEnemy enemy = new SmartAssEnemy(frame, X, Y);
        all_objects[X][Y].add(enemy);
        enemy_list.add(enemy);
        smartass_enemy_list.add(enemy);
    }

    /**
     * Metoda dodająca bloczki z ciemną trawą.
     * @param X pozycja bloczka, wyrażona w ilości kratek.
     * @param Y pozycja bloczka, wyrażona w ilości kratek.
     */
    void addGrassDark(int X, int Y){
        GrassDark grassDark = new GrassDark(frame, X, Y);
        tile_list.add(grassDark);
    }

    /**
     * Metoda dodająca bloczki z jasną trawą.
     * @param X pozycja bloczka, wyrażona w ilości kratek.
     * @param Y pozycja bloczka, wyrażona w ilości kratek.
     */
    void addGrassLight(int X, int Y){
        GrassLight grassLight = new GrassLight(frame, X, Y);
        tile_list.add(grassLight);
    }

    void addSpeedUp(int X, int Y){
        SpeedUp speedUp = new SpeedUp(frame, X, Y);
        all_objects[X][Y].add(speedUp);
        powerup_list.add(speedUp);
    }

    void addFlameUp(int X, int Y){
        FlameUp flameUp = new FlameUp(frame, X, Y);
        all_objects[X][Y].add(flameUp);
        powerup_list.add(flameUp);
    }

    void addBombUp(int X, int Y){
        BombUp bombUp = new BombUp(frame, X, Y);
        all_objects[X][Y].add(bombUp);
        powerup_list.add(bombUp);
    }

    void addLifeUp(int X, int Y){
        LifeUp lifeUp = new LifeUp(frame, X, Y);
        all_objects[X][Y].add(lifeUp);
        powerup_list.add(lifeUp);
    }

    void addShieldUp(int X, int Y){
        ShieldUp shieldUp = new ShieldUp(frame, X, Y);
        all_objects[X][Y].add(shieldUp);
        powerup_list.add(shieldUp);
    }

    void addThrowBombUp(int X, int Y){
        ThrowBombUp throwBombUp = new ThrowBombUp(frame, X, Y);
        all_objects[X][Y].add(throwBombUp);
        powerup_list.add(throwBombUp);
    }

    void addSlowDown(int X, int Y){
        SlowDown slowDown = new SlowDown(frame, X, Y);
        all_objects[X][Y].add(slowDown);
        powerup_list.add(slowDown);
    }

    void addInstantBomb(int X, int Y){
        InstantBomb instantBomb = new InstantBomb(frame, X, Y);
        all_objects[X][Y].add(instantBomb);
        powerup_list.add(instantBomb);
    }

    void addFlameDown(int X, int Y){
        FlameDown flameDown = new FlameDown(frame, X, Y);
        all_objects[X][Y].add(flameDown);
        powerup_list.add(flameDown);
    }

    void addDifferentDirection(int X, int Y){
        DiffrentDirection diffrentDirection = new DiffrentDirection(frame, X, Y);
        all_objects[X][Y].add(diffrentDirection);
        powerup_list.add(diffrentDirection);
    }

    void addForest(int X, int Y){
        Forest forest = new Forest(frame, X, Y);
        all_objects[X][Y].add(forest);
        forest_list.add(forest);
    }

    void addPlayer(int X, int Y){
        frame.player = new Player(frame, X, Y, 3);
        all_objects[X][Y].add(frame.player);
        frame.addKeyListener(new KeyAdapt(frame.player));
    }

    void addPortal(int X, int Y){
        portal = new Portal(frame, X, Y);
        all_objects[X][Y].add(portal);
    }

    public Object leftSolid(Object o){

        if (o.X==0)
            return null;

        if(solids[o.X-1][o.Y] != null)
            return solids[o.X-1][o.Y];

        return null;
    }

    public Object rightSolid(Object o){

        if (o.X==frame.main.ABS_W_MAP_SIZE-1)
            return null;

        if(solids[o.X+1][o.Y] != null)
            return solids[o.X+1][o.Y];

        return null;
    }

    public Object upRightSolid(Object o){
        if(o.X!=frame.main.ABS_W_MAP_SIZE-1 && o.Y!=0)
            return solids[o.X+1][o.Y-1];

        return null;
    }

    public Object upSolid(Object o){

        if (o.Y==0)
            return null;

        if(solids[o.X][o.Y-1] != null)
            return solids[o.X][o.Y-1];

        return null;
    }

    public Object upLeftSolid(Object o){

        if(o.X!=0 && o.Y!=0)
            return solids[o.X-1][o.Y-1];

        return null;
    }

    public Object downSolid(Object o){

        if (o.Y==Main.ABS_H_MAP_SIZE-1)
            return null;

        if(solids[o.X][o.Y+1] != null)
            return solids[o.X][o.Y+1];

        return null;
    }

    public Object downLeftSolid(Object o){

        if(o.X!=0 && o.Y!=Main.ABS_H_MAP_SIZE-1)
            return solids[o.X-1][o.Y+1];

        return null;
    }

    public Object downRightSolid(Object o){

        if(o.X!=Main.ABS_W_MAP_SIZE-1 && o.Y!=Main.ABS_H_MAP_SIZE-1)
            return solids[o.X+1][o.Y+1];

        return null;
    }

    public ArrayList<Object> getSurroundingObjects(int X, int Y){

        ArrayList<Object> objects = new ArrayList<>();

        if(X!=0 && Y!=0)
            objects.addAll(all_objects[X-1][Y-1]);

        if(Y!=0)
            objects.addAll(all_objects[X][Y-1]);

        if(X!=frame.main.ABS_W_MAP_SIZE-1 && Y!=0)
            objects.addAll(all_objects[X+1][Y-1]);

        if(X!=0)
            objects.addAll(all_objects[X-1][Y]);

        objects.addAll(all_objects[X][Y]);

        if(X!=frame.main.ABS_W_MAP_SIZE-1)
            objects.addAll(all_objects[X+1][Y]);

        if(X!=0 && Y!=frame.main.ABS_H_MAP_SIZE-1)
            objects.addAll(all_objects[X-1][Y+1]);

        if(Y!=frame.main.ABS_H_MAP_SIZE-1)
            objects.addAll(all_objects[X][Y+1]);

        if(X!=frame.main.ABS_W_MAP_SIZE-1 && Y!=frame.main.ABS_H_MAP_SIZE-1)
            objects.addAll(all_objects[X+1][Y+1]);

        return objects;
    }

    /**
     * Zwraca Obiekt z podanej listy, o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @param list lista, w której wyszukiwany jest Obiekt o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @return Obiekt przechowywany w podanej liście o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @return null, jeżeli podana lista nie przechowuje Obiektu o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     */

    public boolean containsInstance(ArrayList<Object> list, Class<? extends Object> clazz) {
        for (Object object : list) {
            if (clazz.isInstance(object)) {
                return true;
            }
        }
        return false;
    }
}
