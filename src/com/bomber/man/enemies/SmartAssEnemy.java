package com.bomber.man.enemies;

import com.bomber.man.*;

import java.awt.*;
import java.util.ArrayList;


import static com.bomber.man.Object.direction.*;

public class SmartAssEnemy extends Enemy {

    public String print(){

        String s = "<html>";

        for(int i=0; i<map.length; i++) {
            for (int j = 0; j < map[0].length; j++)
                s += Short.toString(map[j][i]) + " ";
            s+="<br>";
        }

        s+="</html>";
        return s;

    }

    final short LOCK = 4;
    final short GRASS = 1;
    final short EXPLOSION = 8;
    final short SOLID = 9;

    short[][] map;

    boolean check_safety = false;

    Route safetyRoute;

    public SmartAssEnemy(GameFrame frame, int x, int y, int speed) {
        super(frame,x,y,2);
        map = new short[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];
    }

    @Override
    public void updateStep(long time) {

        if (isAlignedX() && isAlignedY()) {

            if(map[X][Y]==LOCK)
                safetyRoute = findRescueRoute();

            if(safetyRoute!= null && safetyRoute.dirs.size()>0) {
                new_dir = safetyRoute.dirs.get(0);
                safetyRoute.dirs.remove(0);
            }else
                new_dir = NULL;
        }

        super.updateStep(time);
    }

    public void liftLock(int X, int Y){
        map[X][Y] = GRASS;
        analizeMap();
        checkSafety();
    }

    public void addBomb(int X, int Y){
        analizeMap();
        simulateExplosion(X, Y, frame.player.fire_length, LOCK);
        checkSafety();

    }

    public void bombDetonation(int X, int Y){
        simulateExplosion(X, Y, frame.player.fire_length, EXPLOSION);
        checkSafety();
    }

    public void checkSafety(){
        check_safety = true;
    }

    private void analizeMap(){
        for(int i=0; i<map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                if(map[i][j]!=LOCK)
                    map[i][j] = GRASS;

        for(Explosion explosion : getObjectManager().explosion_list)
            map[explosion.X][explosion.Y] = EXPLOSION;

        for(Bomb bomb : getObjectManager().bomb_list)
            simulateExplosion(bomb.X, bomb.Y, frame.player.fire_length, LOCK);

        for(Solid solid : getObjectManager().solid_list)
            map[solid.X][solid.Y] = SOLID;
    }

    private Route findRescueRoute(){

        boolean[][] safety_map = new boolean[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];
        boolean[][] route_map = new boolean[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];

        for(int i=0; i<safety_map.length; i++)
            for (int j = 0; j < safety_map[0].length; j++) {
                if(map[i][j]==GRASS){
                    route_map[i][j] = true;
                    safety_map[i][j] = true;
                }

                if(map[i][j]==SOLID){
                    route_map[i][j] = false;
                    safety_map[i][j] = false;
                }

                if(map[i][j]==EXPLOSION){
                    route_map[i][j] = false;
                    safety_map[i][j] = false;
                }

                if(map[i][j]==LOCK){
                    route_map[i][j] = true;
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

    private void simulateExplosion(int X, int Y, int fire_length, short c){

        map[X][Y] = SOLID;

        simulateExpUp(X, Y, fire_length, c);
        simulateExpDown(X, Y, fire_length, c);
        simulateExpRight(X, Y, fire_length, c);
        simulateExpLeft(X, Y, fire_length, c);
    }

    private void simulateExpUp(int X, int Y, int fire_length, short c){
        if(Y==0 || fire_length==0)
            return;

        if(!getObjectManager().containsInstance(getObjectManager().all_objects[X][--Y], Solid.class)) {
            map[X][Y] = c;
            simulateExpUp(X, Y, fire_length - 1, c);
        }
    }

    private void simulateExpDown(int X, int Y, int fire_length, short c){
        if(Y==getMain().ABS_H_MAP_SIZE-1 || fire_length==0)
            return;

        if (!getObjectManager().containsInstance(getObjectManager().all_objects[X][++Y], Solid.class)) {
            simulateExpDown(X, Y, fire_length - 1, c);
            map[X][Y] = c;
        }
    }

    private void simulateExpRight(int X, int Y, int fire_length, short c) {
        if(X==getMain().ABS_W_MAP_SIZE-1 || fire_length==0)
            return;

        if(!getObjectManager().containsInstance(getObjectManager().all_objects[++X][Y], Solid.class)) {
            simulateExpRight(X, Y, fire_length - 1, c);
            map[X][Y] = c;
        }
    }

    private void simulateExpLeft(int X, int Y, int fire_length, short c) {
        if(X==0 || fire_length==0)
            return;

        if (!getObjectManager().containsInstance(getObjectManager().all_objects[--X][Y], Solid.class)) {
            simulateExpLeft(X, Y, fire_length - 1, c);
            map[X][Y] = c;
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
