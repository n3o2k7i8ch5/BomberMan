package com.bomber.man.player;

import com.bomber.man.*;
import com.bomber.man.Object;
import com.bomber.man.enemies.Enemy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;

import static com.bomber.man.Main.CENTER_MAP;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.Object.direction.*;

/**
 * Created by Kisiel on 07.03.2017.
 */
public class Player extends MovingObject {

    boolean move_player_x, move_player_y;

    public direction key1_pressed = NULL;
    private direction key2_pressed = NULL;

    private direction orientation = UP;

    //public PlayerParams params;

    public int flame_length = 2;
    public int flameLength(){return flame_length;}

    private int lives = 3;
    public int lives(){return lives;}

    private int max_bombs = 3;
    public int maxBombs(){return max_bombs;}

    private int bombs_left;
    public int bombsLeft(){return bombs_left;}
    public void restoreBomb(){bombs_left++;}

    private int max_throw_bombs = 0;
    public int maxThrowBombs(){return max_throw_bombs;}

    private int throw_bombs_left;
    public int throwBombsLeft(){return throw_bombs_left;}
    public void restoreThrowBomb(){throw_bombs_left++;}

    private int immortal = -1;
    private final int IMMORTAL_TIME = 3000;
    public static final int IMMORTAL_SHIELD_TIME = 12000;

    static final double SPEED = 2.5;
    private double saved_speed;
    private int slow_down = -1;

    private int instant_bomb = -1;

    private int saved_flame;
    private int flame_down = -1;

    public int changed_direction = -1;
    public static final int CHANGE_DIRECTION_TIME = 10000;

