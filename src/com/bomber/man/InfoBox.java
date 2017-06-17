package com.bomber.man;
import javax.swing.*;

/**
 * Created by Kisiel on 28.04.2017.
 */
public class InfoBox extends JPanel {

    JLabel lab_czas;
    JLabel lab_punktow;
    JLabel lab_zycia;
    JLabel lab_bomby;
    JLabel lab_bomby_rzucane;
    JLabel lab_zasieg;
    JLabel lab_szybkosc;

    GameFrame frame;
    public int points;
    public int bonus = 1;

    final int margin = 10;

    public InfoBox(GameFrame frame) {

        this.frame = frame;

        Box box = Box.createVerticalBox();

        box.add(new JLabel( "Czas:"));
        lab_czas = new JLabel();
        box.add(lab_czas);
        box.add(Box.createVerticalStrut(margin));

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

        box.add(new JLabel( "Ilość bomb rzucanych:"));
        lab_bomby_rzucane = new JLabel();
        box.add(lab_bomby_rzucane);
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

    public void update(long time, Player player){

        int minutes = (int)(frame.FRAME_TIME*time/(1000*60));
        int seconds = (int)((frame.FRAME_TIME*time/1000) % 60);

        lab_czas.setText("Minutes: " + Integer.toString(minutes) + " Seconds: " + Integer.toString(seconds));

        if(player==null)
            return;

        lab_punktow.setText(Integer.toString(points));

        lab_zycia.setText(Integer.toString(player.lives));
        lab_bomby.setText(Integer.toString(player.max_bombs));
        lab_bomby_rzucane.setText(Integer.toString(player.max_throw_bombs));
        lab_zasieg.setText(Integer.toString(player.flame_length));
        lab_szybkosc.setText(Double.toString(player.speed));

        //String s = "<html>";
        //for(Object object : player.getSurroundingObjects())
        //    s += (object.getClass().getSimpleName() + "<br>");

        //s+= "</html>";

        //lab_szybkosc.setText(s);

    }
}
