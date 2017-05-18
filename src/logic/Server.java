package logic;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;

/**
 *
 * @author t_ani
 */

/*Server = hunter*/
public class Server extends Logic{

    private int port;
    private PrintWriter out;
    private Scanner in;
    
    public Server(int port) {
        this.port = port;
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket s = ss.accept();
            out = new PrintWriter(s.getOutputStream());
            in = new Scanner(s.getInputStream());
            receiveOneStep();
        } catch (Exception e) {
            System.err.printf("Can't start server on %d port.\n", port);
            System.out.println(e.getMessage());
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
        String[] nextSteps = in.nextLine().split(" ");
        int fromX = Integer.parseInt(nextSteps[0]);
        int fromY = Integer.parseInt(nextSteps[1]);
        int toX = Integer.parseInt(nextSteps[2]);
        int toY = Integer.parseInt(nextSteps[3]);
        
        gameTable[fromX][fromY].setText("");
        gameTable[toX][toY].setText(PREY);
        switchButtons(true);
    }
    
    @Override
    public boolean isItMe(final JButton button)
    { return button.getText().equals(me); }
    
    @Override
    public void setMe(final JButton button)
    { button.setText(me); }
    
}
