/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectefinalandroid;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleix
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    static boolean sigue = true; //para parar el while
    public static final int SOCKET = 1234;

    public static void main(String[] args) {

        try {
            ServerSocket ssk = new ServerSocket(SOCKET);
            System.out.println("Servidor iniciado");

            while (sigue) {
                Socket sk = ssk.accept();
                ServidorRun ser = new ServidorRun(sk);
                
                ser.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}
    
     static class ServidorRun extends Thread {

        Socket sk;

        DataInputStream dis;
        DataOutputStream dos;
        int idCliente;
        int idDestino;
        ArrayList<CONTACTE> contactes;
        
        ServidorRun(Socket sk) {
            this.sk = sk;
            
        }

        @Override
        public void run() {
            
            
            System.out.println("Establecida la conexión con " + sk.getInetAddress().getHostAddress());
            contactes = new ArrayList<>();
            try {
                
//gestionamos la comunicación con un cliente
                dis = new DataInputStream(sk.getInputStream());
                dos = new DataOutputStream(sk.getOutputStream());
                
                Connection con = null;
                Statement sentencia = null;
                ResultSet resultat = null;

                con = GestionConexionBDD.getConnection();
                sentencia = con.createStatement();
                
               
                
                sentencia.executeQuery("SELECT * FROM CONTACTES");
                resultat = sentencia.getResultSet();
                String idioma=dis.readUTF();
               
                resultSet("SERVEI"+idioma, resultat);
                enviaResultats();
                
             } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void resultSet(String idioma, ResultSet resultat) throws SQLException {
            while(resultat.next()){
                    int telefon=resultat.getInt("TELEFON");
                    String nom=resultat.getString("NOM");
                    String directora=resultat.getString("DIRECTORA");
                    String servei=resultat.getString(idioma);
                    String adreca=resultat.getString("ADRECA");
                    double longitut=resultat.getDouble("LONGITUT");
                    double latitut=resultat.getDouble("LATITUT");
                    contactes.add(new CONTACTE(telefon, nom, directora, servei, adreca, longitut,latitut));
                    
                }
        }

        private void enviaResultats() throws IOException {
            dos.writeInt(contactes.size());
            for (int i = 0; i < contactes.size(); i++) {
                dos.writeInt(contactes.get(i).getTELEFON());
                dos.writeUTF(contactes.get(i).getNOM());
                dos.writeUTF(contactes.get(i).getDIRECTORA());
                dos.writeUTF(contactes.get(i).getSERVEI());
                dos.writeUTF(contactes.get(i).getADRECA());
                dos.writeDouble(contactes.get(i).getLONGITUT());
                dos.writeDouble(contactes.get(i).getLATITUT());
                
            }
        }
        
}
}

