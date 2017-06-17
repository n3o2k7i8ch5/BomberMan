package com.bomber.man.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Murspi on 13.06.2017.
 */
public class Client {

    String serverIP;
    final int serverPort = 13102;

    boolean error = false;

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

            Socket socket = new Socket(serverIP, serverPort);//ip adress machine, port adress
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            String msgin, msgout;
            int map_count;

            msgout = "MAPS";
            output.writeUTF(msgout);

            msgin = input.readUTF();
            System.out.println(msgin);

            map_count = input.readInt();
            System.out.println(map_count);
            return map_count;
        }catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
        return 0;
    }

    public boolean downloadMap(int map_numer) {
        try {
            Socket socket = new Socket(serverIP, serverPort);//ip adress machine, port adress
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            String msgin;

            output.writeUTF("MAP_" + Integer.toString(map_numer));
            msgin = input.readUTF();
            System.out.println(msgin);

            msgin = input.readUTF();
            System.out.println(msgin);

            PrintWriter zapis = new PrintWriter("map" + Integer.toString(map_numer));
            zapis.println(msgin);
            zapis.close();

            return true;

        } catch (Exception e) {
            System.out.println("Brak połączenia.");
        }
        return false;
    }

    public boolean downloadPlayer() {
        try {
            Socket socket1 = new Socket(serverIP, serverPort);//ip adress machine , port adress
            DataInputStream input = new DataInputStream(socket1.getInputStream());
            DataOutputStream output = new DataOutputStream(socket1.getOutputStream());
            String msgin;

            output.writeUTF("PLAYER_1");

            msgin = input.readUTF();
            if (msgin.equals("RETURN_PLAYER_1")) {
                System.out.println(msgin);
                msgin = input.readUTF();
                PrintWriter zapis = new PrintWriter("player_client");
                zapis.println(msgin);
                zapis.close();
                System.out.println(msgin);
            }
            socket1.close();
            return true;

        }catch (Exception e) {
            System.out.println("Brak połączenia.");
        }
        return false;
    }

    public boolean downloadHighscores()
    {

        Socket socket1 = null;
        PrintWriter zapis = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        try {
            socket1 = new Socket(serverIP, serverPort);//ip adress machine , port adress
            input = new DataInputStream(socket1.getInputStream());
            output = new DataOutputStream(socket1.getOutputStream());
            String msgin;

            output.writeUTF("HIGHSCORES");
            msgin = input.readUTF();
            if (msgin.equals("RETURN_HIGHSCORES")) {
                System.out.println(msgin);
                msgin = input.readUTF();
                System.out.println(msgin);
                zapis = new PrintWriter("highscores_clinet");
                zapis.println(msgin);
            }
            return true;
        }catch (Exception e) {
            System.out.println("Brak połączenia.");
        }finally {
            try {
                zapis.close();
                input.close();
                input.close();
                socket1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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



