package model;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import logic.Logic;

/**
 *
 * @author lestarn
 */
public class Game extends JFrame {
    
    private final int size;
    private final Logic gameLogic;
    private final JButton[][] gameTable;
    
    public Game(final Logic gameLogic, final int size) {
        this.size = size;
        this.gameLogic = gameLogic;
        gameTable = new JButton[size][size];
        initialize();
    }
    
    private void initialize() {
        setTitle("Vadászat");
        setBounds(100, 100, 1024, 768);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout( new GridLayout(size, size, 0, 0) );
        GameStarter.exit(this);
        // TODO: createMenuBar();
        
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                gameTable[i][j] = addButton(i, j);
                getContentPane().add( gameTable[i][j] );
            }
        }
        
        gameLogic.setButtons(gameTable);
        setVisible(true);
    }
    
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
            int winner = gameLogic.pressButton(title, button, i, j);
            
            if (-1 == winner) {
                return;
            }
            
            setTitle(title);
        });

        return button;
    }
    
}
