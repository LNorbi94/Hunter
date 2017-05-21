package logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import utils.UniqueButton;

/**
 *
 * A játék logikáját adó absztrakt osztály.
 * Ezen osztály megvalósítása a mesterséges intelligenciát végző osztály, 
 * illetve online játék esetén a kliens és szerver osztályok.
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
    
    protected int stepCount;
    
    /**
     * Beállítja a játékhoz a kezdeti táblát. 
     * Beállítja a "me" adattagot a Hunterre. 
     * @param gameTable - beállítandó tábla, gombok 2 dimenziós tömbje
     */
    public void setButtons(final JButton[][] gameTable) {
        this.gameTable = gameTable;
        me = Logic.HUNTER;
    }
    
    /**
     * Hozzáad egy új támadót a támadók listájához,
     * illetve beállítja a pozícióját az i-edik sorba és a j-edik oszlopba.
     *  
     * @param i - a tábla i-edik sora
     * @param j - a tábla j-edig oszlopa
     * @param place - a gameTable[i][j]-edig gombja, ahová a támadó lépett
     */
    public void addHunter(final int i, final int j, final JButton place) {
        if (null == Hunters) {
            Hunters = new ArrayList<>();
        }
        UniqueButton g = new UniqueButton(i, j, place);
        Hunters.add(g);
    }
    
    /**
     * Megváltozatja a támadó pozícióját.
     * @param i - új lépésének i-edik sora
     * @param j - új lépésének j-edig oszlopa
     * @param place - a gameTable[i][j] -edik gombja
     * @param from - menekülő régi pozícióját tároló gomb
     */
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
    
    /**
     * Megváltoztatja a menekülő pozícióját.
     * @param i - új lépésének i-edik sora
     * @param j - új lépésének j-edig oszlopa
     * @param place - a gameTable[i][j] -edik gombja
     */
    public void setPrey(final int i, final int j, final JButton place) {
        if (null == Prey) {
            Prey = new UniqueButton(i, j, place);
        } else {
            Prey.setData(i, j, place);
        }
    }
    
    /**
     * Absztrakt metódus, mely a genericStep metódus alapján ellépteti a másik játékost.
     * Offline játék esetén a mesterséges intelligencia játékosát.
     * Online játék esetén az ellenfelet.
     * Majd ellenőrzi, hogy vége van-e a játéknak.
     * @param button - a kattintott gomb 
     * @param i - új pozíció sorszáma
     * @param j - új pozíció oszlopszáma
     * @return  - -1, ha még nem nyert senki
     * 0, ha vesztett a játékos
     * 1, ha nyert a játékos
     */
    public abstract int pressButton(String title, JButton button
            , final int i, final int j);
    
    /**
     * Absztrakt metódus, mely megvizsgálja, hogy az éppen kiválasztott gomb
     * menekülő, vagy támadó.
     * 
     * @param button - kiválasztott gomb
     * @return - igaz érték, ha menekülő esetén a kiválasztott gomb is menekülő,
     * illetve, ha támadó esetén a kiválasztott gomb is támadó
     */    
    public abstract boolean isItMe(final JButton button);
    
    /**
     * Beállítja a kiválszott gomb címkéjét a származtatott osztály szerint
     * menekülőre vagy támadóra
     * @param button - kijelölt gomb a táblán
     */
    public abstract void setMe(final JButton button);
    
    /**
     * Elvégzi a lépés feltételeinek ellenőrzéseit, majd lép egyet a kiválasztott gombbal, ha tud.
     * Ha épp nincsen gomb kijelölve, és a paraméterként megadott gomb megegyezik saját magával, mint menekülő vagy támadó,
     * akkor kijelöli a gombot.
     * Ha van már kijelölve gomb, akkor ellenőrzi, hogy tud-e oda lépni a játékos. 
     * Ha tud, akkor odalép, és igazzal tér vissza, különben hamissal.
     * @param button - a kattintott gomb
     * @param i - új lépés sorának száma
     * @param j - új lépés oszlopának száma
     * @return - igaz, ha a játékos tudott lépni
     * hamis, ha nem tudott lépni, mert valamelyik feltétel nem teljeseült
     */
    public boolean genericStep(final JButton button, final int i, final int j) {
        if (selected == null) {
            if (isItMe(button)) {
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
            setMe(button);
            return steppedAway;
        }
        return false;
    }

    /**
     * Egy lépés megtételéhez szükséges feltételt vizsgáló metódus.
     * A játékos kiválasztott gombra léphet, amennyiben az vagy vele egy oszlopban,
     * vagy vele egy sorban található, közvetlenül mellette. Az átlós lépés nem megengedett.
     * 
     * @param i - új lépés i-edik sora
     * @param j - új lépés j-edig oszlopa
     * @param selected - kiválasztott gomb, ahonnan el szeretnénk lépni
     * @return - igaz, ha megfelel a feltételnek, azaz mellette található  
     */
    protected boolean isNextTo(int i, int j, UniqueButton selected) {
        return  (Math.abs(selected.i - i) <= 1 && Math.abs(selected.j - j) == 0) ||
                (Math.abs(selected.i - i) == 0 && Math.abs(selected.j - j) <= 1) ;
    }
    
    /**
     * Megvizsgálja, hogy a megadott pozíció a tábla méretének megfelele-e. 
     * @param i - megadott sorszám
     * @param j - megadott oszlopszám
     * @return  - igaz, amennyiben a sor- és oszlopszám 0 és a tábla mérete közé esik
     */
    protected boolean isValid(int i, int j) {
        final int gameSize = gameTable.length;
        return i >= 0 && j >= 0 && i < gameSize && j < gameSize;
    }
    
    /**
     * A tábla gombjait a megadott állapotba kapcsolja. 
     * Hamis érték esetén a játékos nem tud gombot kijelölni és lépni.
     * @param state - bekapcsolandó állapot
     */
    protected void switchButtons(boolean state) {
        for (JButton[] buttons : gameTable) {
            for (JButton button : buttons) {
                button.setEnabled(state);
            }
        }
    }
    
    /**
     * Lejátszott lépések számának lekérdezése.
     * @return - lépések száma
     */
    protected int getStepCount() {
        return stepCount;
    }
    
    /**
     * Még hátralévő lépések számának lekérdezése.
     * @return - hátralévő lépések száma
     */
    protected int stepLeft() {
        return gameTable.length * 4 - stepCount;
    }
    
    /**
     * Összegyűjti azokat a gombokat ahová a játékos éppen léphet.
     * Megvizsgálja, melyek azok a gombok,amelyek a táblán belül a játékos mellett vannak,
     * majd beleteszi egy listába.
     * @return - lehetséges lépések gombjainak listája
     */
    public List<UniqueButton> gatherValidButtons() {
        List<UniqueButton> validButtons = new ArrayList<>();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                int x = Prey.i + i;
                int y = Prey.j + j;
                if (isValid(x, y)
                        && isNextTo(x, y, Prey)) {
                    boolean empty 
                            = gameTable[x][y].getText().isEmpty();
                    if (empty)
                        validButtons.add(
                                new UniqueButton(x, y, gameTable[x][y]));
                }
            }
        }
        return validButtons;
    }
    
}