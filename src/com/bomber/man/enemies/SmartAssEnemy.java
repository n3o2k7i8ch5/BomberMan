package com.bomber.man.enemies;

import com.bomber.man.*;
import com.bomber.man.Object;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


import static com.bomber.man.Object.direction.*;

public class SmartAssEnemy extends Enemy {

    public String print(){

        String s = "<html>";

        for(int i=0; i<map.length; i++) {
            for (int j = 0; j < map[0].length; j++)
                if(map[j][i]!=null) {
                    if (map[j][i].contains(EXPLOSION))
                        s += "8 ";
                    else if (map[j][i].contains(SOLID))
                        s += "9 ";
                    else if (map[j][i].contains(LOCK))
                        s += "4 ";
                    else
                        s += "0 ";
                }
            s+="<br>";
        }

        s+="</html>";
        return s;

    }

    public String safetyTarget(){

        if(safetyRoute==null)
            return"";

        String s = "<html>";

        s += "X: " + safetyRoute.X + ",Y: " + safetyRoute.Y;

        s+="</html>";
        return s;

    }

    final Character LOCK = 'L';
    //final short GRASS = 0;
    final Character EXPLOSION = 'E';
    final Character SOLID = 'S';

    ArrayList<Character>[][] map;

    private boolean check_safety = false;

    Route safetyRoute;

