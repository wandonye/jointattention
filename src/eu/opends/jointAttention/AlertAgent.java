package eu.opends.jointAttention;

/**
 * Created by Danny on 11/26/15.
 */

import com.jme3.audio.AudioSource;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import eu.opends.audio.AudioCenter;
import eu.opends.main.Simulator;

public class AlertAgent implements Runnable{
    private Simulator sim;
    private Camera cam;
    private boolean serving;
    private Thread t;

    public AlertAgent(Simulator sim) {
        this.sim = sim;
        this.cam = sim.getCamera();
        this.serving = true;
    }

    public void setCam(Camera cam) {
        if (this.cam ==null) {
            this.cam = cam;
        }
    }

    @Override
    public void run() {

        while (this.serving) {

            //maintain a visible list
            for (DrivingAlert alert : HighlightUtils.alertList) {
                if (HighlightUtils.isVisible(cam,alert.obj)) {
                    //System.out.println(alert.obj.getName());

                    if (alert.on==false) {    //alert is not in visibleAlertList
                        alert.highlight = HighlightUtils.highlights[HighlightUtils.visibleAlertList.size()]; //have 2 tags

                        HighlightUtils.visibleAlertList.add(alert);
                        alert.on = true;
                        System.out.println("Alert for "+alert.obj.getName()+" is ON.");
                    }
                    else {
                        //Only if visible, not gazed and in the visibleAlertList we need to check gaze
                        if (alert.gazed==false) {

                            //if (HighlightUtils.isGazed(alert.obj.getWorldBound(),cam.getLocation(),HighlightUtils.gazeError)) {
                            //if (HighlightUtils.hasSeen(cam.getScreenCoordinates(alert.obj.getWorldTranslation()))) {
                            if (alert.hasSeen(cam)) {
                                alert.gazedCounter += 1;
                            }
                            if (alert.gazedCounter > 5) {
                                alert.gazed = true;
                                alert.gazedCounter = 0;
                                if (alert.urgencyDown()) {
                                    alert.highlight.circle[Math.max(0, alert.priority - 1)].setCullHint(Spatial.CullHint.Always);
                                }
                                System.out.println("You saw it");
                                HighlightUtils.gazeStatus = 1;
                                HighlightUtils.gazeNodes[0].setCullHint(Spatial.CullHint.Always);
                            }
                            else {
                                //not gazed before, not this time
                                //System.out.println("You did not see it");
                                if (alert.unnoticedCounter > 50) {
                                    //System.out.println("Beep");
                                    if (HighlightUtils.soundToggler) {
                                        AudioCenter.playSound("warn");
                                    }
                                    alert.unnoticedCounter = 0;
                                } else if (alert.unnoticedCounter > 20) {
                                    //goes to the next tagStatus
                                    if (alert.urgencyUp()) {
                                        alert.highlight.circle[Math.min(1,alert.priority+1)].setCullHint(Spatial.CullHint.Always);
                                    }
                                    alert.unnoticedCounter++;
                                }
                                else {
                                    alert.unnoticedCounter++;
                                }
                            }
                        } else {   // alert.gazed==ture
                            //if (!HighlightUtils.isGazed(alert.obj.getWorldBound(),cam.getLocation(),HighlightUtils.gazeError)) {
                            //if (HighlightUtils.hasSeen(cam.getScreenCoordinates(alert.obj.getWorldTranslation()))) {
                            if (AudioCenter.getAudioNode("warn").getStatus() == AudioSource.Status.Playing) {
                                AudioCenter.stopSound("warn");
                            }
                            if (!alert.hasSeen(cam)) {
                                HighlightUtils.gazeStatus = 0;
                                HighlightUtils.gazeNodes[1].setCullHint(Spatial.CullHint.Always);
                            } else {
                                HighlightUtils.gazeStatus = 1;
                                HighlightUtils.gazeNodes[0].setCullHint(Spatial.CullHint.Always);
                            }
                        }

                    }
                }
                else { //in alerts but not visible in the screen
                    //if it was on the screen and is now moving out
                    if (alert.on) {
                        //System.out.println("Moving out of Screen");
                        //turn the highlight off
                        if (AudioCenter.getAudioNode("warn").getStatus() == AudioSource.Status.Playing) {
                            AudioCenter.stopSound("warn");
                        }

                        alert.highlight.circle[alert.priority].setCullHint(Spatial.CullHint.Always);
                        alert.highlight.popLinkNode.setCullHint(Spatial.CullHint.Always);
                        alert.reset();
                        //turn the red gaze spot off
                        HighlightUtils.gazeStatus = 0;
                        HighlightUtils.gazeNodes[1].setCullHint(Spatial.CullHint.Always);
                    }
                    //otherwise it must be a moving obj (unless our tigger was not made properly)

                    if (alert.objType==0) { //static obj like road sign
                        HighlightUtils.visibleAlertList.remove(alert);
                        HighlightUtils.alertList.remove(alert);
                    }
                    else{ //Moving obj like car, can be removed from visibleAlertList
                        //but stay in alerts, and add back to visibleAlertList from alerts
                        HighlightUtils.visibleAlertList.remove(alert);
                    }
                }

            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void start() {
        this.serving = true;
        t = new Thread(this);
        t.start();
    }
    public void end()  {
        this.serving = false;
        t.interrupt();
    }
}
