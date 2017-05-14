/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author t_ani
 */

/*Server = hunter*/
public class Server extends Logic{

    private int port;
    private String ip;
    private PrintWriter pw;
    private BufferedReader br;
    
    private ServerSocket hunter;
    
    
    
    public Server(int port, String ip) {
        this.port = port;
        this.ip = ip;
        try {
            this.hunter = new ServerSocket(port);                    
        } catch (IOException e) {
            System.err.print("Error with starting the hunter.");
            e.printStackTrace();
        }
    }
    
    /*Connect the client, make one step, send to the client*/
    public void start() throws IOException{
        try{
            Socket prey = hunter.accept();
            pw = new PrintWriter(prey.getOutputStream(),true);
            br = new BufferedReader(new InputStreamReader(prey.getInputStream()));
   
        }catch(IOException e){
            System.err.print("Error with connecting client");
            e.printStackTrace();
        }
        
       //example: pw.println("Hello kliens");
        
        //make one step and send to client
        
    }
    
    
    @Override
    public int pressButton(String title, JButton button, int i, int j) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static void main(String[] args){
        Server s = new Server(8080,"localhost");
        try {
            s.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
