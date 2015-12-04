package eu.opends.trigger;

import eu.opends.jointAttention.HighlightUtils;

public class ToggleSoundAlert extends TriggerAction {

    public ToggleSoundAlert(float delay, int maxRepeat) {
        super(delay, maxRepeat);
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        HighlightUtils.soundToggler = !HighlightUtils.soundToggler;
    }

}