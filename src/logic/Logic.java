package logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import utils.UniqueButton;

/**
 *
 * @author lestarn
 */
public abstract class Logic {
    
    public final static String HUNTER   = "Támadó";
    public final static String PREY     = "Menekülő";
    public final static String NOONE    = "-";
    
    protected JButton[][] gameTable;
    
    protected List<UniqueButton> Hunters;
    protected UniqueButton Prey;
    
    public String me;
    public UniqueButton selected;
    public UniqueButton lastMoved;
    
    public void setButtons(final JButton[][] gameTable) {
        this.gameTable = gameTable;
        me = Logic.HUNTER;
    }
    
    public void addHunter(final int i, final int j, final JButton place) {
        if (null == Hunters) {
            Hunters = new ArrayList<>();
        }
        UniqueButton g = new UniqueButton(i, j, place);
        Hunters.add(g);
    }
    
    public void changeHunter(final int i, final int j, final JButton place
            , JButton from) {
        if (null == from) {
            return;
        }
        for (int k = 0; k < Hunters.size(); ++k) {
            if (Hunters.get(k).place == from) {
                Hunters.get(k).setData(i, j, place);
            }
        }
    }
    
    public void setPrey(final int i, final int j, final JButton place) {
        if (null == Prey) {
            Prey = new UniqueButton(i, j, place);
        } else {
            Prey.setData(i, j, place);
        }
    }
    
    public abstract int pressButton(String title, JButton button
            , final int i, final int j);
    
    public boolean genericStep(final JButton button, final int i, final int j) {
        if (selected == null) {
            if (button.getText().equals(me)) {
                button.setBackground( new Color(125, 127, 255) );
                selected = new UniqueButton(i, j, button);
            }
            return false;
        }
        if (selected.place == button
                || (isNextTo(i, j, selected) && button.getText().equals(""))) {
            boolean steppedAway = selected.place != button;
            selected.place.setBackground(new JButton().getBackground());
            selected.place.setText("");
            lastMoved = selected;
            selected = null;
            button.setText(me);
            return steppedAway;
        }
        return false;
    }

    protected boolean isNextTo(int i, int j, UniqueButton selected) {
        return  (Math.abs(selected.i - i) <= 1 && Math.abs(selected.j - j) == 0) ||
                (Math.abs(selected.i - i) == 0 && Math.abs(selected.j - j) <= 1) ;
    }
    
    protected boolean isValid(int i, int j) {
        final int gameSize = gameTable.length;
        return i >= 0 && j >= 0 && i < gameSize && j < gameSize;
    }
    
    protected void switchButtons(boolean state) {
        for (JButton[] buttons : gameTable) {
            for (JButton button : buttons) {
                button.setEnabled(state);
            }
        }
    }
    
}