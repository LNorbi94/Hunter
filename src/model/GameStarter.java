package model;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
            final int ret = gameCreator();
            if (-1 != ret) {
                dispose();
            }
        });
        getContentPane().add(button);
    }
    
    private int gameCreator() {
        final int choice = JOptionPane.showOptionDialog( null
                        , "Hogyan szeretne játszani?"
                        , "Játék kezdése"
                        , JOptionPane.YES_NO_OPTION
                        , JOptionPane.QUESTION_MESSAGE
                        , null
                        , new Object[] {"Offline", "Online"}
                        , "Offline" );
        if (0 == choice) {
            // TODO
        } else if (1 == choice) {
            
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
