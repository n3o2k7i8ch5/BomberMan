package com.bomber.man.menu;

import com.bomber.man.GameFrame;
import com.bomber.man.Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



/**
 * Created by Murspi on 02.04.2017.
 */
public class MouseInput implements MouseListener {
    Main main;

    MouseInput(Main main){
        this.main = main;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(mx >= 300 && mx <=500)
        {
            if(my >=220 && my<=270)
                main.setGameState(1);
        }

        if(mx >= 300 && mx <=500)
        {
            if(my >=430 && my<=480)
                System.exit(1);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {


    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
