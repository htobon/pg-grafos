package vista3d;

import java.awt.event.KeyListener;
import java.nio.FloatBuffer;

import org.eclipse.swt.widgets.Display;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.InputSystem;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.intersection.BoundingCollisionResults;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
import com.jme.scene.Text;
import com.jme.scene.Spatial.NormalsMode;
import com.jme.scene.Spatial.TextureCombineMode;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.SimpleCanvasImpl;
import com.jme.util.TextureManager;
import com.jme.util.geom.BufferUtils;

import ctrl.Ctrl;

public class Espacio3D extends SimpleCanvasImpl {

	private Quaternion rotQuat;
	private float angle = 0;
	private Vector3f axis;
	private Box box;
	long startTime = 0;
	long fps = 0;
	private FirstPersonHandler input;

	public Espacio3D(int width, int height) {
		super(width, height);

	}

	// Dibuja un grafo
	public void dibujarGrafo() {

		rootNode.detachAllChildren();
		rootNode.setRenderState(null);

		// Se crea Esfera en el centro para comprobar eje centro.
		// Sphere esfera = new Sphere("Esfera", new Vector3f(0, 0, 0), 15, 15,
		// 1.5f);
		// esfera.setModelBound(new BoundingBox());
		// esfera.updateModelBound();
		// rootNode.attachChild(esfera);
		// ///////////////////////////////////////////////////////////

		dibujarCruz();

		int[] codigosNodos = Ctrl.getCodigosNodos();
		dibujarNodos(codigosNodos);

		int[] codigosAristas = Ctrl.getCodigosAristas();
		dibujarAristas(codigosAristas);

	}

	private void dibujarCruz() {
		Text cruz = new Text("cruz", "+");
		// Text cross = Text.createDefaultTextLabel("Cross hairs", "+");
		cruz.setCullHint(Spatial.CullHint.Never);
		// cruz.setTextureCombineMode(TextureCombineMode.Replace);
		cruz.setLocalTranslation(new Vector3f(DisplaySystem.getDisplaySystem()
				.getWidth() / 2f - 8f, // 8 is half the width
				// of a font char
				DisplaySystem.getDisplaySystem().getHeight() / 2f - 8f, 0));
		rootNode.attachChild(cruz);

	}

	private void dibujarAristas(int[] codigosAristas) {

		for (int codigoArista : codigosAristas) {

			int[] verticesNodos = Ctrl.getNodosDeArista(codigoArista);
			Vector3f[] vertices = new Vector3f[2];

			vertices[0] = ((Sphere) rootNode.getChild("Nodo "
					+ verticesNodos[0])).getLocalTranslation();

			vertices[1] = ((Sphere) rootNode.getChild("Nodo "
					+ verticesNodos[1])).getLocalTranslation();

			Line arista = new Line("Arista " + codigoArista, vertices, null,
					null, null);

			arista.setLineWidth(4f);

			arista.setModelBound(new BoundingBox());
			arista.updateModelBound();
			arista.setRenderQueueMode(Renderer.QUEUE_SKIP);
			rootNode.attachChild(arista);
			arista.setSolidColor(Ctrl.getColorArista(codigoArista));
			arista.updateGeometricState(0, true);
			arista.updateRenderState();

			System.out.println("Arista " + codigoArista + " - Colisiones: "
					+ encontrarColisiones(arista, codigoArista));
		}

	}

