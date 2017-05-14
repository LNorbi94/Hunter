package model;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *  A class which provides a starting interface to choose what game sizes
 *  and mode to start with.
 *  It also provides a function for closing the game for further use.
 *  @author lestarn
 */
public class GameStarter extends JFrame {
    
    private final int DEFAULT_SIZE = 3;
    
    private int NUM_OF_GAMES;
    private int GAME_SIZE_DIFFERENCE;
    
    public GameStarter() {
        this(0, 0);
    }
    
    public GameStarter(final int n) {
        this(n, n);
    }
    
    public GameStarter(final int n, final int m) {
        if (2 < n && n < 16 && 1 < m && m < 10) {
            setGameSize(n, n);
        } else {
            setDefaultSize();
        }
        initialize();
    }
    
    private void setGameSize(int n, int m) {
        NUM_OF_GAMES = n;
        GAME_SIZE_DIFFERENCE = m;
    }
    
    private void setDefaultSize() {
        setGameSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }
    
    private void initialize() {
        setTitle("Vadászat");
        int height = 60 * (NUM_OF_GAMES / 4);
        if (4 > NUM_OF_GAMES) {
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
            JFrame frame;
            JTextField ipTextField;
            JTextField portTextField;
            
            frame = new JFrame();
            frame.setBounds(100,100,300,200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(null);
            
            ipTextField = new JTextField();
            ipTextField.setBounds(128,28,86,20);
            frame.getContentPane().add(ipTextField);
            ipTextField.setColumns(10);
            
            JLabel lblIP = new JLabel("IP");
            lblIP.setBounds(65,31,46,14);
            frame.getContentPane().add(lblIP);
            
            portTextField = new JTextField();
            portTextField.setBounds(128,56,86,20);
            frame.getContentPane().add(portTextField);
            portTextField.setColumns(10);
            
            JLabel lblPort = new JLabel("Port");
            lblPort.setBounds(65,60,46,14);
            frame.getContentPane().add(lblPort);
            
            JButton okButton = new JButton("OK");
            JButton backButton = new JButton("Mégse");
            
            okButton.setBounds(50,110,86,20);
            backButton.setBounds(140,110,86,20);
            
            okButton.addActionListener((ActionEvent e) -> {
               // Logic gameLogic = new AI();
                //Game game = new Game(gameLogic,size);
                Server s = new Server(Integer.parseInt(portTextField.getText()),ipTextField.getText());
                try {
                    Client c = new Client(Integer.parseInt(portTextField.getText()),ipTextField.getText());
                    s.start(); // próba az üzenet küldésre,fogadásra
                    c.play();  // próba az üzenet küldésre,fogadásra
                    Game game1 = new Game(s,size);
                    Game game2 = new Game(c,size);
                } catch (IOException ex) {
                    System.err.println("Error with connecting client");
                    Logger.getLogger(GameStarter.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            });
            
            backButton.addActionListener((ActionEvent e) -> {
                exit();
            });
            
            frame.getContentPane().add(okButton);
            frame.getContentPane().add(backButton);
            
            frame.setVisible(true);
            /*Logic gameLogic = new AI();
            Game game = new Game(gameLogic,size);*/
        }
        return choice;
    }
    
    public static void exit(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                exit();
            }
        });
    }
    
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
