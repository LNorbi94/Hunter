package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import utils.UniqueButton;

/**
 *
 * A Client osztály a Logic absztrakt osztály leszármazottja. 
 * Online játék esetén a menekülő játékost reprezentálja.
 * Socketek segítségével képes kommunukálni a Server osztályt példányosító támadó játékossal.
 * Tartalmazza Logic osztály metódusait, továbbá megvalósítja a 
 * pressButton, isItMe és setMe absztrakt metódusokat.
 * @author t_ani
 */
public class Client extends Logic {
    
    private PrintWriter out;
    private Scanner in;
    
    private String host;
    private int port;
    
   /**
    * Client osztály konstruktora, mely a paraméterként megadott host-hoz és port-hoz csatlakozik.
    * Példányosít egy írót és egy olvasót, melyek segítségével a szerver osztállyal
    * - támadó játékossal - kommunikál.
    * @param host - csatlakozni kívánt host, String típusú
    * @param port - csatlakozni kívánt port, int típusú
    */ 
    public Client(String host, int port) {
        this.me = PREY;
        this.host = host;
        this.port = port;
        
        try {
            Socket s = new Socket(host, port);
            out = new PrintWriter(s.getOutputStream());
            in = new Scanner(s.getInputStream());
        } catch (IOException e) {
            System.err.printf("Can't start client on %s host, %d port.\n", host, port);
        }
    }

    /**
     * A Logic osztály absztrakt pressButton metódusának felüldefiniálása.
     * Ha a kijelölt gombbal a menekülő játékos tud lépni, akkor a játéktáblán átállítja a játékost.
     * Majd az új pozícióit elküldi a socketek segítségével a támadó játékosnak.
     * Ezután fogadja az üzenetet a támadótól, amely tartalmazza az új pozíciókat.
     * Végül ellenőrzi, a játék állását.
     * @param button - kattintott gomb
     * @param i - új lépés sorszáma
     * @param j - új lépés oszlopszáma
     * @return - -1, ha még nem nyert egyik játékos sem
     * 0, ha a menekülő nem tud lépni, azaz vesztett
     * 1, ha elfogytak a támadó lépései, azaz nyert a menekülő
     */
    @Override
    public int pressButton(JButton button, int i, int j) {
        boolean steppedAway = genericStep(button, i, j);
        setPrey(i, j, button);
        if (steppedAway) {
            out.printf("%d %d %d %d", lastMoved.i, lastMoved.j, i, j);
            out.println();
            out.flush();
            
            receiveOneStep();
            stepCount++;
            
            List<UniqueButton> validButtons = gatherValidButtons();
            if (validButtons.isEmpty()) {
                return 0;
            }
            
            if (stepLeft() == 0) {
                return 1;
            }
        }
        return -1; 
    }
    
    /** 
     * A támadó játékostól fogad egy üzenetet és a saját tábláján a kapott értékek szerint átállítja a támadó játékost.
     * Az üzenet négy pozíciót tartalmaz: 
     * a támadó régi pozíciójának sor- és oszlopszámát, illetve a lépés utáni, új pozíció sor- és oszlopszámát.
     */
    private void receiveOneStep() {
        switchButtons(false);
        int fromX = in.nextInt();
        int fromY = in.nextInt();
        int toX  = in.nextInt();
        int toY  = in.nextInt();
        gameTable[fromX][fromY].setText("");
        gameTable[toX][toY].setText(HUNTER);
        switchButtons(true);
    }
    
    /**
     * Logic osztály absztrakt isItMe metódus felüldefiniálása.
     * Megvizsgálja, hogy a kiválasztott gomb a menekülő játékos e.
     * @param button - kiválasztott gomb
     * @return igaz, ha a gomb címkéje PREY, különben hamis
     */
    @Override
    public boolean isItMe(final JButton button)
    { return button.getText().equals(PREY); }
    
    /**
     * Logic osztály absztrakt setMe metódusánák felüldefiniálása.
     * Beállítja a kiválaszott gomb címkéjét a menekülőre.
     * @param button - kiválaszott gomb
     */
    @Override
    public void setMe(final JButton button)
    { button.setText(PREY); }
}
