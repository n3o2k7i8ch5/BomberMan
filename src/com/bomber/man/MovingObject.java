package com.bomber.man;

import static com.bomber.man.MovingObject.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class MovingObject extends Object {

    int old_x, old_y;
    int old_X, old_Y;
    int speed;
    enum direction{UP, DOWN, RIGHT, LEFT, NULL}
    direction current_direction, new_direction;

    protected GameFrame gameFrame;

    public MovingObject(int X, int Y, String image_string, int speed, GameFrame gameFrame) {
        super(X, Y, image_string);
        this.speed = speed;
        this.gameFrame = gameFrame;
    }

    public void beforePositionChanged(){
        old_x = x;
        old_y = y;
        old_X = old_x/Main.RESOLUTION;
        old_Y = old_y/Main.RESOLUTION;
    }

    public void onPositionChanged(){
        X = x/Main.RESOLUTION;
        Y = y/Main.RESOLUTION;
    }

    public Boolean isLeftSolid(Object[][] map){
        if(X-1>0){
            if (isAlignedY())
                return map[X - 1][Y] != null;
            else if (Y + 1 < Main.MAP_SIZE)
                return map[X - 1][Y] != null || map[X - 1][Y + 1] != null;
        }
        return true;
    }

    public Boolean isRightSolid(Object[][] map){
        if(X + 1 < Main.MAP_SIZE) {
            if (isAlignedY())
                return map[X + 1][Y] != null;
            else if (Y + 1 < Main.MAP_SIZE)
                return map[X + 1][Y] != null || map[X + 1][Y + 1] != null;
        }
        return true;
    }

    public Boolean isUpSolid(Object[][] map){

        if(Y-1>0) {
            if (isAlignedX())
                return map[X][Y - 1] != null;
            else if (X + 1 < Main.MAP_SIZE)
                return map[X][Y - 1] != null || map[X + 1][Y - 1] != null;
        }
        return true;
    }

    public Boolean isDownSolid(Object[][] map){

        if(Y+1<Main.MAP_SIZE) {
            if (isAlignedX())
                return map[X][Y + 1] != null;
            else if (X + 1 < Main.MAP_SIZE)
                return map[X][Y + 1] != null || map[X + 1][Y + 1] != null;
        }
        return true;
    }

    protected boolean isAlignedY(){
        return y % Main.RESOLUTION == 0;
    }

    protected boolean isAlignedX() {
        return x % Main.RESOLUTION == 0;
    }

    public Boolean isDirectionFreeToGo(direction direction){
        if(direction==UP)
            return !isUpSolid(gameFrame.solids) || !isAlignedY();
        else if(direction==DOWN)
            return !isDownSolid(gameFrame.solids) || !isAlignedY();
        else if(direction==LEFT)
            return !isLeftSolid(gameFrame.solids) || !isAlignedX();
        else if(direction==RIGHT)
            return !isRightSolid(gameFrame.solids) || !isAlignedX();

        return null;
    }
}
