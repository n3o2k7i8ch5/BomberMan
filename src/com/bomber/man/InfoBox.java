package com.bomber.man;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kisiel on 28.04.2017.
 */
public class InfoBox extends Object {

    JTextField textSpeed;

    public InfoBox(GameFrame frame) {
        super(frame, 14, 0);
        textSpeed = new JTextField(8);
        textSpeed.setText("Speed: ");
        textSpeed.setLocation(200, 10);
        textSpeed.setFont(textSpeed.getFont().deriveFont(20));
        textSpeed.setVisible(true);
        frame.add(textSpeed);
        frame.setVisible(true);
    }

    @Override
    protected ArrayList<Image> getImageNullList() {
        return getMain().graphicsContainer.infoBoxPath;
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.drawImage(current_image_list.get(current_image_index),
                frame.getBounds().width - Main.INFO_WIDTH, 0,
                null);

    }
}
