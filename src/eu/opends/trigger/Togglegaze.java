package eu.opends.trigger;

import eu.opends.basics.SimulationBasics;
import eu.opends.jointAttention.DrivingAlert;
import eu.opends.jointAttention.HighlightUtils;

public class ToggleGaze extends TriggerAction {

    public ToggleGaze(float delay, int maxRepeat)
    {
        super(delay, maxRepeat);
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        if (HighlightUtils.gazeToggler) {
            HighlightUtils.gazeToggler = false;
        }
        else {
            HighlightUtils.gazeToggler = true;
        }
    }

}