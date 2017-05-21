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
        this.me = PREY;
        this.host = host;
        this.port = port;
        
        try {
            Socket s = new Socket(host, port);
            out = new PrintWriter(s.getOutputStream());
            in = new Scanner(s.getInputStream());
        } catch (Exception e) {
            System.err.printf("Can't start client on %s host, %d port.\n"
                    , host, port);
            
        }
    }

    @Override
    public int pressButton(String title, JButton button, int i, int j) {
        boolean steppedAway = genericStep(button, i, j);
        if (steppedAway) {
            out.printf("%d %d %d %d", lastMoved.i, lastMoved.j, i, j);
            out.println();
            out.flush();
            
            receiveOneStep();
        }
        return -1; 
    }
    
    private void receiveOneStep() {
        switchButtons(false);
        Runnable t = () -> {
            int fromX = in.nextInt();
            int fromY = in.nextInt();
            int toX  = in.nextInt();
            int toY  = in.nextInt();
            gameTable[fromX][fromY].setText("");
            gameTable[toX][toY].setText(HUNTER);
            switchButtons(true);
        };
        new Thread(t).start();
    }
    
    @Override
    public boolean isItMe(final JButton button)
    { return button.getText().equals(PREY); }
    
    @Override
    public void setMe(final JButton button)
    { button.setText(PREY); }
}