    public Player(GameFrame frame, int X, int Y, int align_factor) {
        super(frame, X, Y, SPEED, align_factor);
        setAnimationDuration(200);
        bombs_left = max_bombs;
        throw_bombs_left = max_throw_bombs;
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP)
        {
            if(key1_pressed==NULL)
                key1_pressed = UP;
            else if(key2_pressed==NULL && key1_pressed!=UP)
                key2_pressed = UP;
        }
        else if(key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN)
        {
            if(key1_pressed==NULL)
                key1_pressed = DOWN;
            else if(key2_pressed==NULL  && key1_pressed!=DOWN)
                key2_pressed = DOWN;
        }
        else if(key==KeyEvent.VK_A || key==KeyEvent.VK_LEFT)
        {
            if(key1_pressed==NULL)
                key1_pressed = LEFT;
            else if(key2_pressed==NULL  && key1_pressed!=LEFT)
                key2_pressed = LEFT;
        }
        else if(key==KeyEvent.VK_D || key==KeyEvent.VK_RIGHT) {
            if (key1_pressed == NULL)
                key1_pressed = RIGHT;
            else if(key2_pressed==NULL && key1_pressed!=RIGHT)
                key2_pressed = RIGHT;
        }
        else if(key==KeyEvent.VK_SPACE){
            if(getMain().getGameState()==1)
                throwBomb();
        }
        else if(key==KeyEvent.VK_X){
            if(getMain().getGameState()==1)
                putBomb();
        }
    }

    public void keyRealised(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP)
        {
            if(key1_pressed==UP) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==UP)
                key2_pressed = NULL;
        }
        else if(key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN)
        {
            if(key1_pressed==DOWN) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==DOWN)
                key2_pressed = NULL;
        }
        else if(key==KeyEvent.VK_A || key==KeyEvent.VK_LEFT)
        {
            if(key1_pressed==LEFT) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==LEFT)
                key2_pressed = NULL;
        }
        else if(key==KeyEvent.VK_D || key==KeyEvent.VK_RIGHT)
        {
            if(key1_pressed==RIGHT) {
                key1_pressed = key2_pressed;
                key2_pressed = NULL;
            }
            else if(key2_pressed==RIGHT)
                key2_pressed = NULL;
        }
        else if(key==KeyEvent.VK_P)
        {
            frame.pause = !frame.pause;
        }
        else if(key == KeyEvent.VK_R){
            getMain().setGameState(1);
        }

    }

    @Override
    public void onPositionChanged() {
        super.onPositionChanged();
        getDirectionFromKey();
    }

    @Override
    protected void updateStep(long time) {

        if(current_dir!=NULL)
            orientation = current_dir;

        if(current_dir!=NULL && !isDirFreeToGo(current_dir))
            current_dir = NULL;
        else
            super.updateStep(time);

    }

    @Override
    public void update(long time) {

        checkNearbyCollisions();

        if(immortal>0) {
            immortal -= frame.FRAME_TIME;
            if(immortal < 0)
                immortal = 0;
        }else if(immortal==0){
            immortal = -1;
        }

        if(slow_down>0) {
            slow_down -= frame.FRAME_TIME;
            if(slow_down < 0)
                slow_down = 0;
        }else if(slow_down==0){
            slow_down = -1;
            speed = saved_speed;
        }

        if(instant_bomb > 0) {
            instant_bomb -=frame.FRAME_TIME;
            putBomb();
            if(instant_bomb<0)
                instant_bomb = 0;
        }else if(instant_bomb==0){
            instant_bomb = -1;
        }

        if(flame_down>0) {
            flame_down -= frame.FRAME_TIME;
            if(flame_down < 0)
                flame_down = 0;
        }else if(flame_down==0){
            flame_down = -1;
            flame_length = saved_flame;
        }

        if(changed_direction>0) {
            changed_direction -= frame.FRAME_TIME;
            if(changed_direction < 0)
                changed_direction = 0;
        }else if(changed_direction==0){
            changed_direction = -1;
        }

        if(current_dir==NULL)
            getDirectionFromKey();

        if(X + Main.CENTER_MAP < getMain().ABS_W_MAP_SIZE-1 && X >= Main.CENTER_MAP) {
            move_player_x = false;
            frame.x_map_shift = (int)x - CENTER_MAP*RESOLUTION;
        }else
            move_player_x = true;

        if(Y + Main.CENTER_MAP < getMain().ABS_H_MAP_SIZE-1 && Y >= Main.CENTER_MAP) {
            move_player_y = false;
            frame.y_map_shift = (int)y - CENTER_MAP*RESOLUTION;
        }else
            move_player_y = true;

        super.update(time);

    }

    private void checkNearbyCollisions(){

        if(X!=0 && Y!=0)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X-1][Y-1].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }

        if(Y!=0)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X][Y-1].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }

        if(X!=getMain().ABS_W_MAP_SIZE-1 && Y!=0)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X+1][Y-1].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }

        if(X!=0)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X-1][Y].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }

        for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X][Y].iterator(); it_all_objects.hasNext(); ) {
            Object object = it_all_objects.next();
            if (object.playerColisionListener != null)
                object.playerColisionListener.checkColision(it_all_objects);
        }

        if(X!=getMain().ABS_W_MAP_SIZE-1)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X+1][Y].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }

        if(X!=0 && Y!=getMain().ABS_H_MAP_SIZE-1)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X-1][Y+1].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }

        if(Y!=getMain().ABS_H_MAP_SIZE-1)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X][Y+1].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }

        if(X!=getMain().ABS_W_MAP_SIZE-1 && Y!=getMain().ABS_H_MAP_SIZE-1)
            for (Iterator<Object> it_all_objects = getObjectManager().all_objects[X+1][Y+1].iterator(); it_all_objects.hasNext(); ) {
                Object object = it_all_objects.next();
                if (object.playerColisionListener != null)
                    object.playerColisionListener.checkColision(it_all_objects);
            }
    }

    @Override
    protected ArrayList<Image> getImageUpList() {

        if (lives() != 0) {
            if (immortal != -1 && (frame.time /10) % 4 == 0)
                return getMain().graphicsContainer.emptyImages;
            else
                return getMain().graphicsContainer.playerUpImages;
        }else
            return getMain().graphicsContainer.graveImages;

    }

    @Override
    protected ArrayList<Image> getImageDownList() {

        if(lives()!=0) {
            if(immortal!=-1 && (frame.time/10)%4==0)
                return getMain().graphicsContainer.emptyImages;
            else
                return getMain().graphicsContainer.playerDownImages;
        }else
            return getMain().graphicsContainer.graveImages;    }

    @Override
    protected ArrayList<Image> getImageLeftList() {

        if (lives() != 0){
            if (immortal != -1 && (frame.time/10) % 4 == 0)
                return getMain().graphicsContainer.emptyImages;
            else
                return getMain().graphicsContainer.playerLeftImages;
        }else
            return getMain().graphicsContainer.graveImages;    }

    @Override
    protected ArrayList<Image> getImageRightList() {

        if(lives()!=0) {
            if(immortal!=-1 && (frame.time/10)%4==0)
                return getMain().graphicsContainer.emptyImages;
            else
                return getMain().graphicsContainer.playerRightImages;
        }else
            return getMain().graphicsContainer.graveImages;
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        if(lives()!=0) {
            if(immortal!=-1 && (frame.time/10)%4==0)
                return getMain().graphicsContainer.emptyImages;

            if(orientation==UP)
                return new ArrayList<>(getMain().graphicsContainer.playerUpImages.subList(0,1));
            else if(orientation==DOWN)
                return new ArrayList<>(getMain().graphicsContainer.playerDownImages.subList(0,1));
            else if(orientation==LEFT)
                return new ArrayList<>(getMain().graphicsContainer.playerLeftImages.subList(0,1));
            else if(orientation==RIGHT)
                return new ArrayList<>(getMain().graphicsContainer.playerRightImages.subList(0,1));
            else
                return new ArrayList<>(getMain().graphicsContainer.playerUpImages.subList(0,1));
        }else
            return getMain().graphicsContainer.graveImages;
    }

    /**
     * Metoda, określająca nowy kierunek poruszania się gracza na podstawie kontekstu mapy, oraz wciśnietych klawiszy.
     */
    private void getDirectionFromKey(){

        direction key1_pressed = this.key1_pressed;
        direction key2_pressed = this.key2_pressed;

        if(changed_direction!=-1) {
            key1_pressed = dirReverse(this.key1_pressed);
            key2_pressed = dirReverse(this.key2_pressed);
        }

        if(key2_pressed==NULL){
            if(isDirFreeToGo(key1_pressed))
                new_dir = key1_pressed;
            else
                new_dir = NULL;
        }
        else
        {
            if(current_dir!=key2_pressed) {
                if (isDirFreeToGo(key2_pressed))
                    new_dir = key2_pressed;
                else if (isDirFreeToGo(key1_pressed))
                    new_dir = key1_pressed;
                else
                    new_dir = NULL;
            }else if(current_dir!=key1_pressed) {
                if (isDirFreeToGo(key1_pressed))
                    new_dir = key1_pressed;
                else if (isDirFreeToGo(key2_pressed))
                    new_dir = key2_pressed;
                else
                    new_dir = NULL;
            }
        }
    }

    public void throwBomb(){

        if(throwBombsLeft()==0)
            return;

        int X = this.X;
        int Y = this.Y;

        if(orientation == RIGHT && !isAlignedX())
            X++;

        if(orientation == DOWN && !isAlignedY())
            Y++;

        if(getObjectManager().bomb_list.size()==0) {
            getObjectManager().addBomb(X, Y, orientation);
            throw_bombs_left--;
        } else {
            for (Bomb bomb : getObjectManager().bomb_list)
                if (bomb.touches(X, Y, 1))
                    return;

            for (Explosion explosion : getObjectManager().explosion_list)
                if (explosion.touches(X, Y, 1))
                    return;

            getObjectManager().addBomb(X, Y, orientation);
            throw_bombs_left--;
        }
    }

    public void putBomb(){

        if(bombsLeft()==0)
            return;

        int X, Y;

        if(x%getMain().RESOLUTION<getMain().RESOLUTION/2)
            X = this.X;
        else
            X = this.X + 1;

        if(y%getMain().RESOLUTION<getMain().RESOLUTION/2)
            Y = this.Y;
        else
            Y = this.Y + 1;

        for(Bomb bomb : getObjectManager().bomb_list)
            if(bomb.Y == Y && bomb.X == X)
                return;

        for (Explosion explosion : getObjectManager().explosion_list)
            if (explosion.touches(X, Y, 1))
                return;

        for(Enemy enemy : getObjectManager().enemy_list)
            if(enemy.touches(X, Y, 1))
                return;

        getObjectManager().addBomb(X, Y, NULL);
        bombs_left--;

    }

    /**
     * Metoda zwiększająca prędkość gracza
     */
    public void increaseSpeed(){
        if(speed<5) {
            speed += 0.5;
            speed = speed;
        }

    }

    public void addLife(){
        lives++;
    }

    /**
     *Metoda zwiekszająca promień wybuchu
     */
    public void increaseFlame(){
        flame_length++;
    }

    /**
     * Metoda zwiększająca ilość bomb do położenia
     */
    public void increaseBomb() {
        max_bombs++;
        bombs_left++;
    }
    public void increaseThrowBomb() {
        max_throw_bombs++;
        throw_bombs_left++;
    }

    public void slowDown(final int slow_down_time){
        saved_speed = speed;
        speed = 1;
        slow_down = slow_down_time;
    }

    public void instantBomb(final int instant_bomb_time){
        instant_bomb = instant_bomb_time;
    }

    public void flameDown(final int flame_down_time){
        saved_flame = flameLength();
        flame_length = 1;
        flame_down = flame_down_time;
    }

    public void changeDirection(int change_direction_time) {
        changed_direction = change_direction_time;
    }

    public boolean isImmortal(){
        return immortal!=-1;
    }

    public void setImmortal(int immortal){
        this.immortal=immortal;
    }

    public void reduceLife(){
        if(!player().isImmortal() && player().lives()>0) {
            lives--;
            setImmortal(IMMORTAL_TIME);
        }
        if(player().lives() == 0)
            getMain().setGameState(Main.STATE_PLAYER_DEAD);
    }

    public void setParams(PlayerParams params){
        this.speed = params.speed;
        this.lives = params.lives;
        this.max_bombs = params.max_bombs;
        bombs_left = params.max_bombs;
        this.max_throw_bombs = params.max_throw_bombs;
        throw_bombs_left = params.max_throw_bombs;
        this.flame_length = params.flame_length;
    }

    public PlayerParams getParams(){
        return new PlayerParams(speed, lives, max_bombs, max_throw_bombs, flameLength());
    }

    public void setDefaultParams(){
        setParams(new PlayerParams(2.5, 3, 3, 0, 2));
    }
}