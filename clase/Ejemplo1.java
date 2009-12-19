package clase;

import jmetest.flagrushtut.*;

import com.jme.app.BaseGame;
import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingVolume;
import com.jme.input.FirstPersonHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;
import com.jme.util.Timer;

public class Ejemplo1 extends BaseGame {

	int width = 0, height = 0, depth = 0, freq = 0;
	boolean isFullscreen = false;
	Camera cam = null;
	Timer timer = null;
	Node rooNode = null;
	Sphere esfera = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ejemplo1 app = new Ejemplo1();
		app.setConfigShowMode(ConfigShowMode.AlwaysShow);
		app.start();

	}

	@Override
	protected void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initGame() {
		rooNode = new Node("root");
		cam.update();

		esfera = new Sphere("esfera", 30, 30, 25);
		esfera.setLocalTranslation(new Vector3f(0, 0, -40));
		esfera.setModelBound(new BoundingBox());
		esfera.updateModelBound();

		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);
		ts.setTexture(TextureManager.loadTexture(Lesson2.class.getClassLoader()
				.getResource("jmetest/data/images/Monkey.jpg")));
		esfera.setRenderState(ts);
		
		rooNode.attachChild(esfera);
		rooNode.updateGeometricState(0, true);
		rooNode.updateRenderState();
	}

	@Override
	protected void initSystem() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		width = settings.getWidth();
		height = settings.getHeight();
		depth = settings.getDepth();
		freq = settings.getFrequency();

		isFullscreen = settings.isFullscreen();
		try {
			display = DisplaySystem.getDisplaySystem(settings.getRenderer());
			display.createWindow(width, height, depth, freq, isFullscreen);
			cam = display.getRenderer().createCamera(width, height);
		} catch (JmeException e) {
			System.out.println("No se pudo crear el display o la ventana.");
			e.printStackTrace();
			System.exit(1);
		}

		display.getRenderer().setBackgroundColor(ColorRGBA.randomColor());
		cam.setFrustumPerspective(45.0f, (float) width / height, 1, 1000);

		// ubicación de la cámara en el espacio (X, Y, Z).
		Vector3f tmpLoc = new Vector3f(0f, 0f, 25f);

		// Borde superior izquierdo de la cámara y contra qué vectores están
		// alineadas.
		Vector3f tmpLeft = new Vector3f(-1, 0, 0);
		Vector3f tmpUp = new Vector3f(0, 1, 0);

		// Dirección hacia donde apunta la cámara.
		Vector3f tmpDir = new Vector3f(0, 0, -1);

		cam.setFrame(tmpLoc, tmpLeft, tmpUp, tmpDir);
		cam.update();

		timer = Timer.getTimer();

		display.getRenderer().setCamera(cam);
		
		KeyBindingManager.getKeyBindingManager().set("salir", KeyInput.KEY_ESCAPE);
		KeyBindingManager.getKeyBindingManager().set("arriba", KeyInput.KEY_Q);
		KeyBindingManager.getKeyBindingManager().set("abajo", KeyInput.KEY_Z);
	}

	@Override
	protected void reinit() {
		// TODO Auto-generated method stub

	}
	
	public void salir() {
		super.quit();
		System.exit(0);
	}

	@Override
	protected void render(float interpolation) {
		// TODO Auto-generated method stub

		display.getRenderer().clearBuffers();
		display.getRenderer().draw(rooNode);

	}

	@Override
	protected void update(float interpolation) {
		// TODO Auto-generated method stub
		
		timer.update();
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("salir", false)) {
			finished = true;
			salir();
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("arriba", true)) {
			cam.getLocation().setY(cam.getLocation().getY()+1);
			//cam.setDirection(esfera.);
			cam.lookAt(esfera.getLocalTranslation(), new Vector3f(0, 1, 0));
			
			cam.update();
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("abajo", true)) {
			cam.getLocation().setY(cam.getLocation().getY()-1);
			cam.lookAt(esfera.getLocalTranslation(), new Vector3f(0, 1, 0));
			cam.update();
		}
		
		
	}

}
