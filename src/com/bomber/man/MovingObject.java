package com.bomber.man;

import static com.bomber.man.GameFrame.*;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.Object.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public abstract class MovingObject extends Object {

    private int old_x, old_y;
    protected int old_X, old_Y;
    int speed;
    int align_factor;
    protected direction current_direction = NULL;
    public direction new_direction = NULL;
    private double element;
    private boolean is_element_int;

    public MovingObject(GameFrame frame, int X, int Y, int speed, String... image_strings) {
        super(frame, X, Y, image_strings);
        this.speed = speed;
        this.align_factor = 1;
        this.old_x = x;
        this.old_y = y;
        element = ((double)RESOLUTION/(double)align_factor);
        is_element_int = element == Math.floor(element);

    }

    public MovingObject(GameFrame frame, int X, int Y, int speed, int align_factor, String... image_string) {
        super(frame, X, Y, image_string);
        this.speed = speed;
        this.align_factor = align_factor;
        this.old_x = x;
        this.old_y = y;
        element = ((double)RESOLUTION/(double)align_factor);
    }

    public void beforePositionChanged(){
        old_x = x;
        old_y = y;
        old_X = (old_x/ RESOLUTION);
        old_Y = (old_y/ RESOLUTION);
    }

    public void onPositionChanged(){
        X = x/ RESOLUTION;
        Y = y/ RESOLUTION;
    }

    @Override
    void update(long time) {
        super.update(time);
        if (is_element_int){
            updateDirection();
            if (current_direction == UP) {
                beforePositionChanged();
                y -= speed;
                onPositionChanged();
            } else if (current_direction == DOWN) {
                beforePositionChanged();
                y += speed;
                onPositionChanged();
            } else if (current_direction == LEFT) {
                beforePositionChanged();
                x -= speed;
                onPositionChanged();
            } else if (current_direction == RIGHT) {
                beforePositionChanged();
                x += speed;
                onPositionChanged();
            }
        }else{
            for(int i = 0; i<speed; i++) {
                updateDirection();
                if (current_direction == UP) {
                    beforePositionChanged();
                    y--;
                    onPositionChanged();
                } else if (current_direction == DOWN) {
                    beforePositionChanged();
                    y++;
                    onPositionChanged();
                } else if (current_direction == LEFT) {
                    beforePositionChanged();
                    x--;
                    onPositionChanged();
                } else if (current_direction == RIGHT) {
                    beforePositionChanged();
                    x++;
                    onPositionChanged();
                }
            }
        }
    }

    private void updateDirection(){
        if ((current_direction == RIGHT && x % element < old_x % element) ||
                (current_direction == LEFT && (x - 1) % element > (old_x - 1) % element) ||
                (current_direction == UP && (y - 1) % element > (old_y - 1) % element) ||
                (current_direction == DOWN && y % element < old_y % element) ||
                current_direction == NULL)
            if (new_direction != current_direction)
                current_direction = new_direction;
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
