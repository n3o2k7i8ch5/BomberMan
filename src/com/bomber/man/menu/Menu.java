package com.bomber.man.menu;

import com.bomber.man.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by Murspi on 28.03.2017.
 */
public class Menu extends JPanel implements MouseListener {

    private Rectangle playButton = new Rectangle(300, 220, 200, 50);
    private Rectangle helpButton = new Rectangle(300, 290, 200, 50);
    private Rectangle editorButton = new Rectangle(300, 360, 200, 50);
    private Rectangle quitButton = new Rectangle(300, 430, 200, 50);

    Main main;

    public Menu(Main main) {
        this.main = main;
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getImage("/drawables/menu1.png"), 0, 0, 800, 600, this);

        Font fnt0 = new Font("arial", Font.BOLD, 40);
        g.setFont(fnt0);
        g.setColor(Color.white);

        g.drawString("Play", playButton.x + 55, playButton.y + 40);
        g2d.draw(playButton);
        g.drawString("Settings", helpButton.x + 35, helpButton.y + 40);
        g2d.draw(helpButton);
        g.drawString("Quit", quitButton.x + 55, quitButton.y + 40);
        g2d.draw(quitButton);
        g.drawString("Editor", editorButton.x + 45, editorButton.y + 40);
        g2d.draw(editorButton);

    }

    public Image getImage(String path) {
        Image image = null;
        try {
            image = new ImageIcon(Main.class.getResource(path)).getImage();
        } catch (Exception e) {
            System.out.println("Error");
        }

        return image;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(mx >= 300 && mx <=500)
        {
            if(my >=220 && my<=270)
                removeMouseListener(this);
                main.setGameState(Main.NEXT_LEVEL);
        }

        if(mx >= 300 && mx <=500)
        {
            if(my >=430 && my<=480)
                System.exit(1);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}



