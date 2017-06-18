package com.bomber.man.menu;

import com.bomber.man.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;


/**
 * Created by Murspi on 28.03.2017.
 */

/**
 * Klasa Menu jest to główne menu gry w którym mamy do dyspozycji 4 opcje Play , Settings , Highscore, Quit
 * Play buttom pozwala nam przejść dalej do gry i rozpocząć pierwszy poziom, Settings pozwala nam ustawić nickname gracza,
 * Highscore wyświetla nam wszystkie wyniki graczy, Quit wyłącza menu główne(gre).
 */
public class Menu extends JPanel implements MouseListener {

    private Rectangle playButton = new Rectangle(300, 220, 200, 50);
    //private Rectangle helpButton = new Rectangle(300, 290, 200, 50);
    private Rectangle editorButton = new Rectangle(300, 360, 200, 50);
    private Rectangle quitButton = new Rectangle(300, 430, 200, 50);

    Main main;

    public Menu(Main main) {
        this.main = main;
        addMouseListener(this);
        setSize(800,600);
        setVisible(true);
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getImage("/drawables/menu2.png"), 0, 0, 800, 600, this);

        Font fnt0 = new Font("Papyrus", Font.BOLD, 35);
        g.setFont(fnt0);
        g.setColor(Color.white);

        g.drawString("Bomberman-Knight",20,60);
        g.drawString("Created by ",550,60);
        g.drawString("Jakub & Daniel",500,100);
        g.drawString("Play", playButton.x + 55, playButton.y + 40);
        g2d.draw(playButton);
        //g.drawString("Settings", helpButton.x + 35, helpButton.y + 40);
        //g2d.draw(helpButton);
        g.drawString("Quit", quitButton.x + 55, quitButton.y + 40);
        g2d.draw(quitButton);
        g.drawString("Highscores", editorButton.x , editorButton.y + 40);
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

        if(my >=220 && my<=270 && mx >= 300 && mx <=500)
        {
            if(main.player_name==null)
                main.player_name = JOptionPane.showInputDialog("Podaj nickname gracza:");

            if(main.player_name==null)
                return;
            JOptionPane.showMessageDialog(null,"Witaj, " + main.player_name);

            removeMouseListener(this);
            main.setGameState(Main.NEXT_LEVEL);
        }

        if(my>=290 && my <= 340 && mx >= 300 && mx <=500)
        {
        }
        if(my >=360 && my <= 410 && mx >= 300 && mx <=500)
        {
            main.setGameState(Main.HIGHSCORES);
        }
        if(my >=430 && my<=480 && mx >= 300 && mx <=500)
        {
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



