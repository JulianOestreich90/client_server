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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server zum Versenden von Wetterdaten
 * 
 * @author Julian Oestreich jo87casi@studserv.uni-leipzig.de
 */
public class server {
    /**
     * Startet den Server
     * @param args 
     */
    public static void main(String[] args){
        
        try {
            //neuer ServerSocket an Port 5554    
            ServerSocket server = new ServerSocket(5554);
            System.out.println("Server gestartet");
            
            Socket connection = server.accept();
            
            //  Streams
            OutputStream out = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //------------------------------
            
            // Eine HashMap initializiert mit meinen Wetterdaten
            HashMap<String, String> wetterdaten = new HashMap<String, String>();            
            wetterdaten.put("leipzig", "Sonnig, 23 Grad");            
            wetterdaten.put("siegen", "Sonnig, 20 Grad");            
            wetterdaten.put("hamburg", "Nebelig, 18 Grad");
            wetterdaten.put("frankfurt", "Regen, 19 Grad");
            wetterdaten.put("osnabrück", "Windig, 24 Grad");
            
            String input = null;
            String send = null;
            
            //Endlosschleife, solange Input Stream nicht leer ist
            while( (input = reader.readLine() ) != null) {
                
                System.out.println("Empfangen von Client: " + input);
                /**
                 * Wenn die empfangenen Daten aus dem Inputstream in der Hashmap als Key 
                 * gefunden werden können, sende die zugehörigen Daten über den Outputstream
                 */
                if(wetterdaten.containsKey(input.toLowerCase())){                 
                    send=input + ": " + wetterdaten.get(input.toLowerCase());               
                }else{
                    send="zu " + input + " gibt es leider keine Wetterdaten. Versuche: Leipzig, Hamburg, Siegen, Frankfurt oder Osnabrück";
                }
                writer.write(send + "\n");
                writer.flush();
            }
            
                       
            writer.close();
            reader.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
