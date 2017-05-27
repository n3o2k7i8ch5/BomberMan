package com.bomber.man;


import com.bomber.man.enemies.SmartAssEnemy;
import javax.swing.*;

/**
 * Created by Kisiel on 28.04.2017.
 */
public class InfoBox extends JPanel {

    JLabel lab_punktow;
    JLabel lab_zycia;
    JLabel lab_bomby;
    JLabel lab_zasieg;
    JLabel lab_szybkosc;

    GameFrame frame;

    final int margin = 10;

    public InfoBox(GameFrame frame) {

        this.frame = frame;

        Box box = Box.createVerticalBox();

        box.add(new JLabel( "Ilość punktów:"));
        lab_punktow = new JLabel();
        box.add(lab_punktow);
        box.add(Box.createVerticalStrut(margin));

        box.add(new JLabel( "Ilość żyć:"));
        lab_zycia = new JLabel();
        box.add(lab_zycia);
        box.add(Box.createVerticalStrut(margin));

        box.add(new JLabel( "Ilość bomb:"));
        lab_bomby = new JLabel();
        box.add(lab_bomby);
        box.add(Box.createVerticalStrut(margin));

        box.add(new JLabel( "Zasięg ognia:"));
        lab_zasieg = new JLabel();
        box.add(lab_zasieg);
        box.add(Box.createVerticalStrut(margin));

        box.add(new JLabel( "Szybkość:"));
        lab_szybkosc = new JLabel();
        box.add(lab_szybkosc);


        add(box);
    }

    public void update(Player player){
//        lab_punktow.setText(((SmartAssEnemy)frame.objectManager.enemy_list.get(0)).print());
        lab_zycia.setText("x: " + player.x);
        lab_bomby.setText("y: " + player.y);
        lab_szybkosc.setText("" + player.speed);
    }
}
