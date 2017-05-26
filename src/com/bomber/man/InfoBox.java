package com.bomber.man;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Kisiel on 28.04.2017.
 */
public class InfoBox extends JPanel {

    Label lab_punktow;
    Label lab_zycia;
    Label lab_bomby;
    Label lab_zasieg;
    Label lab_szybkosc;

    public InfoBox() {

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(2);
        gridLayout.setRows(5);

        setLayout(gridLayout);

        add(new Label( "Ilość punktów:"));
        lab_punktow = new Label();
        add(lab_punktow);

        add(new Label( "Ilość żyć:"));
        lab_zycia = new Label();
        add(lab_zycia);

        add(new Label( "Ilość bomb:"));
        lab_bomby = new Label();
        add(lab_bomby);

        add(new Label( "Zasięg ognia:"));
        lab_zasieg = new Label();
        add(lab_zasieg);

        add(new Label( "Szybkość:"));
        lab_szybkosc = new Label();
        add(lab_szybkosc);
    }

    public void update(Player player){
        lab_punktow.setText("" + 0);
        lab_zycia.setText("x: " + player.x);
        lab_bomby.setText("y: " + player.y);
        lab_szybkosc.setText("" + player.speed);
    }
}
