/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client zum Empfangen von Wetterdaten
 * 
 * @author Julian Oestreich jo87casi@studserv.uni-leipzig.de
 */
public class client {
    /**
     * Startet den Client
     * @param args 
     */
    public static void main(String[] args){
        
        /**
         * Scanner zum Erfassen von Benutzereingaben
         */
        Scanner eingabe = new Scanner(System.in);
        
        try {
            // Socket um Anfragen an den Server zu senden
            Socket client = new Socket("localhost", 5554);
            System.out.println("Client gestartet\nBeenden mit e\n");
            
            //  Streams
            OutputStream out = client.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            
            InputStream in = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));            
            //------------------------------
            
            System.out.println("###########################################\n"
                         + "### Um Wetterdaten zu empfangen, geben  ###\n"
                         + "### Sie bitte eine gewünschte Stadt ein ###\n"
                         + "###########################################\n");
            /**
             * Eingabe an Server senden
             */
            boolean exit = true;
            while(exit) {
                System.out.print("Eingabe machen: ");
                String anServer = eingabe.nextLine();
                if(anServer.equals("e")) {
                    exit = false; 
                    break;
                }
                writer.write(anServer + "\n");
                writer.flush();  
                String s = null;
                while((s = reader.readLine()) != null) {
                    System.out.println("Empfangen vom Server: " + s);
                    break;
                    }
                }
            /**
             * Streams schließen und Programm beenden
             */
            reader.close();
            writer.close();
            System.out.println("Client beendet");
            System.exit(0);
            
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
