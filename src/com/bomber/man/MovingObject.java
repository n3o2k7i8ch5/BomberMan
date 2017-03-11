package com.bomber.man;

import static com.bomber.man.GameFrame.*;
import static com.bomber.man.Object.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class MovingObject extends Object {

    double old_x, old_y;
    private int old_X, old_Y;
    double speed;
    int align_factor;
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

    public Boolean isDirectionFreeToGo(direction direction){
        if(direction==UP)
            return (!isUpObject(solids) && !isUpObject(bombs)) || !isAlignedY();
        else if(direction==DOWN)
            return (!isDownObject(solids) && !isDownObject(bombs)) || !isAlignedY();
        else if(direction==LEFT)
            return (!isLeftObject(solids) && !isLeftObject(bombs)) || !isAlignedX();
        else if(direction==RIGHT)
            return (!isRightObject(solids) && !isRightObject(bombs)) || !isAlignedX();
        else if(direction==NULL)
            return true;

        return null;
    }
}
