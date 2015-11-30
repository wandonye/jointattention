package eu.opends.jointAttention;

import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;

/**
 * Created by Danny on 11/28/15.
 */
public class Highlight {
    public Spatial popLinkNode;
    public Line popLink;
    public Spatial [] circle = new Spatial[HighlightUtils.numOfStatus];

    public Highlight(Spatial popLinkNode, Line popLink){
        this.popLink = popLink;
        this.popLinkNode = popLinkNode;
    }
}
