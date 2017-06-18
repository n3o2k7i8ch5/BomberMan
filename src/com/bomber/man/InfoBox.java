package com.bomber.man;
import com.bomber.man.player.Player;
import com.bomber.man.player.PlayerParams;

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

    public PlayerParams params;

    GameFrame frame;
    public int points;
    public int bonus = 1;

    final int margin = 10;

    public InfoBox(GameFrame frame) {

        this.frame = frame;

        Box box = Box.createVerticalBox();

        lab_czas = new JLabel();
        lab_czas.setFont(lab_czas.getFont().deriveFont (36.0f));
        box.add(lab_czas);
        box.add(Box.createVerticalStrut(margin));

        box.add(new JLabel( "P - pauza"));
        box.add(Box.createVerticalStrut(margin));

        box.add(new JLabel( "GÓRA - ruch w górę"));
        box.add(new JLabel( "DÓŁ - ruch w dół"));
        box.add(new JLabel( "PRAWO - ruch w prawo"));
        box.add(new JLabel( "LEWO - ruch w lewo"));

        box.add(Box.createVerticalStrut(margin));
        box.add(new JLabel( "X - postaw bombę"));
        box.add(new JLabel( "SPACJA - rzuć bombę"));

        box.add(Box.createVerticalStrut(margin));
        box.add(Box.createVerticalStrut(margin));
        box.add(Box.createVerticalStrut(margin));

        lab_punktow = new JLabel();
        lab_punktow.setFont(lab_punktow.getFont().deriveFont (36.0f));
        box.add(lab_punktow);
        box.add(Box.createVerticalStrut(margin));
        box.add(Box.createVerticalStrut(margin));
        box.add(Box.createVerticalStrut(margin));

        lab_zycia = new JLabel("", new ImageIcon(getClass().getResource(getImages().POWER_UP_LIFE_PATH[0])), JLabel.CENTER);
        lab_zycia.setFont(lab_zycia.getFont().deriveFont (36.0f));
        box.add(lab_zycia);
        box.add(Box.createVerticalStrut(margin));

        lab_bomby = new JLabel("", new ImageIcon(getClass().getResource(getImages().POWER_UP_BOMB_PATH[0])), JLabel.CENTER);
        lab_bomby.setFont(lab_bomby.getFont().deriveFont (36.0f));
        box.add(lab_bomby);
        box.add(Box.createVerticalStrut(margin));

        lab_bomby_rzucane = new JLabel("", new ImageIcon(getClass().getResource(getImages().POWER_UP_THROW_BOMB_PATH[0])), JLabel.CENTER);
        lab_bomby_rzucane.setFont(lab_bomby_rzucane.getFont().deriveFont (36.0f));
        box.add(lab_bomby_rzucane);
        box.add(Box.createVerticalStrut(margin));

        lab_zasieg = new JLabel("", new ImageIcon(getClass().getResource(getImages().POWER_UP_FLAME_PATH[0])), JLabel.CENTER);
        lab_zasieg.setFont(lab_zasieg.getFont().deriveFont (36.0f));
        box.add(lab_zasieg);
        box.add(Box.createVerticalStrut(margin));

        lab_szybkosc = new JLabel("", new ImageIcon(getClass().getResource(getImages().POWER_UP_SPEED_PATH[0])), JLabel.CENTER);
        lab_szybkosc.setFont(lab_szybkosc.getFont().deriveFont (36.0f));
        box.add(lab_szybkosc);
        add(box);
    }

    public void update(long time, Player player){

        int minutes = (int)(frame.FRAME_TIME*time/(1000*60));
        int seconds = (int)((frame.FRAME_TIME*time/1000) % 60);

        lab_czas.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));

        if(player==null)
            return;

        lab_punktow.setText("Punkty: " + Integer.toString(points));

        lab_zycia.setText(Integer.toString(player.lives()));
        lab_bomby.setText(Integer.toString(player.bombsLeft()) + "/" + Integer.toString(player.maxBombs()));
        lab_bomby_rzucane.setText(Integer.toString(player.throwBombsLeft()) + "/" + Integer.toString(player.maxThrowBombs()));
        lab_zasieg.setText(Integer.toString(player.flameLength()));
        lab_szybkosc.setText(Double.toString(player.speed));

        //String s = "<html>";
        //for(Object object : player.getSurroundingObjects())
        //    s += (object.getClass().getSimpleName() + "<br>");

        //s+= "</html>";

        //lab_szybkosc.setText(s);

    }

    GraphicsContainer getImages(){
        return frame.main.graphicsContainer;
    }
}
