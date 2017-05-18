package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;

/**
 *
 * @author t_ani
 */
/*Client = prey */
public class Client extends Logic {
    
    private PrintWriter out;
    private Scanner in;
    
    private String host;
    private int port;
    
    
    public Client(String host, int port) {
        this.port = port;
        this.host = host;
        
        try (Socket s = new Socket(host, port)) {
            out = new PrintWriter(s.getOutputStream());
            in = new Scanner(s.getInputStream());
            
            receiveOneStep();
        } catch (Exception e) {
            System.err.printf("Can't start client on %s host, %d port.\n"
                    , host, port);
            
        }
    }

    @Override
    public int pressButton(String title, JButton button, int i, int j) {
        boolean steppedAway = genericStep(button, i, j);
        if (steppedAway) {
            switchButtons(false);
            out.printf("%d %d %d %d", lastMoved.i, lastMoved.j, i, j);
            out.println();
            out.flush();
            
            receiveOneStep();
            switchButtons(true);
        }
        return -1; 
    }
    
    private void receiveOneStep() {
        String[] nextSteps = in.nextLine().split(" ");
        int fromX = Integer.parseInt(nextSteps[0]);
        int fromY = Integer.parseInt(nextSteps[1]);
        int toX = Integer.parseInt(nextSteps[2]);
        int toY = Integer.parseInt(nextSteps[3]);

        gameTable[fromX][fromY].setText("");
        gameTable[toX][toY].setText(HUNTER);
    }
}
