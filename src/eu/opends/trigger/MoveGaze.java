package eu.opends.trigger;

import eu.opends.basics.SimulationBasics;
import eu.opends.jointAttention.DrivingAlert;
import eu.opends.jointAttention.GazeCoord;
import eu.opends.jointAttention.HighlightUtils;

public class MoveGaze extends TriggerAction {
    float x = 0;
    float y = 0;

    public MoveGaze(float delay, int maxRepeat, float x, float y) {
        super(delay, maxRepeat);
        this.x = x;
        this.y = y;
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        GazeCoord.x = Math.min(HighlightUtils.screenSizeX, Math.max(0, GazeCoord.x + this.x));
        GazeCoord.y = Math.min(HighlightUtils.screenSizeY, Math.max(0, GazeCoord.y + this.y));
    }

}