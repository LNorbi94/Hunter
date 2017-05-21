package model;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import logic.AI;
import logic.Client;
import logic.Logic;
import logic.Server;

/**
 * Biztosítja a játék elindítását, játék méretének és módjának (offline/online) kiválasztását.
 * 
 * @author lestarn
 */
public class GameStarter extends JFrame {
    
    private final int DEFAULT_SIZE = 3;
    
    private int NUM_OF_GAMES;
    private int GAME_SIZE_DIFFERENCE;
    
    /**
     * GameStarter osztály konstruktora.
     * Beállítja a játékok számát és a játék méretét 0-ra.
     */
    public GameStarter() {
        this(0, 0);
    }
    
    /**
     * GameStarter osztály egy paraméteres konstruktora.
     * Beállítja a játékok számát és a játék méretét n-re.
     * @param n - játékok száma, játék mérete 
     */
    public GameStarter(final int n) {
        this(n, n);
    }
    
    /**
     * GameStarter osztály két paraméteres konstruktora.
     * Beállítja a játékok számát n-re és a játék méretét m-re,
     * amennyiben megfelelnek a feltételeknek.
     * Különben az alapértelmezett értékre.
     * @param n - játékok száma
     * @param m - játék mérete
     */
    public GameStarter(final int n, final int m) {
        if (2 < n && n < 16 && 1 < m && m < 10) {
            setGameSize(n, n);
        } else {
            setDefaultSize();
        }
        initialize();
    }
    
    /**
     * Beállítja az osztály adattagjait a paraméterek szerint.
     * @param n - játékok száma
     * @param m - játék mérete
     */
    private void setGameSize(int n, int m) {
        NUM_OF_GAMES = n;
        GAME_SIZE_DIFFERENCE = m;
    }
    
    /**
     * Beállítja a játékok számát és a játékok méretét az alapértelmezett értékre.
     */
    private void setDefaultSize() {
        setGameSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }
    
    /**
     * 
     */
    private void initialize() {
        setTitle("Vadászat");
        int height = 60 * (NUM_OF_GAMES / 4);
        if (5 >= NUM_OF_GAMES) {
            height = 75;
        }
        setBounds(100, 100, 400, height);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout( new FlowLayout(FlowLayout.CENTER, 5, 5) );
        
        for (int i = 1; i <= NUM_OF_GAMES; ++i) {
            createGameButton(i * GAME_SIZE_DIFFERENCE);
        }
        
        exit(this);
        
        setVisible(true);
    }
    
    /**
     * A játék méretének kiválasztására szolgáló gombok létrehozása.
     * @param size - tábla mérete, sor- és oszlopszám
     */
    private void createGameButton(final int size) {
        JButton button = new JButton(size + "x" + size);
        button.addActionListener((ActionEvent e) -> {
            final int ret = gameCreator(size);
            if (-1 != ret) {
                dispose();
            }
        });
        getContentPane().add(button);
    }
    
    /**
     * A játék indításához szükséges ablakokat hozza létre.
     * Lehetőség van választani offline és online játék közül.
     * Online játék esetén host és port megadása szükséges.
     * @param size - játéktábla mérete
     * @return - kiválasztott mód száma
     * 0, offline mód esetén
     * 1, online játék esetén
     */
    
    private int gameCreator(final int size) {
        final int choice = JOptionPane.showOptionDialog( null
                        , "Hogyan szeretne játszani?"
                        , "Játék kezdése"
                        , JOptionPane.YES_NO_OPTION
                        , JOptionPane.QUESTION_MESSAGE
                        , null
                        , new Object[] {"Offline", "Online"}
                        , "Offline" );
        if (0 == choice) {
            Logic gameLogic = new AI();
            Game game = new Game(gameLogic, size);
        } else if (1 == choice) {
            
            JOptionPane.showMessageDialog(null
                    , "Ez a játékmód csak 4x4-es táblával játszható!"
                            + " A tábla mérete átállításra került.");
            
            JFrame frame;
            JTextField ipTextField;
            JTextField portTextField;
            
            frame = new JFrame();
            frame.setTitle("Adatok megadása");
            frame.setBounds(100, 100, 300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(null);
            
            ipTextField = new JTextField();
            ipTextField.setBounds(128, 28, 86, 20);
            frame.getContentPane().add(ipTextField);
            ipTextField.setColumns(10);
            
            JLabel lblIP = new JLabel("IP");
            lblIP.setBounds(65, 31, 46, 14);
            frame.getContentPane().add(lblIP);
            
            portTextField = new JTextField();
            portTextField.setBounds(128, 56, 86, 20);
            frame.getContentPane().add(portTextField);
            portTextField.setColumns(10);
            
            JLabel lblPort = new JLabel("Port");
            lblPort.setBounds(65, 60, 46, 14);
            frame.getContentPane().add(lblPort);
            
            JButton okButton = new JButton("OK");
            JButton backButton = new JButton("Mégse");
            
            okButton.setBounds(50, 110, 86, 20);
            backButton.setBounds(140, 110, 86, 20);
            
            okButton.addActionListener((ActionEvent e) -> {
                String host = ipTextField.getText();
                try {
                    int port = Integer.parseInt(portTextField.getText());
                    Logic gameLogic;
                    
                    if (!host.isEmpty()) {
                        gameLogic = new Client(host, port);
                    } else {
                        gameLogic = new Server(port);
                    }
                    
                    Game game = new Game(gameLogic, 4);
                    frame.dispose();
                } catch (NumberFormatException numE) {
                    JOptionPane.showMessageDialog(null
                            , "Kérem számot adjon meg portként!");
                }
            });
            
            backButton.addActionListener((ActionEvent e) -> {
                exit();
            });
            
            frame.getContentPane().add(okButton);
            frame.getContentPane().add(backButton);
            
            frame.setVisible(true);
        }
        return choice;
    }
    
    /**
     * Bezárja a játék ablakát.
     * @param frame - bezárandó ablak
     */
    public static void exit(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                exit();
            }
        });
    }
    
    /**
     * A játékból való kilépés megerősítésére szolgáló ablak megjelenítése.
     * A kilépés opció esetén az alkalmazás bezárása.
     */
    private static void exit() {
        final int choice = JOptionPane.showOptionDialog( null
                        , "Biztosan ki szeretne lépni?"
                        , "Kilépés megerősítése"
                        , JOptionPane.YES_NO_OPTION
                        , JOptionPane.QUESTION_MESSAGE
                        , null
                        , new Object[] {"Igen", "Nem"}
                        , "Nem" );
        if (0 == choice) {
            System.exit(0);
        }
    }
}
