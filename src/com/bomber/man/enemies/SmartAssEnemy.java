package com.bomber.man.enemies;

import com.bomber.man.*;

import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;

import static com.bomber.man.Object.direction.NULL;

/**
 * Created by Murspi on 10.03.2017.
 */
public class SmartAssEnemy extends Enemy {

    boolean[][] s_map;
    boolean[][] route_map;

    Route safetyRoute;

    public String print(){

        String s = "<html>";

        for(int i=0; i<route_map.length; i++) {
            for (int j = 0; j < route_map[0].length; j++) {
                s += route_map[j][i] ? "1 " : "0 ";
            }
            s += "<br>";
        }
        s += "</html>";

        return s;
    }

    public SmartAssEnemy(GameFrame frame, int x, int y, int speed)
    {
        super(frame,x,y,speed);
        s_map = new boolean[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];
        route_map = new boolean[getMain().ABS_W_MAP_SIZE][getMain().ABS_H_MAP_SIZE];
    }

    @Override
    public void updateStep(long time) {

        if (isAlignedX() && isAlignedY()) {
            nearestSafePlace();

            if(!s_map[X][Y]) {
                safetyRoute = findRescueRoute();
                if(safetyRoute!= null && safetyRoute.dirs.size()>0) {
                    new_dir = safetyRoute.dirs.get(0);
                    safetyRoute.dirs.remove(0);
                }else
                    new_dir = NULL;
            }else{
                new_dir = NULL;
            }

        }

        super.updateStep(time);
    }

    int[] nearestSafePlace(){
        int[] coords = new int[2];

        for(int i=0; i<s_map.length; i++)
            for (int j = 0; j < s_map[0].length; j++)
                s_map[i][j] = true;

        for(Solid solid : getObjectManager().solid_list)
            s_map[solid.X][solid.Y] = false;

        for(Explosion explosion : getObjectManager().explosion_list)
            s_map[explosion.X][explosion.Y] = false;

        for(Bomb bomb : getObjectManager().bomb_list){
            simulateExplosion(bomb.X, bomb.Y, frame.player.fire_length);
        }

        return coords;
    }

    ArrayList<Route> global_routes = new ArrayList<>();
    private Route findRescueRoute(){

        global_routes.clear();
        int unchecked_places = Main.ABS_H_MAP_SIZE*Main.ABS_W_MAP_SIZE;

        for(int i=0; i<route_map.length; i++)
            for (int j = 0; j < route_map[0].length; j++)
                route_map[i][j] = true;

        for(Solid solid : getObjectManager().solid_list) {
            route_map[solid.X][solid.Y] = false;
            unchecked_places--;
        }

        int old_unchecked_places;

        route_map[X][Y] = false;

        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route(X, Y, new ArrayList<>()));
        boolean keep_searching = true;
        while(keep_searching) {

            old_unchecked_places = unchecked_places;

            for (Route route : routes) {
                int X = route.X;
                int Y = route.Y;

                if (route_map[X][Y - 1]) {

                    route_map[X][Y - 1] = false;
                    unchecked_places--;
                    if (s_map[X][Y - 1])
                        return route.addUp();
                    else
                        global_routes.add(route.addUp());
                }

                if (route_map[X][Y + 1]) {

                    route_map[X][Y + 1] = false;
                    unchecked_places--;
                    if (s_map[X][Y + 1])
                        return route.addDown();
                    else
                        global_routes.add(route.addDown());
                }

                if (route_map[X - 1][Y]) {

                    route_map[X - 1][Y] = false;
                    unchecked_places--;
                    if (s_map[X - 1][Y])
                        return route.addLeft();
                    else
                        global_routes.add(route.addLeft());
                }

                if (route_map[X + 1][Y]) {

                    route_map[X + 1][Y] = false;
                    unchecked_places--;
                    if (s_map[X + 1][Y])
                        return route.addRight();
                    else
                        global_routes.add(route.addRight());
                }
            }

            if (old_unchecked_places == unchecked_places)
                return null;

            routes.clear();
            routes = new ArrayList<>(global_routes);
            global_routes.clear();
        }
        return null;
    }

    private void simulateExplosion(int X, int Y, int fire_length){

        s_map[X][Y] = false;

        simulateExpUp(X, Y, fire_length);
        simulateExpDown(X, Y, fire_length);
        simulateExpRight(X, Y, fire_length);
        simulateExpLeft(X, Y, fire_length);
    }

    private void simulateExpUp(int X, int Y, int fire_length){
        if(Y==0 || fire_length<0)
            return;

        s_map[X][Y--] = false;

        if(!getObjectManager().containsInstance(getObjectManager().all_objects[X][Y], Solid.class))
            simulateExpUp(X, Y, fire_length - 1);
    }

    private void simulateExpDown(int X, int Y, int fire_length){
        if(Y==getMain().ABS_H_MAP_SIZE-1 || fire_length<0)
            return;

        s_map[X][Y++] = false;

        if (!getObjectManager().containsInstance(getObjectManager().all_objects[X][Y], Solid.class))
            simulateExpDown(X, Y, fire_length - 1);
    }

    private void simulateExpRight(int X, int Y, int fire_length) {
        if(X==getMain().ABS_W_MAP_SIZE-1 || fire_length<0)
            return;

        s_map[X++][Y] = false;

        if(!getObjectManager().containsInstance(getObjectManager().all_objects[X][Y], Solid.class))
            simulateExpRight(X, Y, fire_length - 1);
    }

    private void simulateExpLeft(int X, int Y, int fire_length) {
        if(X==0 || fire_length<0)
            return;

        s_map[X++][Y] = false;

        if (!getObjectManager().containsInstance(getObjectManager().all_objects[X][Y], Solid.class))
            simulateExpLeft(X, Y, fire_length - 1);
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
            dirs.add(direction.DOWN);
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
