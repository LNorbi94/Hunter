package model;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author lestarn
 */
public class GameStarter extends JFrame {
    
    private final int NUM_OF_GAMES;
    private final int GAME_SIZE_DIFFERENCE;
    
    public GameStarter() {
        NUM_OF_GAMES = GAME_SIZE_DIFFERENCE = 3;
    }
    
    public GameStarter(final int n) {
        NUM_OF_GAMES = GAME_SIZE_DIFFERENCE = n;
        initialize();
    }
    
    private void initialize() {
        setTitle("Vadászat");
        setBounds(100, 100, 310, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout( new FlowLayout(FlowLayout.CENTER, 5, 5) );
        setVisible(true);
        
        for (int i = GAME_SIZE_DIFFERENCE
                ; i <= NUM_OF_GAMES * GAME_SIZE_DIFFERENCE
                ; i += GAME_SIZE_DIFFERENCE) {
            createGameButton(i);
        }
    }
    
    private void createGameButton(final int size) {
        JButton button = new JButton(size + "x" + size);
        button.addActionListener((ActionEvent e) -> {
            // new GameWindow() ?
            dispose();
        });
        getContentPane().add(button);
    }
}
