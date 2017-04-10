package logic;

import javax.swing.JButton;

/**
 *
 * @author lestarn
 */
public class AI extends Logic {
    
    @Override
    public int pressButton(String title, JButton button, final int i, final int j) {
        genericStep(button, i, j);
        return -1;
    }
    
}
