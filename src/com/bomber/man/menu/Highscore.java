package com.bomber.man.menu;

import com.bomber.man.Main;
import com.bomber.man.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Murspi on 14.06.2017.
 */

/**
 * Klasa Highscore pozwala nam na odczyt zapisanych w pliku wyników wszystkich aktualnych graczy, przycisk Quit pozwala
 * powrócić do menu głównego gry
 */
public class Highscore extends JPanel implements MouseListener {
    private Rectangle quitButton = new Rectangle(300, 480, 200, 50);

    Main main;

    Client client;

    Thread highscoresDownloader, displayHighscores;

    public Highscore(Main main) {
        this.main = main;
        addMouseListener(this);
        setSize(800,600);
        setVisible(true);
        repaint();
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

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getImage("/drawables/menu3.png"), 0, 0, 800, 600, this);

        Font fnt1 = new Font("Papyrus", Font.BOLD, 15);
        Font fnt0 = new Font("Papyrus", Font.BOLD, 35);
        g.setFont(fnt0);

        g.setColor(Color.WHITE);
        g.drawString("Najlepsze wyniki:", 460, 150);

        g.setFont(fnt1);

        try {
            client = new Client();

            if (client.error()) {
                main.setTitle("Highscores - serverIP file error");
                g.setColor(Color.WHITE);
                g.drawString("Server error.", 500, 170 + 20);

                String highscores = null;
                try {
                    highscores = readHighscoresLocal();
                } catch (Exception e) {
                    highscores = null;
                }

                if (highscores != null)
                    drawHighscores(highscores, g, highscores_new);
                return;
            }

            highscoresDownloader = getHighscoresDownloader();
            displayHighscores = getDisplayHighscores(g);

            highscoresDownloader.start();
            highscoresDownloader.join(1500);
            displayHighscores.start();
            displayHighscores.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.setFont(fnt0);
        g.drawString("Quit", quitButton.x + 55, quitButton.y + 40);
        g2d.draw(quitButton);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (my >= 480 && my <= 530 && mx >= 300 && mx <= 500)
            main.setGameState(Main.STATE_MENU);
    }

    @Override
    public void mousePressed(MouseEvent e) {
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


    boolean highscores_new;

    private Thread getHighscoresDownloader() {
        return new Thread(() -> {
            highscores_new = client.downloadHighscores();
        }
        );
    }
    private Thread getDisplayHighscores(Graphics g) {
        return new Thread(() -> {
            String highscores = null;
            try {
                highscores = readHighscoresLocal();
            } catch (Exception e) {
                highscores = null;
            }

            if (highscores != null)
                drawHighscores(highscores, g, highscores_new);
            else
                g.drawString("Serwer error.", 460,  80);

            return;
        }
        );
    }

    private String readHighscoresLocal() throws Exception{
        String code = null;
        if(new File("highscores").exists()) {

            FileReader fileReader = new FileReader("highscores");
            BufferedReader bufferReader = new BufferedReader(fileReader);

            String linia;
            while ((linia = bufferReader.readLine()) != null) {
                code = linia;
            }
            fileReader.close();
        }
        return code;
    }

    private void drawHighscores(String highscores, Graphics g, boolean highscores_new){

        if(!highscores_new){
            g.setColor(Color.WHITE);
            g.drawString("Serwer error.", 460,  80);
            g.drawString("Wyniki niekoniecznie aktualne.", 460,  100);
        }

        String[] items = highscores.split("!");
        for(int i=0;i<items.length;i++) {
            g.setColor(Color.WHITE);
            g.drawString(items[i], 520,  200 + 20*i);
        }
    }
}
