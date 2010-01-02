package vista3d;

import vista.Principal;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.intersection.BoundingPickResults;
import com.jme.intersection.PickResults;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

/**
 * <code>MousePick</code>
 * 
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
	private final Ray ray = new Ray();
	private TrianglePickResults picker;

	public MousePicking(Camera camera, Node scene) {
		this.camera = camera;
		this.scene = scene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jme.input.action.MouseInputAction#performAction(float)
	 */
	public void performAction(InputActionEvent evt) {
		shotTime += evt.getTime();

		if (MouseInput.get().isButtonDown(1) && shotTime > 0.1f) {
			shotTime = 0;
			float xScreen = MouseInput.get().getXAbsolute(); // mouseAdapter is
			// a class I use
			// to be able to
			// use the same
			// classes in an
			// applet and in
			// a desktop
			// application,
			// because I've
			// found it to
			// behave
			// differently
			// for the two
			// types of
			// apps. For a
			// desktop app,
			// I believe
			// MouseInput.get().getXAbsolute()
			// will work
			// here.
			float yScreen = MouseInput.get().getYAbsolute();

			Vector2f screenPos = new Vector2f(xScreen, yScreen);
			Vector3f worldCoordsStart = DisplaySystem.getDisplaySystem()
					.getWorldCoordinates(screenPos, 0);
			Vector3f worldCoordsEnd = DisplaySystem.getDisplaySystem()
					.getWorldCoordinates(screenPos, 1);

			Vector3f startPoint = worldCoordsStart;
			Vector3f endPoint = worldCoordsEnd.subtractLocal(worldCoordsStart);

			Ray rayLine = new Ray(startPoint, camera.getDirection());
			

			picker = new TrianglePickResults();
			picker.setCheckDistance(true);
			scene.findPick(rayLine, picker); // mouseNode is the node with thes
			// geometry to pick. For
			// example, it can be the root
			// node
			System.out.println("cualquier cosa");
			if (picker.getNumber() > 0) {
				System.out.println(picker.getNumber());
				texto = "";
				for (int n = 0; n < picker.getNumber(); n++) {
					texto += picker.getPickData(n).getTargetMesh().getName()
							+ "\n";
				}
				Principal.jtaInfo.setText(texto);

			}
		}

		// shotTime = 0;
		// ray.setOrigin(camera.getLocation());
		// ray.setDirection(camera.getDirection());
		// // camera direction is already normalized
		// ray.getDirection().normalizeLocal();
		//			
		// PickResults results = new BoundingPickResults();
		// results.setCheckDistance(true);
		// scene.findPick(ray, results);
		//
		// hits += results.getNumber();
		// hitItems = "";
		// if (results.getNumber() > 0) {
		// texto = results.getPickData(0).getTargetMesh().getName();
		// Principal.jtaInfo.setText(texto);
		// for (int i = 0; i < results.getNumber(); i++) {
		// hitItems += results.getPickData(i).getTargetMesh()
		// .getName()
		// + " " + results.getPickData(i).getDistance();
		// if (i != results.getNumber() - 1) {
		// hitItems += ", ";
		// }
		// }
		// }
		// shots++;
		// results.clear();
		// System.out.println(hitItems);
	}

}
