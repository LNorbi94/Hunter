package hunter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Yoshitoki
 */
public class TestHunter {
    
    private ByteArrayOutputStream outContent;
    
    @Before public void initialize() {
        outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
    }
    
    @Test
    public void testEmpty() {
        String[] args = new String[0];
        Hunter.main(args);
        String[] lines = outContent.toString().split("\n");
        boolean isGameNumberCorrect = lines[0].contains("4");
        boolean isIncrementCorrect = lines[1].contains("2");
        assertTrue("Hibás játékindítás."
                , isGameNumberCorrect && isIncrementCorrect);
    }
    
    @Test
    public void testNegative() {
        String[] args = new String[2];
        args[0] = "-1";
        args[1] = "-1";
        Hunter.main(args);
        String[] lines = outContent.toString().split("\n");
        boolean isGameNumberCorrect = lines[0].contains("4");
        boolean isIncrementCorrect = lines[1].contains("2");
        assertTrue("Hibás játékindítás."
                , isGameNumberCorrect && isIncrementCorrect);
    }
    
    @Test
    public void testLowerBound() {
        String[] args = new String[2];
        args[0] = "1";
        args[1] = "1";
        Hunter.main(args);
        String[] lines = outContent.toString().split("\n");
        boolean isGameNumberCorrect = lines[0].contains("1");
        boolean isIncrementCorrect = lines[1].contains("1");
        assertTrue("Hibás játékindítás."
                , isGameNumberCorrect && isIncrementCorrect);
    }
    
    @Test
    public void testUpperBound() {
        String[] args = new String[2];
        args[0] = "5";
        args[1] = "4";
        Hunter.main(args);
        String[] lines = outContent.toString().split("\n");
        boolean isGameNumberCorrect = lines[0].contains("5");
        boolean isIncrementCorrect = lines[1].contains("4");
        assertTrue("Hibás játékindítás."
                , isGameNumberCorrect && isIncrementCorrect);
    }
    
    @Test
    public void testMax() {
        String[] args = new String[2];
        args[0] = "6";
        args[1] = "6";
        Hunter.main(args);
        String[] lines = outContent.toString().split("\n");
        boolean isGameNumberCorrect = lines[0].contains("4");
        boolean isIncrementCorrect = lines[1].contains("2");
        assertTrue("Hibás játékindítás."
                , isGameNumberCorrect && isIncrementCorrect);
    }
    
    @Test
    public void testBadGameNum() {
        String[] args = new String[2];
        args[0] = "almafa";
        args[1] = "nemfa";
        Hunter.main(args);
        String[] lines = outContent.toString().split("\n");
        boolean isGameNumberCorrect = lines[0].contains("4");
        boolean isIncrementCorrect = lines[1].contains("2");
        assertTrue("Hibás játékindítás."
                , isGameNumberCorrect && isIncrementCorrect);
    }

}
