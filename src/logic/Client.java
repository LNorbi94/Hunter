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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author t_ani
 */
/*Client = prey */
public class Client extends Logic {
    
    private PrintWriter pw;
    private BufferedReader br;
    private Socket prey;
    
    int port;
    String ip;    
    
    
    public Client(int port, String ip) throws IOException {
        this.port = port;
        this.ip = ip;
        
        this.prey = new Socket(ip,port);
        this.pw = new PrintWriter(prey.getOutputStream(),true);
        this.br = new BufferedReader(new InputStreamReader(prey.getInputStream()));
        
    }
    
    
    public void play() throws IOException{
       System.out.println(br.readLine());
       
       //receive message from server
       //set Hunter's position from the message
       //make one step
       //send to server
    }

    
    
    @Override
    public int pressButton(String title, JButton button, int i, int j) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       genericStep(button,i,j);
       return -1;  
    }
    
    public static void main(String[] arg){
        Client c;
        try {
            c = new Client(8080,"localhost");
            c.play();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
