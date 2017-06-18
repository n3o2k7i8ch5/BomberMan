package com.bomber.man.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;

/**
 * Created by Murspi on 13.06.2017.
 */
public class Client {

    String serverIP;
    final int serverPort = 13102;

    boolean error = false;

    Socket socket = null;
    DataInputStream input = null;
    DataOutputStream output = null;

    public Client(){

        File serverIPFile = new File("serverIP");
        if(serverIPFile.exists()){
            Scanner in = null;
            try {
                in = new Scanner(new FileReader(serverIPFile));
                serverIP = in.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
            }
            in.close();

        }
        else
        {
            error = true;
        }

    }

    public int downloadMapCount(){
        try {

            socket = new Socket(serverIP, serverPort);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            int map_count = 0;

            output.writeUTF("MAPS");
            System.out.println("Client OUT: MAPS");

            System.out.println("Client IN: " + input.readUTF());

            map_count = input.readInt();
            System.out.println("Client IN: " + map_count);

            if(socket!=null)
                socket.close();
            if(input!=null)
                input.close();
            if(output!=null)
                output.close();

            return map_count;
        }catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
        return -1;
    }

    public boolean downloadMap(int map_numer) {

        try {

            socket = new Socket(serverIP, serverPort);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            String msgin;

            output.writeUTF("MAP_" + Integer.toString(map_numer));
            System.out.println("Out: MAP_" + Integer.toString(map_numer));

            msgin = input.readUTF();
            System.out.println("In: " + msgin);

            msgin = input.readUTF();
            System.out.println("In: " + msgin);

            PrintWriter zapis = new PrintWriter("map" + Integer.toString(map_numer));
            zapis.println(msgin);
            zapis.close();

            if(socket!=null)
                socket.close();
            if(input!=null)
                input.close();
            if(output!=null)
                output.close();

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean downloadPlayer(int player_number) {
        try {

            Socket socket1 = new Socket(serverIP, serverPort);//ip adress machine , port adress
            DataInputStream input = new DataInputStream(socket1.getInputStream());
            DataOutputStream output = new DataOutputStream(socket1.getOutputStream());
            String msgin;

            output.writeUTF("PLAYER_" + Integer.toString(player_number));

            msgin = input.readUTF();
            if (msgin.equals("RETURN_PLAYER_" + Integer.toString(player_number))) {
                System.out.println(msgin);
                PrintWriter zapis = new PrintWriter("player_params_" + Integer.toString(player_number));
                zapis.println(input.readDouble());
                zapis.println(input.readInt());
                zapis.println(input.readInt());
                zapis.println(input.readInt());
                zapis.println(input.readInt());
                zapis.close();
            }
            socket1.close();
            return true;

        }catch (Exception e) {
            System.out.println("Brak połączenia.");
        }
        finally {
            try {
                if(socket!=null)
                    socket.close();
                if(input!=null)
                    input.close();
                if(output!=null)
                    output.close();

            }catch(IOException e){}
        }
        return false;
    }

    public boolean downloadHighscores()
    {

        try {

            Socket socket1 = new Socket(serverIP, serverPort);//ip adress machine , port adress
            DataInputStream input = new DataInputStream(socket1.getInputStream());
            DataOutputStream output = new DataOutputStream(socket1.getOutputStream());
            PrintWriter zapis = null;

            String msgin;

            output.writeUTF("HIGHSCORES");
            msgin = input.readUTF();
            if (msgin.equals("RETURN_HIGHSCORES")) {
                System.out.println(msgin);
                msgin = input.readUTF();
                System.out.println(msgin);
                zapis = new PrintWriter("highscores");
                zapis.println(msgin);
            }

            if(zapis!=null)
                zapis.close();
            if(input!=null)
                input.close();
            if(socket1!=null)
                socket1.close();

            return true;
        }catch (Exception e) {
            System.out.println("Brak połączenia.");
        }
        return false;
    }

    public boolean setScore(String score)
    {
        try {
            Socket socket1 = new Socket(serverIP, serverPort);//ip adress machine , port adress
            DataInputStream input = new DataInputStream(socket1.getInputStream());
            DataOutputStream output = new DataOutputStream(socket1.getOutputStream());
            String msgin = "";
            output.writeUTF("SET_SCORE");
            output.writeUTF(score);
            socket1.close();

            return true;
        }catch (Exception e) {
            System.out.println("Brak połączenia.");
        }
        return false;
    }

    public boolean error(){
        return error;
    }
}



