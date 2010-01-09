package vista3d;

import java.util.HashMap;

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

import ctrl.Ctrl;

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
			float xScreen = MouseInput.get().getXAbsolute();
			// mouseAdapter is a class I use to be able to use the same classes
			// in an applet and in a desktop application, because I've found it
			// to behave differently for the two types of apps. For a desktop
			// app, I believe MouseInput.get().getXAbsolute() will work here.
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
			try {
				if (picker.getNumber() > 0) {
					System.out.println(picker.getNumber());

					String codigo = picker.getPickData(0).getTargetMesh()
							.getName().split(" ")[1];
					HashMap<String, Object> atributos = new HashMap<String, Object>();
					texto = "";
					if (picker.getPickData(0).getTargetMesh().getName().split(
							" ")[0].equals("Arista")) {
						atributos = Ctrl.getAtributosArista(Integer
								.parseInt(codigo));
						//Obtener datos del tipo de arista
						texto+= "Tipo: "+Ctrl.getTipoArista(Integer.parseInt(codigo))+"\n";
					}
					if (picker.getPickData(0).getTargetMesh().getName().split(
							" ")[0].equals("Nodo")) {
						atributos = Ctrl.getAtributosNodo(Integer
								.parseInt(codigo));
						//Obtener datos del tipo de Nodo
						texto+= "Tipo: "+Ctrl.getTipoNodo(Integer.parseInt(codigo))+"\n";
					}

					for (String atributo : atributos.keySet()) {
						texto += atributo + ": "
								+ ((String) atributos.get(atributo)) + "\n";
					}

					Principal.jtaInfo.setText(texto);

				}
			} catch (Exception e) {
			}
		}
	}

}
