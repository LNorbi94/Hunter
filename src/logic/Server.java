package logic;

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
 * A Server osztály a Logic absztrakt osztály leszármazottja. 
 * Online játék esetén a támadó játékost reprezentálja.
 * Socketek segítségével képes kommunukálni a Client osztályt példányosító menekülő játékossal.
 * Tartalmazza Logic osztály metódusait, továbbá megvalósítja a 
 * pressButton, isItMe és setMe absztrakt metódusokat.
 * 
 * @author t_ani
 */

public class Server extends Logic{

    private final int port;
    private PrintWriter out;
    private Scanner in;
    
    /**
     * Server osztály konstruktora.
     * A kapott port számot beállítja, majd elindít egy ServerSocket-et
     * a startServer() metódus meghívásával.
     * @param port - ezen a porton fog elindulni a szerver.
     */
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
    
    /**
     * A Logic osztály absztrakt pressButton metódusának felüldefiniálása.
     * Ha a kijelölt gombbal a támadó játékos tud lépni, akkor a játéktáblán átállítja a játékost.
     * Majd az új pozícióit elküldi socketek segítségével a menekülő játékosnak.
     * Ezután fogadja az üzenetet a menekülőtől, amely tartalmazza az új pozíciókat, és át is állítja a játéktáblán a menekülőt.
     * Végül ellenőrzi, a játék állását.
     * @param button - kattintott gomb
     * @param i - új lépés sorszáma
     * @param j - új lépés oszlopszáma
     * @return - -1, ha még nem nyert egyik játékos sem
     * 0, ha elfogytak a lépései, azaz vesztett
     * 1, ha a menekülő nem tud lépni, azaz nyert a támadó
     */
    @Override
    public int pressButton(JButton button, int i, int j) {
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

    /** 
     * A menekülő játékostól fogad egy üzenetet és a saját tábláján a kapott értékek szerint átállítja a támadó játékost.
     * Az üzenet négy pozíciót tartalmaz: 
     * a támadó régi pozíciójának sor- és oszlopszámát, illetve a lépés utáni, új pozíció sor- és oszlopszámát.
     */
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
    
    /**
     * Logic osztály absztrakt isItMe metódus felüldefiniálása.
     * Megvizsgálja, hogy a kiválasztott gomb a menekülő játékos e.
     * @param button - kiválasztott gomb
     * @return igaz, ha a gomb címkéje PREY, különben hamis
     */
    @Override
    public boolean isItMe(final JButton button)
    { return button.getText().equals(me); }
    
    /**
     * Logic osztály absztrakt setMe metódusánák felüldefiniálása.
     * Beállítja a kiválaszott gomb címkéjét a menekülőre.
     * @param button - kiválaszott gomb
     */
    @Override
    public void setMe(final JButton button)
    { button.setText(me); }
    
    /**
     * Elindít egy szervert a konstruktorban megadott porton és fogadja a kliens csatlakozását.
     * Példányosítja a hozzá tartozó írót és olvasót,
     * majd  fogadja az első üzenetet a klienstől. 
     */
    private void startServer() {
        switchButtons(false);
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
