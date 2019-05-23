package com.example.riseapp.Helper;

import com.example.riseapp.Contacte;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GestionConexion extends Thread {

    static int puerto = 1234; //puerto del servidor
    static String ip = "192.168.12.203";//10.0.2.2

    static DataInputStream dis;
    static DataOutputStream dos;
    private String lang;

    private static ArrayList<Contacte> contactes = new ArrayList<>();

    public static ArrayList<Contacte> getContactes() {
        return contactes;
    }

    public void setContactes(ArrayList<Contacte> contactes) {
        this.contactes = contactes;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public GestionConexion() {

    }

    @Override
    public void run() {
        try {
            Socket sk = new Socket(ip, puerto);
            //hemos conectado
            dis = new DataInputStream(sk.getInputStream());
            dos = new DataOutputStream(sk.getOutputStream());
            System.out.println("Se ha conectado " + sk.getRemoteSocketAddress());

            dos.writeUTF(lang);
            recibirContactos();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //LEER RANDOM

    }

    private void recibirContactos() throws IOException {
        int size= dis.readInt();
        for (int i = 0; i < size; i++) {
            int telefon=dis.readInt();
            String nom=dis.readUTF();
            String servei=dis.readUTF();
            String adreca=dis.readUTF();
            double longitut=dis.readDouble();
            double latitut=dis.readDouble();
            String web = dis.readUTF();
            String correu= dis.readUTF();
            contactes.add(new Contacte(telefon, nom, servei, adreca, longitut,latitut, web, correu));


        }

    }


}

