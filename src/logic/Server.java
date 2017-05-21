package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import utils.UniqueButton;

/**
 *
 * @author t_ani
 */

/*Server = hunter*/
public class Server extends Logic{

    private final int port;
    private PrintWriter out;
    private Scanner in;
    
    public Server(int port) {
        this.port = port;
        long delay = 1000;
        TimerTask taskPerformer = new TimerTask() {
            @Override
            public void run() {
                startServer();
            }
        };
        new Timer().schedule(taskPerformer, delay);
    }
    
    @Override
    public int pressButton(String title, JButton button, int i, int j) {
        boolean steppedAway = genericStep(button, i, j);
        if (steppedAway) {
            out.printf("%d %d %d %d", lastMoved.i, lastMoved.j, i, j);
            out.println();
            out.flush();
            
            stepCount++;
            
            if (stepLeft() == 0) {
                return 0;
            }
            
            List<UniqueButton> validButtons = gatherValidButtons();
            if (validButtons.isEmpty()) {
                return 1;
            }
            
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
            gameTable[toX][toY].setText(PREY);
            setPrey(toX, toY, gameTable[toX][toY]);
            switchButtons(true);
        };
        new Thread(t).start();
    }
    
    @Override
    public boolean isItMe(final JButton button)
    { return button.getText().equals(me); }
    
    @Override
    public void setMe(final JButton button)
    { button.setText(me); }
    
    private void startServer() {
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket s = ss.accept();
            out = new PrintWriter(s.getOutputStream());
            in = new Scanner(s.getInputStream());
            
            receiveOneStep();
        } catch (IOException e) {
            System.err.printf("Can't start server on %d port.\n", port);
        }
    }
    
}
