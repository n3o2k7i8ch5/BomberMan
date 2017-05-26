package com.bomber.man;

import com.bomber.man.enemies.Enemy;
import com.bomber.man.enemies.RandomEnemy;
import com.bomber.man.enemies.StraightEnemy;
import com.bomber.man.forest.Forest;
import com.bomber.man.power_ups.BombUp;
import com.bomber.man.power_ups.FlameUp;
import com.bomber.man.power_ups.PowerUp;
import com.bomber.man.power_ups.SpeedUp;
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

    GameFrame frame;

    public ArrayList<Bomb> bomb_list = new ArrayList<>();
    public ArrayList<Tile> tile_list = new ArrayList<>();
    public ArrayList<Solid> solid_list = new ArrayList<>();
    public ArrayList<LivingWall> living_wall_list = new ArrayList<>();
    public ArrayList<Explosion> explosion_list = new ArrayList<>();
    public ArrayList<Explosion> new_explosion_list = new ArrayList<>();
    public ArrayList<Enemy> enemy_list = new ArrayList<>();
    public ArrayList<PowerUp> powerup_list = new ArrayList<>();
    public ArrayList<Forest> forest_list = new ArrayList<>();

    public ArrayList<Object> all_objects[][];
    public Solid solids[][];

    ObjectManager(GameFrame frame){
        this.frame = frame;

        all_objects = new ArrayList[frame.main.ABS_W_MAP_SIZE][frame.main.ABS_H_MAP_SIZE];
        for(int i=0; i<frame.main.ABS_W_MAP_SIZE; i++)
            for(int j=0; j<frame.main.ABS_H_MAP_SIZE; j++)
                all_objects[i][j] = new ArrayList<>();

        solids = new Solid[frame.main.ABS_W_MAP_SIZE][frame.main.ABS_H_MAP_SIZE];

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

    private void addSolid(Solid solid){
        all_objects[solid.X][solid.Y].add(solid);
        solids[solid.X][solid.Y] = solid;
        solid_list.add(solid);
    }


    public void addLivingWall(int X, int Y){
        LivingWall livingWall = new LivingWall(frame, X, Y, true);
        addSolid(livingWall);
        living_wall_list.add(livingWall);
    }

    /**
     * Metoda usuwająca obiekt Solid.
     * @param solid obiekt do usunięcia.
     */
    public void removeSolid(Solid solid){
        solid_list.remove(solid);
        all_objects[solid.X][solid.Y].remove(solid);
        solids[solid.X][solid.Y] = null;
        if(solid.getClass() == LivingWall.class)
            living_wall_list.remove(solid);
    }

    public void removePowerUp(PowerUp powerUp){
        powerup_list.remove(powerUp);
        all_objects[powerUp.X][powerUp.Y].remove(powerUp);
    }

    /**
     * Metoda dodająca bombę na planszę.
     * @param X pozycja bomby wyrażona w ilości kratek.
     * @param Y pozycja bomby wyrażona w ilości kratek.
     * @param fire_length długość eksplozji
     */
    public void addBomb(int X, int Y, int fire_length){
        if(frame.player.max_bombs > bomb_list.size()) {
            Bomb bomb = new Bomb(frame, X, Y, fire_length);
            bomb_list.add(bomb);
            addSolid(bomb);
        }
    }

    /**
     * Metoda służąca do detonacji bomby.
     * @param bomb
     */
    void detonate(Bomb bomb){
        bomb_list.remove(bomb);
        removeSolid(bomb);
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
        StraightEnemy straightEnemy = new StraightEnemy(frame, X, Y, 1);
        all_objects[X][Y].add(straightEnemy);
        enemy_list.add(straightEnemy);
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

    void addForest(int X, int Y){
        Forest forest = new Forest(frame, X, Y);
        all_objects[X][Y].add(forest);
        forest_list.add(forest);
    }

    void addPlayer(int X, int Y){
        frame.player = new Player(frame, X, Y, 1.3, 3);
        all_objects[X][Y].add(frame.player);
        frame.addKeyListener(new KeyAdapt(frame.player));
    }

    public Solid leftSolid(Object o){

        if (o.X==0)
            return null;

        if(solids[o.X-1][o.Y] != null)
            return solids[o.X-1][o.Y];

        if(!o.isAlignedY() && o.Y!=frame.main.ABS_W_MAP_SIZE-1)
            return solids[o.X-1][o.Y+1];

        return null;
    }

    public Solid rightSolid(Object o){

        if (o.X==frame.main.ABS_W_MAP_SIZE-1)
            return null;

        if(solids[o.X+1][o.Y] != null)
            return solids[o.X+1][o.Y];

        if(!o.isAlignedY() && o.Y!=frame.main.ABS_W_MAP_SIZE-1)
            return solids[o.X+1][o.Y+1];

        return null;
    }

    public Solid upSolid(Object o){

        if (o.Y==0)
            return null;

        if(solids[o.X][o.Y-1] != null)
            return solids[o.X][o.Y-1];

        if(!o.isAlignedX() && o.X!=getMain().ABS_W_MAP_SIZE-1)
            return solids[o.X+1][o.Y-1];

        return null;
    }

    public Solid downSolid(Object o){

        if (o.Y==Main.ABS_H_MAP_SIZE-1)
            return null;

        if(solids[o.X][o.Y+1] != null)
            return solids[o.X][o.Y+1];

        if(!o.isAlignedX() && o.X!=getMain().ABS_W_MAP_SIZE-1)
            return solids[o.X+1][o.Y+1];

        return null;
    }


    /**
     * Zwraca Obiekt z podanej listy, o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @param list lista, w której wyszukiwany jest Obiekt o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @return Obiekt przechowywany w podanej liście o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     * @return null, jeżeli podana lista nie przechowuje Obiektu o tych samych współrzędnych, co Obiekt, z którego wywoływana jest metoda.
     */

    public Object hereObject(ArrayList list, Object o){

        boolean isObject = false;

        ArrayList<Object> arrayList = (ArrayList<Object>) list;
        for(Object object : arrayList) {
            isObject = object.X == o.X && object.Y == o.Y;

            if(isObject)
                return object;
        }
        return null;
    }

    private Main getMain(){return frame.main;}
}
