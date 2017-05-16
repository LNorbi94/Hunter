package utils;

import javax.swing.JButton;

/**
 *
 * @author lestarn
 */
public class UniqueButton {
    public int distance;
    public int i;
    public int j;
    public JButton place;

    public UniqueButton(final int i, final int j, final JButton place) {
        setData(i, j, place);
    }

    public final void setData(final int i, final int j, final JButton place) {
        this.i = i;
        this.j = j;
        this.place = place;
    }
    
    public double distanceFrom(UniqueButton other) {
        return Math.sqrt(Math.pow(i - other.i, 2) + Math.pow(j - other.j, 2));
    }
}