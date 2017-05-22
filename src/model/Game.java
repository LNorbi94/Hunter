package model;

import hunter.Hunter;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logic.Logic;

/**
 * Játék grafikus megjelenítésére szolgáló osztály.
 * 
 * @author lestarn
 */
public class Game extends JFrame {
    
    private final int size;
    private final Logic gameLogic;
    private final JButton[][] gameTable;
    
    /**
     * A Game osztály konstruktora.
     * Inicializálja a megadott paraméterekkel az adattagokat 
     * és beállítja a megfelelő méretű táblát a játékhoz.
     * @param gameLogic - a játék módja, offline mód esetén a mesterséges intelligenciát reprezentáló osztály,
     * online mód esetén a kliens és szerver osztálya
     * @param size - játék tábla mérete
     */    
    public Game(final Logic gameLogic, final int size) {
        this.size = size;
        this.gameLogic = gameLogic;
        gameTable = new JButton[size][size];
        initialize();
    }
    
    /**
     * Inicializálja a játék tábláját, hozzáadja a megfelelő számú gombokat.
     */
    private void initialize() {
        setTitle("Vadászat");
        setBounds(100, 100, 1024, 768);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout( new GridLayout(size, size, 0, 0) );
        GameStarter.exit(this);
        
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                gameTable[i][j] = addButton(i, j);
                getContentPane().add( gameTable[i][j] );
            }
        }
        
        gameLogic.setButtons(gameTable);
        setVisible(true);
    }
    
    /**
     * Hozzáadja a játékosok gombjait a táblához.
     * Kezdetben a négy támadó a tábla négy sarkában van elhelyezve, 
     * míg a menekülő a tábla közepére.
     * Továbbá ellenőrzi a játék állását. A játék vége esetén külön ablakban megjeleníti,
     * hogy az adott játékos nyert vagy veszített.
     * @param i - játéktábla sorszáma
     * @param j - játéktábla oszlopszáma
     * @return - visszatérési értéke a módosított gomb
     */
    private JButton addButton(final int i, final int j) {
        final JButton button = new JButton();

        if (  (i == j && (i == 0 || i == size - 1))
           || (i == 0 && j == size - 1)
           || (j == 0 && i == size - 1) ) {
            button.setText(Logic.HUNTER);
            gameLogic.addHunter(i, j, button);
        }
        if (i == j && i == (size - 1) / 2) {
            button.setText(Logic.PREY);
            gameLogic.setPrey(i, j, button);
        }

        button.addActionListener( (ActionEvent e) -> {
            String title = "Vadászat";
            int winner = gameLogic.pressButton(button, i, j);
            String message;
            
            switch (winner) {
                case -1:
                    return;
                case 0:
                    message = "Sajnálom! Ön vesztett! :(";
                    break;
                default:
                    message = "Gratulálok! Ön nyert! :)";
                    break;
            }
            if (!message.isEmpty()) {
                final int choice = JOptionPane.showOptionDialog( null
                                , message + " Mit szeretne most tenni?"
                                , "Játék vége!"
                                , JOptionPane.YES_NO_OPTION
                                , JOptionPane.QUESTION_MESSAGE
                                , null
                                , new Object[] {"Új játék", "Kilépés"}
                                , "Kilépés" );
                
                if (0 == choice) {
                    Hunter.main(new String[0]);
                    dispose();
                } else {
                    System.exit(0);
                }
            }
        });

        return button;
    }
    
}