    public SmartAssEnemy(GameFrame frame, int x, int y) {
        super(frame,x,y,2);
        map = new ArrayList[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];
        for(int i=0; i<map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                map[i][j] = new ArrayList<>();
    }

    @Override
    public void updateStep(long time) {


        if (isAlignedX() && isAlignedY()) {

            if(!map[X][Y].contains(LOCK)) {
                new_dir = randomSafeDirection();
            }else if(safetyRoute!= null && safetyRoute.dirs.size()>0) {
                new_dir = safetyRoute.dirs.get(0);
                safetyRoute.dirs.remove(0);
            }else
                new_dir = NULL;
        }

        super.updateStep(time);
    }

    @Override
    public void onTilePositionChanged(){
        checkSafety();
        super.onTilePositionChanged();
    }

    public void explosionRemoved(int X, int Y){
        map[X][Y].remove(EXPLOSION);
        if(getObjectManager().solids[X][Y]==null)
            map[X][Y].remove(SOLID);
        checkSafety();
    }

    public void bombAdded(){
        analizeMap();
        checkSafety();
    }

    public void bombMoved(){
        analizeMap();
        checkSafety();
    }

    public void bombDetonation(int X, int Y){
        simulateExp(X, Y, frame.player.flame_length);
        checkSafety();
    }

    public void checkSafety(){
        if(map[X][Y].contains(LOCK))
            safetyRoute = findRescueRoute();
        else if(safetyRoute!= null)
            safetyRoute.dirs.clear();
    }

    private void analizeMap(){
        for(int i=0; i<map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                map[i][j].clear();

        for(Explosion explosion : getObjectManager().explosion_list)
            map[explosion.X][explosion.Y].add(EXPLOSION);

        for(Bomb bomb : getObjectManager().bomb_list)
            lockExp(bomb.X, bomb.Y, frame.player.flame_length);

        for(Object solid : getObjectManager().solid_list)
            map[solid.X][solid.Y].add(SOLID);
    }

    private Route findRescueRoute(){

        boolean[][] safety_map = new boolean[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];
        boolean[][] route_map = new boolean[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];

        for(int i=0; i<safety_map.length; i++)
            for (int j = 0; j < safety_map[0].length; j++) {
                if(map[i][j].size()==0){
                    route_map[i][j] = true;
                    safety_map[i][j] = true;
                }

                if(map[i][j].contains(LOCK)){
                    route_map[i][j] = true;
                    safety_map[i][j] = false;
                }

                if(map[i][j].contains(SOLID)){
                    route_map[i][j] = false;
                    safety_map[i][j] = false;
                }

                if(map[i][j].contains(EXPLOSION)){
                    route_map[i][j] = false;
                    safety_map[i][j] = false;
                }

            }

        route_map[X][Y] = false;

        ArrayList<Route> global_routes = new ArrayList<>();
        ArrayList<Route> routes = new ArrayList<>();

        routes.add(new Route(X, Y, new ArrayList<>()));
        while(true) {

            boolean no_places_left = true;

            for (Route route : routes) {
                int X = route.X;
                int Y = route.Y;

                if (route_map[X][Y - 1]) {

                    route_map[X][Y - 1] = false;
                    no_places_left=false;
                    if (safety_map[X][Y - 1])
                        return route.addUp();
                    else
                        global_routes.add(route.addUp());
                }

                if (route_map[X][Y + 1]) {

                    route_map[X][Y + 1] = false;
                    no_places_left=false;
                    if (safety_map[X][Y + 1])
                        return route.addDown();
                    else
                        global_routes.add(route.addDown());
                }

                if (route_map[X - 1][Y]) {

                    route_map[X - 1][Y] = false;
                    no_places_left=false;
                    if (safety_map[X - 1][Y])
                        return route.addLeft();
                    else
                        global_routes.add(route.addLeft());
                }

                if (route_map[X + 1][Y]) {

                    route_map[X + 1][Y] = false;
                    no_places_left=false;
                    if (safety_map[X + 1][Y])
                        return route.addRight();
                    else
                        global_routes.add(route.addRight());
                }
            }

            if (no_places_left)
                return null;

            routes.clear();
            routes = new ArrayList<>(global_routes);
            global_routes.clear();
        }
    }

    private void lockExp(int X, int Y, int fire_length){

        //map[X][Y].add(SOLID); //this is added in AnalizeMap

        lockExpUp(X, Y, fire_length);
        lockExpDown(X, Y, fire_length);
        lockExpRight(X, Y, fire_length);
        lockExpLeft(X, Y, fire_length);
    }

    private void lockExpUp(int X, int Y, int fire_length){
        if(Y==0 || fire_length==0)
            return;

        if(!getObjectManager().isThereSolid(X, --Y)) {
            map[X][Y].add(LOCK);
            lockExpUp(X, Y, fire_length - 1);
        }
    }
    private void lockExpDown(int X, int Y, int fire_length){
        if(Y==getMain().ABS_H_MAP_SIZE-1 || fire_length==0)
            return;

        if (!getObjectManager().isThereSolid(X, ++Y)) {
            lockExpDown(X, Y, fire_length - 1);
            map[X][Y].add(LOCK);
        }
    }
    private void lockExpRight(int X, int Y, int fire_length) {
        if(X==getMain().ABS_W_MAP_SIZE-1 || fire_length==0)
            return;

        if(!getObjectManager().isThereSolid(++X, Y)) {
            lockExpRight(X, Y, fire_length - 1);
            map[X][Y].add(LOCK);
        }
    }
    private void lockExpLeft(int X, int Y, int fire_length) {
        if(X==0 || fire_length==0)
            return;

        if (!getObjectManager().isThereSolid(--X, Y)) {
            lockExpLeft(X, Y, fire_length - 1);
            map[X][Y].add(LOCK);
        }
    }

    private void simulateExp(int X, int Y, int fire_length){
        map[X][Y].remove(SOLID);

        simulateExpUp(X, Y, fire_length);
        simulateExpDown(X, Y, fire_length);
        simulateExpRight(X, Y, fire_length);
        simulateExpLeft(X, Y, fire_length);
    }

    private void simulateExpUp(int X, int Y, int fire_length){
        if(Y==0 || fire_length==0)
            return;

        if(!getObjectManager().isThereSolid(X, --Y)) {
            map[X][Y].remove(LOCK);
            map[X][Y].add(EXPLOSION);
            simulateExpUp(X, Y, fire_length - 1);
        }
    }
    private void simulateExpDown(int X, int Y, int fire_length){
        if(Y==getMain().ABS_H_MAP_SIZE-1 || fire_length==0)
            return;

        if (!getObjectManager().isThereSolid(X, ++Y)) {
            simulateExpDown(X, Y, fire_length - 1);
            map[X][Y].remove(LOCK);
            map[X][Y].add(EXPLOSION);
        }
    }
    private void simulateExpRight(int X, int Y, int fire_length) {
        if(X==getMain().ABS_W_MAP_SIZE-1 || fire_length==0)
            return;

        if(!getObjectManager().isThereSolid(++X, Y)) {
            simulateExpRight(X, Y, fire_length - 1);
            map[X][Y].remove(LOCK);
            map[X][Y].add(EXPLOSION);
        }
    }
    private void simulateExpLeft(int X, int Y, int fire_length) {
        if(X==0 || fire_length==0)
            return;

        if (!getObjectManager().isThereSolid(--X, Y)) {
            simulateExpLeft(X, Y, fire_length - 1);
            map[X][Y].remove(LOCK);
            map[X][Y].add(EXPLOSION);
        }
    }

    protected direction randomSafeDirection() {

        ArrayList<direction> free_dirs = new ArrayList<>();
        if (isDirFreeToGo(UP) && map[X][Y-1].size()==0)
            free_dirs.add(UP);

        if (isDirFreeToGo(DOWN) && map[X][Y+1].size()==0)
            free_dirs.add(DOWN);

        if (isDirFreeToGo(RIGHT) && map[X+1][Y].size()==0)
            free_dirs.add(RIGHT);

        if (isDirFreeToGo(LEFT) && map[X-1][Y].size()==0)
            free_dirs.add(LEFT);

        if (free_dirs.size() == 0)
            return NULL;
        else {
            int r = Math.abs(new Random().nextInt()) % free_dirs.size();
            return free_dirs.get(r);
        }
    }

    @Override
    protected ArrayList<Image> getImageUpList() {
        return getMain().graphicsContainer.smartAssEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageDownList() {
        return getMain().graphicsContainer.smartAssEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageLeftList() {
        return getMain().graphicsContainer.smartAssEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageRightList() {
        return getMain().graphicsContainer.smartAssEnemyImages;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.smartAssEnemyImages;
    }

    class Route{

        int X, Y;
        ArrayList<direction> dirs;

        public Route(int X, int Y, ArrayList<direction> dirs) {
            this.X = X;
            this.Y = Y;
            this.dirs = new ArrayList<>(dirs);
        }

        Route addUp(){
            ArrayList<direction> dirs = new ArrayList<>(this.dirs);
            dirs.add(direction.UP);
            return new Route(X, Y-1, dirs);
        }

        Route addDown(){
            ArrayList<direction> dirs = new ArrayList<>(this.dirs);
            dirs.add(DOWN);
            return new Route(X, Y+1, dirs);
        }

        Route addLeft(){
            ArrayList<direction> dirs = new ArrayList<>(this.dirs);
            dirs.add(direction.LEFT);
            return new Route(X-1, Y, dirs);
        }

        Route addRight(){
            ArrayList<direction> dirs = new ArrayList<>(this.dirs);
            dirs.add(direction.RIGHT);
            return new Route(X+1, Y, dirs);
        }
    }

}
