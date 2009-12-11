package vista3d;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.input.InputHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Spatial.NormalsMode;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.TextureState;
import com.jme.system.canvas.SimpleCanvasImpl;
import com.jme.util.TextureManager;

import ctrl.Ctrl;

public class Espacio3D extends SimpleCanvasImpl {

	private Quaternion rotQuat;
	private float angle = 0;
	private Vector3f axis;
	private Box box;
	long startTime = 0;
	long fps = 0;
	private InputHandler input;

	public Espacio3D(int width, int height) {
		super(width, height);
	}

	// Dibuja un grafo
	public void dibujarGrafo() {

		rootNode.detachAllChildren();
		
		
		// Se crea Esfera en el centro para comprobar eje centro.
		Sphere esfera = new Sphere("Esfera", new Vector3f(0, 0, 0), 15, 15,
				1.5f);
		esfera.setModelBound(new BoundingBox());
		esfera.updateModelBound();
		rootNode.attachChild(esfera);
		// ///////////////////////////////////////////////////////////

		int[] codigosNodos = Ctrl.getCodigosNodos();

		// Dimensiones Caja
		Vector3f max = new Vector3f(1, 1, 1);
		Vector3f min = new Vector3f(-1, -1, -1);
		int z = 0;

		// Cantidad de nodos en el eje X
		int cantidadX = (int) Math.ceil(Math.sqrt(codigosNodos.length));
		// Distancia entre nodos
		float distanciaNodos = 2.0f;

		// Dimension Ancho de la Caja
		float anchoNodo = max.getX() - min.getX();

		// Distancia eje X de toda la matriz (Eje Horizontal)
		float perimetroX = (anchoNodo * cantidadX)
				+ (distanciaNodos * (cantidadX - 1));
		float mitadPerimetroX = perimetroX / 2;

		float location_x = -mitadPerimetroX + (anchoNodo / 2); // Debido a que
																// es un Box por
																// eso se hace
																// la suma.
		float location_y = mitadPerimetroX + (anchoNodo / 2);
		
		for (int codigoNodo : codigosNodos) {
			Box caja = new Box("" + codigoNodo, min, max);
			caja.setModelBound(new BoundingBox());
			caja.updateModelBound();

			caja.setLocalTranslation(new Vector3f(location_x, location_y, 0));
			caja.setRenderQueueMode(Renderer.QUEUE_SKIP);
			rootNode.attachChild(caja);
			caja.setRandomColors();
			location_x += (anchoNodo + distanciaNodos);
			if (location_x >= mitadPerimetroX + (anchoNodo / 2)) {
				location_y -= (anchoNodo + distanciaNodos);
				
				location_x = -mitadPerimetroX + (anchoNodo / 2);
			}
			caja.updateGeometricState(0, true);
			caja.updateRenderState();
		}
	}

	public void simpleSetup() {

		// Normal Scene setup stuff...
		rotQuat = new Quaternion();
		axis = new Vector3f(1, 1, 0.5f);
		axis.normalizeLocal();

		Vector3f max = new Vector3f(5, 5, 5);
		Vector3f min = new Vector3f(-5, -5, -5);

		box = new Box("Box", min, max);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		box.setLocalTranslation(new Vector3f(0, 0, -10));
		box.setRenderQueueMode(Renderer.QUEUE_SKIP);
		rootNode.attachChild(box);

		box.setRandomColors();

		TextureState ts = renderer.createTextureState();
		ts.setEnabled(true);
		ts.setTexture(TextureManager.loadTexture(Grafo3D.class.getClassLoader()
				.getResource("jmetest/data/images/Monkey.jpg"),
				Texture.MinificationFilter.BilinearNearestMipMap,
				Texture.MagnificationFilter.Bilinear));

		rootNode.setRenderState(ts);
		startTime = System.currentTimeMillis() + 5000;

		input = new InputHandler();
		input.addAction(new InputAction() {
			public void performAction(InputActionEvent evt) {
				// logger.info(evt.getTriggerName());
			}
		}, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_ALL,
				InputHandler.AXIS_NONE, false);

		input.addAction(new InputAction() {
			public void performAction(InputActionEvent evt) {
				// logger.info(evt.getTriggerName());
			}
		}, InputHandler.DEVICE_KEYBOARD, InputHandler.BUTTON_ALL,
				InputHandler.AXIS_NONE, false);
	}

	public void simpleUpdate() {
		input.update(tpf);

		// Code for rotating the box... no surprises here.
//		if (tpf < 1) {
//			angle = angle + (tpf * 25);
//			if (angle > 360) {
//				angle = 0;
//			}
//		}
//		rotQuat.fromAngleNormalAxis(angle * FastMath.DEG_TO_RAD, axis);
//		box.setLocalRotation(rotQuat);
//
//		if (startTime > System.currentTimeMillis()) {
//			fps++;
//		} else {
//			long timeUsed = 5000 + (startTime - System.currentTimeMillis());
//			startTime = System.currentTimeMillis() + 5000;
//			// logger.info(fps + " frames in " + (timeUsed / 1000f) +
//			// " seconds = " + (fps / (timeUsed / 1000f)) + " FPS (average)");
//			fps = 0;
//		}
	}

}
