/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JButton;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import utils.UniqueButton;

/**
 *
 * @author suszt
 */
public class TestLogic {
    private ByteArrayOutputStream outContent;
    
    @Before public void initialize() {
        outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
    }
    
    @Test
    public void testGenericStepSelectedNull() {
        JButton button = new JButton();
        Logic logic = new LogicImpl();
        assertFalse("A genericStep() engedi a lépést rossz esetben.",
                logic.genericStep(button,10,10));
    }
    
    @Test
    public void testGenericStepSelectedNotNull() {
        JButton button = new JButton();
        Logic logic = new LogicImpl();
        logic.selected = new UniqueButton(1,1,button);
        button.setText("");
        JButton button2 = new JButton();
        
        assertFalse("A genericStep() nem engedi a lépést jó esetben.",
                logic.genericStep(button, 2, 1));
        
        logic.selected = new UniqueButton(1,1,button2);
        assertTrue("A genericStep() nem engedi a lépést jó esetben.",
                logic.genericStep(button, 2, 1));
    }
    
    @Test
    public void testIsNextTo() {
        Logic logic = new LogicImpl();
        
        JButton button = new JButton();
        UniqueButton selected = new UniqueButton(1,1,button);
        
        assertTrue("Az isNextTo() függvény hamis értéket ad vissza megfelelő pozíciók esetén.",
                logic.isNextTo(2,1,selected));
        assertTrue("Az isNextTo() függvény hamis értéket ad vissza megfelelő pozíciók esetén.",
                logic.isNextTo(0, 1, selected));
        assertTrue("Az isNextTo() függvény hamis értéket ad vissza megfelelő pozíciók esetén.",
                logic.isNextTo(1, 0, selected));
        assertTrue("Az isNextTo() függvény hamis értéket ad vissza megfelelő pozíciók esetén.",
                logic.isNextTo(1,2,selected));
        assertFalse("Az isNextTo() függvény igaz értéket ad vissza rossz pozíciók esetén.",
                logic.isNextTo(2,0,selected));
        assertFalse("Az isNextTo() függvény igaz értéket ad vissza rossz pozíciók esetén.",
                logic.isNextTo(0,0,selected));
        assertFalse("Az isNextTo() függvény igaz értéket ad vissza rossz pozíciók esetén.",
                logic.isNextTo(0,2,selected));
        assertFalse("Az isNextTo() függvény igaz értéket ad vissza rossz pozíciók esetén.",
                logic.isNextTo(2,2,selected));
    }
    
    
    @Test
    public void testIsValid() {
        JButton[][] testGameTable = new JButton[10][10];
        Logic logic = new LogicImpl();
        logic.gameTable = testGameTable;
        assertTrue("Az isValid() hamisat ad vissza megfelelő pozíciók esetén.",logic.isValid(8,7));
        assertFalse("Az isValid() igazat ad vissza rossz pozíciók esetén.",logic.isValid(11, 12));
    }
    
    public class LogicImpl extends Logic {
        public int pressButton(JButton button, int i, int j) {
            return 0;
        }

        public boolean isItMe(JButton button) {
            return false;
        }

        public void setMe(JButton button) {
        }
    }
}