	/**
	 * @param codigosNodos
	 */
	private void dibujarNodos(int[] codigosNodos) {
		// Dimensiones Caja
		Vector3f max = new Vector3f(1, 1, 1);
		Vector3f min = new Vector3f(-1, -1, -1);
		int z = 0;

		// Cantidad de nodos en el eje X
		int cantidadX = (int) Math.ceil(Math.sqrt(codigosNodos.length));
		// Distancia entre nodos
		float distanciaNodos = 4.0f;

		// Dimension Ancho de la Caja
		float anchoNodo = max.getX() - min.getX();

		// Distancia eje X de toda la matriz (Eje Horizontal)
		float perimetroX = (anchoNodo * cantidadX)
				+ (distanciaNodos * (cantidadX - 1));
		float mitadPerimetroX = perimetroX / 2;

		float location_x = -mitadPerimetroX + (anchoNodo / 2);

		// float location_y = mitadPerimetroX + (anchoNodo / 2);
		float location_y = mitadPerimetroX;

		for (int codigoNodo : codigosNodos) {
			Sphere nodo = new Sphere("Nodo " + codigoNodo, 10, 10, 1f);
			BoundingBox bounding = new BoundingBox();
			nodo.setModelBound(bounding);
			nodo.updateModelBound();

			nodo.setLocalTranslation(new Vector3f(location_x, location_y, 0));
			nodo.setRenderQueueMode(Renderer.QUEUE_SKIP);
			rootNode.attachChild(nodo);

			// System.out.println(Ctrl.getColorNodo(codigoNodo));
			// nodo.setSolidColor(Ctrl.getColorNodo(codigoNodo));

			// ///ASIGNAR COLOR//////

			// nodo.setDefaultColor(Ctrl.getColorNodo(codigoNodo));
			nodo.setSolidColor(Ctrl.getColorNodo(codigoNodo));
			// MaterialState material =
			// DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
			// material.setEnabled(true);
			// material.setDiffuse(Ctrl.getColorNodo(codigoNodo));
			// nodo.setRenderState(material);
			// //////////////////////

			location_x += (anchoNodo + distanciaNodos);
			if (location_x >= mitadPerimetroX + (anchoNodo / 2)) {
				location_y -= (anchoNodo + distanciaNodos);

				location_x = -mitadPerimetroX + (anchoNodo / 2);
			}
			nodo.updateGeometricState(0, true);
			nodo.updateRenderState();
		}
	}

	public int encontrarColisiones(Line arista, int codigoArista) {
		// int cantidad = 0;
		int[] codigosNodos = Ctrl.getNodosDeArista(codigoArista);
		Sphere nodoOrigen = (Sphere) rootNode.getChild("Nodo "
				+ codigosNodos[0]);
		Sphere nodoDestino = (Sphere) rootNode.getChild("Nodo "
				+ codigosNodos[1]);

		Ray rayo = new Ray(nodoOrigen.getLocalTranslation(), nodoDestino
				.getLocalTranslation().clone().subtractLocal(nodoOrigen.getLocalTranslation()));
		TrianglePickResults picker = new TrianglePickResults();
		picker.setCheckDistance(true);
		rootNode.findPick(rayo, picker);
		for (int c = 0; c < picker.getNumber(); c++) {
			if (picker.getPickData(c).getTargetMesh() instanceof Sphere) {
				System.out.println(arista.getName() + " - Colisiona con Nodo: "
						+ picker.getPickData(c).getTargetMesh().getName());
			}
		}

		return picker.getNumber();
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

		// TextureState ts = renderer.createTextureState();
		// ts.setEnabled(true);
		// ts.setTexture(TextureManager.loadTexture(Grafo3D.class.getClassLoader()
		// .getResource("jmetest/data/images/Monkey.jpg"),
		// Texture.MinificationFilter.BilinearNearestMipMap,
		// Texture.MagnificationFilter.Bilinear));
		//
		// rootNode.setRenderState(ts);
		startTime = System.currentTimeMillis() + 5000;
		// input = new InputHandler();
		// input = new FirstPersonHandler(this.getCamera());

		FirstPersonHandler firstPersonHandler = new FirstPersonHandler(cam,
				10f, 1f);
		input = firstPersonHandler;

		input.addAction(new InputAction() {
			public void performAction(InputActionEvent evt) {
				((FirstPersonHandler) input).getMouseLookHandler().setEnabled(
						evt.getTriggerPressed());
				input.getMouseLookHandler().getMouseLook().setSpeed(0.5f);
				input.getMouseLookHandler().getMouseLook()
						.setButtonPressRequired(true);

			}
		}, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_ALL,
				InputHandler.AXIS_NONE, false);

		input.addAction(new InputAction() {
			public void performAction(InputActionEvent evt) {
				// logger.info(evt.getTriggerName());
			}
		}, InputHandler.DEVICE_KEYBOARD, InputHandler.BUTTON_ALL,
				InputHandler.AXIS_NONE, false);

		input.update(10.5f);
		((FirstPersonHandler) input).getMouseLookHandler().setEnabled(true);

		MousePicking pick = new MousePicking(cam, rootNode);
		input.addAction(pick);

	}

