package vista3d;

import vista.Principal;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.intersection.BoundingPickResults;
import com.jme.intersection.PickResults;
import com.jme.math.Ray;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.scene.Text;

/**
 * <code>MousePick</code>
 * @author Mark Powell
 * @version
 */
public class MousePicking extends MouseInputAction {

    private Camera camera;
    private Node scene;
    private float shotTime = 0;
    private int hits = 0;
    private int shots = 0;
    private String hitItems;
    private String texto;

    public MousePicking(Camera camera, Node scene) {
        this.camera = camera;
        this.scene = scene;
    }
    /* (non-Javadoc)
     * @see com.jme.input.action.MouseInputAction#performAction(float)
     */
    public void performAction(InputActionEvent evt) {
        shotTime += evt.getTime();
        
        if( MouseInput.get().isButtonDown(1) && shotTime > 0.1f) {
            shotTime = 0;
            Ray ray = new Ray(camera.getLocation(), camera.getDirection()); // camera direction is already normalized
            PickResults results = new BoundingPickResults();
            results.setCheckDistance(true);
            scene.findPick(ray,results);


            hits += results.getNumber();
            hitItems = "";
            if(results.getNumber() > 0) {
            	texto = results.getPickData(0).getTargetMesh().getName();
            	Principal.jtaInfo.setText(texto);
                for(int i = 0; i < results.getNumber(); i++) {
                    hitItems += results.getPickData(i).getTargetMesh().getName() + " " + results.getPickData(i).getDistance();
                    if(i != results.getNumber() -1) {
                        hitItems += ", ";
                    }
                }
            }
            shots++;
            results.clear();
            System.out.println(hitItems);
        }
    }
}
