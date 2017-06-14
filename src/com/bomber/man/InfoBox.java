package com.bomber.man;
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

        Object upSolid = player.upSolid();
        Object downSolid = player.downSolid();
        Object rightSolid = player.rightSolid();
        Object leftSolid = player.leftSolid();

        Object upLeftSolid = player.upLeftSolid();
        Object upRightSolid = player.upRightSolid();
        Object downLeftSolid = player.downLeftSolid();
        Object downRightSolid = player.downRightSolid();

        String s1 = "<html>";
        if(upSolid!=null)
            s1 += ("UP: " + upSolid.getClass().getSimpleName()) + "<br>";
        else
            s1 += "UP: <br>";

        if(downSolid!=null)
            s1 += ("DOWN: " + downSolid.getClass().getSimpleName()) + "<br>";
        else
            s1 += "DOWN: <br>";

        if(leftSolid!=null)
            s1 += ("LEFT: " + leftSolid.getClass().getSimpleName()) + "<br>";
        else
            s1 += "LEFT: <br>";

        if(rightSolid!=null)
            s1 += ("RIGHT: " + rightSolid.getClass().getSimpleName()) + "<br><br>";
        else
            s1 += "RIGHT: <br><br>";

        if(upLeftSolid!=null)
            s1 += ("UP_LEFT: " + upLeftSolid.getClass().getSimpleName()) + "<br>";
        else
            s1 += "UP_LEFT: <br>";

        if(upRightSolid!=null)
            s1 += ("UP_RIGHT: " + upRightSolid.getClass().getSimpleName()) + "<br>";
        else
            s1 += "UP_RIGHT: <br>";

        if(downLeftSolid!=null)
            s1 += ("DOWN_LEFT: " + downLeftSolid.getClass().getSimpleName()) + "<br>";
        else
            s1 += "DOWN_LEFT: <br>";

        if(downRightSolid!=null)
            s1 += ("DOWN_RIGHT: " + downRightSolid.getClass().getSimpleName()) + "<br>";
        else
            s1 += "DOWN_RIGHT: <br>";

        lab_zycia.setText(s1);
        lab_bomby.setText("");
        lab_szybkosc.setText(player.speed + "");

        String s = "<html>";
        for(Object object : player.getSurroundingObjects())
            s += (object.getClass().getSimpleName() + "<br>");

        s+= "</html>";

        //lab_szybkosc.setText(s);

    }
}
