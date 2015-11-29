package eu.opends.jointAttention;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny on 11/28/15.
 */
public class Tag {
    public Spatial popLinkNode;
    public Line popLink;
    public List<Spatial> circle = new ArrayList<Spatial>();

    public Tag(Spatial popLinkNode, Line popLink){
        this.popLink = popLink;
        this.popLinkNode = popLinkNode;
    }
}
