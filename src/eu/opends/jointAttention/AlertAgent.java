package eu.opends.jointAttention;

/**
 * Created by Danny on 11/26/15.
 */

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import eu.opends.main.HighlightUtils;
import eu.opends.main.Simulator;

public class AlertAgent implements Runnable{
    private Simulator sim;
    private Camera cam;
    private boolean serving;
    private Thread t;

    Vector3f gazeScreenCoord;

    public void AlertAgent(Simulator sim) {
        this.sim = sim;
        this.cam = sim.getCamera();
        this.serving = true;
        this.gazeScreenCoord = new Vector3f(0,0,0);
    }

    @Override
    public void run() {
        /*
        while (this.serving) {
            gazeScreenCoord.x = GazeCoord.x;
            gazeScreenCoord.y = GazeCoord.y;

            //replace pq with list
            //maintain a visible list
            for (DrivingAlert alert : HighlightUtils.alerts) {

                if (HighlightUtils.isVisible(cam,alert.obj)) {

                    if (alert.on==false) {    //alert is not in visibleAlertList
                        alert.tag = HighlightUtils.tags.get(HighlightUtils.visibleAlertList.size()); //have 3 tags
                        alert.tag.circle.get(Math.min(1,alert.priority)).setCullHint(Spatial.CullHint.Dynamic);
                        alert.tag.popLinkNode.setCullHint(Spatial.CullHint.Dynamic);

                        HighlightUtils.visibleAlertList.add(alert);
                        alert.on = true;
                        System.out.println("Alert for "+alert.obj.getName()+" is ON.");
                    }
                    else {
                        //Only if visible, not gazed and in the visibleAlertList we need to check gaze
                        if (alert.gazed==false) {

                            HighlightUtils.gazeRay.setOrigin(cam.getLocation());
                            HighlightUtils.gazeRay.setDirection(gazeScreenCoord.subtract(HighlightUtils.gazeRay.origin));
                            //HighlightUtils.gazeCone

                            if (alert.obj.getWorldBound().intersects(HighlightUtils.gazeRay)) {
                                alert.gazed = true;
                                alert.setNormal();
                                System.out.println("You saw it");
                            }
                            else {
                                //not gazed before, not this time
                                if (alert.unnoticedCounter>50) {
                                    //goes to the next tagStatus
                                    if (alert.urgencyUp()) {
                                        alert.tag.circle.get(Math.min(1,alert.priority+1)).setCullHint(Spatial.CullHint.Always);
                                        HighlightUtils.unnoticedCounter = 0;
                                        alert.tag.circle.get(Math.min(1,alert.priority)).setCullHint(Spatial.CullHint.Dynamic);
                                    }
                                }
                                else {
                                    HighlightUtils.unnoticedCounter++;
                                }
                            }
                        }

                    }
                }
                else { //in alerts but not visible in the screen
                    //if it was on the screen and is now moving out
                    if (alert.on) {
                        //System.out.println("Moving out of Screen");
                        //turn the highlight off
                        alert.tag.circle.get(Math.min(1,alert.priority)).setCullHint(Spatial.CullHint.Always);
                        alert.tag.popLinkNode.setCullHint(Spatial.CullHint.Always);
                        alert.tag = null;
                        alert.on = false;
                    }
                    //otherwise it must be a moving obj (unless our tigger was not made properly)

                    if (alert.objType==0) { //static obj like road sign
                        HighlightUtils.visibleAlertList.remove(alert);
                        HighlightUtils.alerts.remove(alert);
                    }
                    else{ //Moving obj like car, can be removed from visibleAlertList
                        //but stay in alerts, and add back to visibleAlertList from alerts
                        HighlightUtils.visibleAlertList.remove(alert);
                    }
                }

            }

        }
        */
    }

    public void start() {

        t = new Thread(this);
        t.start();
    }
    public void end()
    {
        t.interrupt();
    }
}
