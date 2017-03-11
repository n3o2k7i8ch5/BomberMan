package com.bomber.man;

import static com.bomber.man.MovingObject.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class MovingObject extends Object {

    double old_x, old_y;
    private int old_X, old_Y;
    double speed;
    int align_factor;
    enum direction{UP, DOWN, RIGHT, LEFT, NULL}
    direction current_direction, new_direction;

    public MovingObject(GameFrame frame, int X, int Y, String image_string, double speed) {
        super(frame, X, Y, image_string);
        this.speed = speed;
        this.align_factor = 1;
    }

    public MovingObject(GameFrame frame, int X, int Y, String image_string, double speed, int align_factor) {
        super(frame, X, Y, image_string);
        this.speed = speed;
        this.align_factor = align_factor;
    }

    public void beforePositionChanged(){
        old_x = x;
        old_y = y;
        old_X = (int)(old_x/Main.RESOLUTION);
        old_Y = (int)(old_y/Main.RESOLUTION);
    }

    public void onPositionChanged(){
        X = (int)(x/Main.RESOLUTION);
        Y = (int)(y/Main.RESOLUTION);
    }

    @Override
    void update() {
        super.update();

        if(x % (Main.RESOLUTION/align_factor) == 0 && y % (Main.RESOLUTION/align_factor) == 0)
            if(new_direction != current_direction)
                current_direction = new_direction;

        if(current_direction==UP)
            y -= speed;
        else if (current_direction==DOWN)
            y += speed;
        else if(current_direction==LEFT)
            x -= speed;
        else if (current_direction==RIGHT)
            x += speed;
    }

    public Boolean isLeftSolid(Object[][] map){
        if(X-1>0){
            if (isAlignedY())
                return map[X - 1][Y] != null;
            else if (Y + 1 < Main.ABS_MAP_SIZE)
                return map[X - 1][Y] != null || map[X - 1][Y + 1] != null;
        }
        return true;
    }

    public Boolean isRightSolid(Object[][] map){
        if(X + 1 < Main.ABS_MAP_SIZE) {
            if (isAlignedY())
                return map[X + 1][Y] != null;
            else if (Y + 1 < Main.ABS_MAP_SIZE)
                return map[X + 1][Y] != null || map[X + 1][Y + 1] != null;
        }
        return true;
    }

    public Boolean isUpSolid(Object[][] map){

        if(Y-1>0) {
            if (isAlignedX())
                return map[X][Y - 1] != null;
            else if (X + 1 < Main.ABS_MAP_SIZE)
                return map[X][Y - 1] != null || map[X + 1][Y - 1] != null;
        }
        return true;
    }

    public boolean isDownSolid(Object[][] map){

        if(Y+1<Main.ABS_MAP_SIZE) {
            if (isAlignedX())
                return map[X][Y + 1] != null;
            else if (X + 1 < Main.ABS_MAP_SIZE)
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
            return !isUpSolid(frame.solids) || !isAlignedY();
        else if(direction==DOWN)
            return !isDownSolid(frame.solids) || !isAlignedY();
        else if(direction==LEFT)
            return !isLeftSolid(frame.solids) || !isAlignedX();
        else if(direction==RIGHT)
            return !isRightSolid(frame.solids) || !isAlignedX();
        else if(direction==NULL)
            return true;

        return null;
    }
}
