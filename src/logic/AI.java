package logic;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import utils.UniqueButton;

/**
 *
 * @author lestarn
 */
public class AI extends Logic {
    
    @Override
    public int pressButton(String title, JButton button, final int i, final int j) {
        boolean steppedAway = genericStep(button, i, j);
        if (steppedAway) {
            stepCount++;
            
            if (stepLeft() == 0) {
                return 0;
            }
            
            switchButtons(false);
            List<UniqueButton> validButtons = gatherValidButtons();
            
            double minDistance = 0;
            UniqueButton placeToStep = null;
            if (validButtons.size() >= 1)
                placeToStep = validButtons.get(0);
            
            for (UniqueButton newPlace : validButtons) {
                double distance = 0;
                for (UniqueButton Hunter : Hunters) {
                    distance += newPlace.distanceFrom(Hunter);
                }
                if (distance < minDistance) {
                    minDistance = distance;
                    placeToStep = newPlace;
                }
            }
            
            if (null != placeToStep) {
                Prey.place.setText("");
                Prey = placeToStep;
                placeToStep.place.setText(PREY);
                switchButtons(true);
            } else {
                return 1;
            }
        }
        return -1;
    }
    
    @Override
    public boolean isItMe(final JButton button)
    { return button.getText().equals(me); }
    
    @Override
    public void setMe(final JButton button)
    { button.setText(me); }
}
