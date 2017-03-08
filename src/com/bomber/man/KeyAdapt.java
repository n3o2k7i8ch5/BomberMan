package com.bomber.man;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Kisiel on 08.03.2017.
 */
public class KeyAdapt extends KeyAdapter {

    Player player;

    public KeyAdapt(Player player) {
        this.player = player;
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        //player.keyRealised(e);
    }


}