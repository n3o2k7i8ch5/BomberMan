package com.bomber.man;

import com.bomber.man.enemies.SmartAssEnemy;
import com.bomber.man.listeners.ExplosionColisionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static com.bomber.man.Main.ABS_H_MAP_SIZE;
import static com.bomber.man.Main.ABS_W_MAP_SIZE;
import static com.bomber.man.Main.RESOLUTION;
import static com.bomber.man.Object.direction.*;
import static com.bomber.man.Object.direction.RIGHT;


public class Bomb extends MovingObject {
    /**
     * @param destruct_time czas po jakim bomba zostanie zdetonowana.
     * @param fire_length długość wybuchu wyrażona w ilości kratek.
     */
    static final int BOMB_DESTRUCT_TIME = 3000;
    static final int BOMB_QUICK_DESTRUCT_TIME = 1000;
    static final double SPEED = 2;
    private int destruct_time;
    public int flame_length;
    public boolean mooving_bomb;

    /**
     * Klasa Bomb wybuchająca po określonym czasie i niszcząca wszystkie zniszczalne obiekty
     * @param frame okno gry
     * @param X współrzędna X bomby na mapie, określająca ilość kratek.
     * @param Y współrzędna y bomby na mapie, określająca ilość kratek.
     * @param quick_explode wartość określająca, czy bomba po krótkim, czy długim czasie.
     */
    public Bomb(GameFrame frame, int X, int Y, boolean quick_explode) {
        super(frame, X, Y, 0, 1, true);
        this.destruct_time = quick_explode?BOMB_QUICK_DESTRUCT_TIME:BOMB_DESTRUCT_TIME;
        this.flame_length = frame.player.flameLength();
        this.mooving_bomb = false;

        setExplosionListener();
        setAnimationDuration(250);
    }

    /**
     * Klasa Bomb wybuchająca po określonym czasie i niszcząca wszystkie zniszczalne obiekty
     * @param frame okno gry
     * @param X współrzędna X bomby na mapie, określająca ilość kratek.
     * @param Y współrzędna y bomby na mapie, określająca ilość kratek.
     */
    public Bomb(GameFrame frame, int X, int Y) {
        super(frame, X, Y, 0, 1, true);
        this.destruct_time = BOMB_DESTRUCT_TIME;
        this.flame_length = frame.player.flameLength();
        this.mooving_bomb = false;

        setExplosionListener();
        setAnimationDuration(250);
    }

    public Bomb(GameFrame frame, int X, int Y, boolean quick_explode, direction dir) {
        super(frame, X, Y, SPEED, 1, true);
        this.destruct_time = quick_explode?BOMB_QUICK_DESTRUCT_TIME:BOMB_DESTRUCT_TIME;
        this.flame_length = frame.player.flameLength();
        this.current_dir = dir;
        this.new_dir = dir;
        this.mooving_bomb = true;

        setExplosionListener();
        setAnimationDuration(250);
    }

    public Bomb(GameFrame frame, int X, int Y, direction dir) {
        super(frame, X, Y, SPEED, 1, true);
        this.destruct_time = BOMB_DESTRUCT_TIME;
        this.flame_length = frame.player.flameLength();
        this.current_dir = dir;
        this.new_dir = dir;
        this.mooving_bomb = true;

        setExplosionListener();
        setAnimationDuration(250);
    }

    public void tick(Iterator<Bomb> bomb_list_iter){
        destruct_time -= frame.FRAME_TIME;
        if(destruct_time == 0)
            detonate(bomb_list_iter);
    }

    public void detonate(Iterator bomb_list_iter){
        bomb_list_iter.remove();
        getObjectManager().removeSolid(this);
        getObjectManager().addExplosion(
                ((x() + Main.RESOLUTION/2)/Main.RESOLUTION),
               ((y() + Main.RESOLUTION/2)/Main.RESOLUTION),
                flame_length, NULL);

        if(mooving_bomb)
            player().restoreThrowBomb();
        else
            player().restoreBomb();

        for(SmartAssEnemy enemy : getObjectManager().smartass_enemy_list)
            enemy.bombDetonation(X, Y);
    }

    public void detonate(){
        getObjectManager().bomb_list.remove(this);
        getObjectManager().removeSolid(this);
        getObjectManager().addExplosion(
                ((x() + Main.RESOLUTION/2)/Main.RESOLUTION),
                ((y() + Main.RESOLUTION/2)/Main.RESOLUTION),
                flame_length, NULL);

        if(mooving_bomb)
            player().restoreThrowBomb();
        else
            player().restoreBomb();

        for(SmartAssEnemy enemy : getObjectManager().smartass_enemy_list)
            enemy.bombDetonation(X, Y);
    }

    private void setExplosionListener(){
        addExplosionColisionListener(new ExplosionColisionListener() {
            @Override
            public void onColision(Explosion explosion, Iterator<Object> it) {
                detonate();
            }
        });
    }

    @Override
    protected void update(long time) {
        handleAnimation(time);
    }

    private boolean prev_touched;

    public void updateDir(){

        if(touches(player(), 1) && isDirFreeToGo(current_dir)) {
            prev_touched = true;
        }else if(touches(player(), 1) && !isDirFreeToGo(current_dir)){
            if (isDirFreeToGo(dirReverse(current_dir)))
                current_dir = dirReverse(current_dir);
            else
                current_dir = NULL;
        }else if(!isDirFreeToGo(current_dir) || (!isDirFreeFrom(player(), current_dir) && !prev_touched)) {
            if (isDirFreeToGo(dirReverse(current_dir)))
                current_dir = dirReverse(current_dir);
            else
                current_dir = NULL;

            prev_touched = false;
        }
    }

    @Override
    protected void updateStep(long time) {
        //updateDir();
    }

    @Override
    public void onTilePositionChanged() {

        super.onTilePositionChanged();

        getObjectManager().solids[old_X][old_Y] = null;
        getObjectManager().solids[X][Y] = this;

        for(SmartAssEnemy enemy : getObjectManager().smartass_enemy_list)
            enemy.bombMoved();

    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.bombImages;
    }

    @Override
    protected ArrayList<Image> getImageUpList() {
        return getMain().graphicsContainer.bombImages;
    }

    @Override
    protected ArrayList<Image> getImageDownList() {
        return getMain().graphicsContainer.bombImages;
    }

    @Override
    protected ArrayList<Image> getImageLeftList() {
        return getMain().graphicsContainer.bombImages;
    }

    @Override
    protected ArrayList<Image> getImageRightList() {
        return getMain().graphicsContainer.bombImages;
    }
}