	private void actualizarAristas() {
		for (int codigoArista : Ctrl.getCodigosAristas()) {
			int[] verticesNodos = Ctrl.getNodosDeArista(codigoArista);
			Vector3f[] vertices = new Vector3f[2];

			vertices[0] = ((Sphere) rootNode.getChild("Nodo "
					+ verticesNodos[0])).getLocalTranslation();

			vertices[1] = ((Sphere) rootNode.getChild("Nodo "
					+ verticesNodos[1])).getLocalTranslation();
			Line arista = (Line) rootNode.getChild("Arista " + codigoArista);

			arista.reconstruct(BufferUtils.createFloatBuffer(vertices), null,
					arista.getColorBuffer(), null);

		}
	}

	public void simpleUpdate() {
		input.update(tpf);

		// Ejemplo..... MOVIENDO NODO
		// if (rootNode.getChild("Nodo 300") != null) {
		//
		// Sphere nodo = (Sphere) rootNode.getChild("Nodo 300");
		// nodo.setLocalTranslation(nodo.getLocalTranslation().x + 0.1f, nodo
		// .getLocalTranslation().y, nodo.getLocalTranslation().z);
		// System.out.println(nodo.getLocalTranslation().x);
		//
		// actualizarAristas();
		// }

		// PARA DESAPARECER EL PUNTERO DEL MOUSE: (Todavía no se donde ponerlo
		// ya que no funciona bien)
		// if (MouseInput.get().isButtonDown(0)) {
		//
		// MouseInput.get().setCursorVisible(true);
		// } else {
		// MouseInput.get().setCursorVisible(false);
		// }

		// Code for rotating the box... no surprises here.
		// if (tpf < 1) {
		// angle = angle + (tpf * 25);
		// if (angle > 360) {
		// angle = 0;
		// }
		// }
		// rotQuat.fromAngleNormalAxis(angle * FastMath.DEG_TO_RAD, axis);
		// box.setLocalRotation(rotQuat);
		//
		// if (startTime > System.currentTimeMillis()) {
		// fps++;
		// } else {
		// long timeUsed = 5000 + (startTime - System.currentTimeMillis());
		// startTime = System.currentTimeMillis() + 5000;
		// // logger.info(fps + " frames in " + (timeUsed / 1000f) +
		// // " seconds = " + (fps / (timeUsed / 1000f)) + " FPS (average)");
		// fps = 0;
		// }
	}

	public void cambiarColorNodo(String nombreNodo, ColorRGBA color) {
		((Sphere) rootNode.getChild(nombreNodo)).setSolidColor(color);
		((Sphere) rootNode.getChild(nombreNodo)).updateRenderState();
	}

	public void cambiarColorArista(String nombreArista, ColorRGBA color) {
		((Line) rootNode.getChild(nombreArista)).setSolidColor(color);
		((Line) rootNode.getChild(nombreArista)).updateRenderState();
	}

	public void resetCam() {
		Vector3f loc = new Vector3f(0.0f, 0.0f, 25.0f);
		Vector3f left = new Vector3f(-1.0f, 0.0f, 0.0f);
		Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
		Vector3f dir = new Vector3f(0.0f, 0f, -1.0f);

		cam.setFrame(loc, left, up, dir);

		cam.update();
	}

}
