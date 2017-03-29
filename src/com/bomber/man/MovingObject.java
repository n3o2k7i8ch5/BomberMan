package com.bomber.man;

import java.util.Random;

import static com.bomber.man.Main.ABS_H_MAP_SIZE;
import static com.bomber.man.Main.ABS_W_MAP_SIZE;
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

    public MovingObject(GameFrame frame, int X, int Y, int speed) {
        super(frame, X, Y);
        this.speed = speed;
        this.align_factor = 1;
        this.old_x = x;
        this.old_y = y;
        element = ((double)RESOLUTION/(double)align_factor);
        is_element_int = element == Math.floor(element);

    }

    public MovingObject(GameFrame frame, int X, int Y, int speed, int align_factor) {
        super(frame, X, Y);
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
    public void update(long time) {
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
                current_direction = new_direction;
    }

    public boolean isDirectionFreeToGo(direction direction){
        if(direction==UP && y > 0)
            return (upObject(frame.solid_list)==null && upObject(frame.bomb_list)==null) || !isAlignedY();
        else if(direction==DOWN && y < ABS_H_MAP_SIZE*RESOLUTION)
            return (downObject(frame.solid_list)==null && downObject(frame.bomb_list)==null) || !isAlignedY();
        else if(direction==LEFT && x > 0)
            return (leftObject(frame.solid_list)==null && leftObject(frame.bomb_list)==null) || !isAlignedX();
        else if(direction==RIGHT && x < ABS_W_MAP_SIZE*RESOLUTION)
            return (rightObject(frame.solid_list)==null && rightObject(frame.bomb_list)==null) || !isAlignedX();
        else if(direction==NULL)
            return true;
        else
            return false;

    }

    protected direction randomFreeDirection(){

        Random random = new Random();

        int r = random.nextInt() % 4;

        if (r == 0 && isDirectionFreeToGo(UP))
            return UP;
        else if(r==1 && isDirectionFreeToGo(DOWN))
            return DOWN;
        else if(r==2 && isDirectionFreeToGo(RIGHT))
            return RIGHT;
        else if(r==3 && isDirectionFreeToGo(LEFT))
            return LEFT;
        else
            return NULL;

        //dopisać przypadek kiedy losowany jest null

    }

}
