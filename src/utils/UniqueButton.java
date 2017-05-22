package utils;

import javax.swing.JButton;

/**
 * Egyedi gombot valósít meg, ami eltárolja a relatív pozicíóját is a játéktáblában.
 * 
 * @author lestarn
 */
public class UniqueButton {
    public int distance;
    public int i;
    public int j;
    public JButton place;

    /**
     * UniqueButton osztálykonstruktora.
     * Paraméterként kapott értékekre inicializálja az osztály adattagjait.
     * @param i - pozíció sorszáma
     * @param j - pozíció oszlopszáma
     * @param place - gomb
     */
    public UniqueButton(final int i, final int j, final JButton place) {
        setData(i, j, place);
    }

    /**
     * Beállítja a gomb pozícióját a paraméterek alapján.
     * @param i - sorszám
     * @param j - oszlopszám
     * @param place - beállítandó gomb
     */
    public final void setData(final int i, final int j, final JButton place) {
        this.i = i;
        this.j = j;
        this.place = place;
    }
    
    /**
     * Két gomb közötti távolságot számolja ki.
     * @param other - összehasonlítandó gomb
     * @return - két gomb távolsága
     */
    public double distanceFrom(UniqueButton other) {
        return Math.sqrt(Math.pow(i - other.i, 2) + Math.pow(j - other.j, 2));
    }  
    
}