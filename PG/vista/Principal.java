package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import vista3d.Espacio3D;

import net.sourceforge.napkinlaf.NapkinLookAndFeel;
import net.sourceforge.napkinlaf.NapkinTheme;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import com.jme.input.KeyInput;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvas;
import com.jme.system.canvas.JMECanvasImplementor;
import com.jme.system.lwjgl.LWJGLSystemProvider;
import com.jme.util.GameTaskQueueManager;
import com.jmex.awt.input.AWTMouseInput;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;
import com.jmex.awt.lwjgl.LWJGLCanvas;

import ctrl.Ctrl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.concurrent.Callable;

import jmetest.util.JMESwingTest;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class Principal extends javax.swing.JFrame {

	private JPanel panelPrincipal;
	private JPanel panelVisualizacion;
	private JScrollPane scrollPanelIzquierdo;
	private JButton botonEditarPropiedades;
	private JMenuItem acercaDeMenu;
	private JSplitPane divisor;
	private JPanel panelNorteIconos;
	private JPanel panelSurContenido;
	private JMenu informacinoMenu;
	private JPanel panelIzquierdo;
	private JMenuItem salirMenu;
	private JSeparator separadorMenu;
	private JMenuItem cerrarMenu;
	private JMenuItem abrirMenu;
	private JMenu jMenu1;
	private JMenuBar menuPrincipal;
	private ImageIcon iconoEditar;

	// JMONKEY
	private LWJGLCanvas canvas = null;
	private Espacio3D impl;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		Principal inst = new Principal();
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);

	}

	private void establecerLookAndFeel() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			NapkinLookAndFeel laf = new NapkinLookAndFeel();
			//NapkinTheme tema = NapkinTheme.Manager.getTheme("blueprint");
			NapkinTheme tema = NapkinTheme.Manager.getTheme("napkin");
			for (String t : NapkinTheme.Manager.themeNames()) {
				System.out.println(t);
			}
			NapkinTheme.Manager.setCurrentTheme(tema);
			SwingUtilities.updateComponentTreeUI(this);
			UIManager.setLookAndFeel(laf);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Principal() {
		// super();
		establecerLookAndFeel();
		initGUI();
		initScene();
	}

	private void initScene() {
		// make the canvas:
		DisplaySystem display = DisplaySystem
				.getDisplaySystem(LWJGLSystemProvider.LWJGL_SYSTEM_IDENTIFIER);
		display.registerCanvasConstructor("AWT",
				LWJGLAWTCanvasConstructor.class);
		canvas = (LWJGLCanvas) display.createCanvas(panelVisualizacion
				.getWidth(), panelVisualizacion.getHeight());
		canvas.setUpdateInput(true);
		canvas.setTargetRate(60);

		// add a listener... if window is resized, we can do something about
		// it.
		canvas.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				doResize();
			}
		});
		
		// Setup key and mouse input
        KeyInput.setProvider(KeyInput.INPUT_AWT);
        KeyListener kl = (KeyListener) KeyInput.get();
        canvas.addKeyListener(kl);
        AWTMouseInput.setup(canvas, false);
        
        // Important! Here is where we add the guts to the panel:
        impl = new Espacio3D(panelVisualizacion.getWidth(), panelVisualizacion.getHeight());
        canvas.setImplementor(impl);

        Callable<?> call = new Callable<Object>() {
            public Object call() throws Exception {
                canvas.setBackground(new Color(0,0,72));
                return null;
            }
        };
        GameTaskQueueManager.getManager().render(call);
        
        
        
        canvas.setBounds(0, 0, panelVisualizacion.getWidth(), panelVisualizacion.getHeight());
        panelVisualizacion.add(canvas, BorderLayout.CENTER);
	}

	protected void doResize() {
		impl.resizeCanvas(canvas.getWidth(), canvas.getHeight());
		((JMECanvas) canvas).makeDirty();
	}

	private void initGUI() {

		try {
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this
					.setTitle("Modelado, Visualización e Interacción de Grafos en 3D ");
			{
				menuPrincipal = new JMenuBar();
				menuPrincipal.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				this.setJMenuBar(menuPrincipal);
				{
					jMenu1 = new JMenu();
					menuPrincipal.add(jMenu1);
					jMenu1.setText("Archivo");
					{
						abrirMenu = new JMenuItem();
						jMenu1.add(abrirMenu);
						abrirMenu.setText("Abrir Grafo");
						abrirMenu.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								abrirMenuActionPerformed(evt);
							}
						});
					}
					{
						cerrarMenu = new JMenuItem();
						jMenu1.add(cerrarMenu);
						cerrarMenu.setText("Cerrar Grafo");
					}
					{
						separadorMenu = new JSeparator();
						jMenu1.add(separadorMenu);
					}
					{
						salirMenu = new JMenuItem();
						jMenu1.add(salirMenu);
						salirMenu.setText("Salir");
					}
				}
				{
					informacinoMenu = new JMenu();
					menuPrincipal.add(informacinoMenu);
					informacinoMenu.setText("Información");					
					{
						acercaDeMenu = new JMenuItem();
						informacinoMenu.add(acercaDeMenu);
						acercaDeMenu.setText("Acerca De");
						acercaDeMenu.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								acercaDeMenuActionPerformed(evt);
							}
						});
					}
				}
			}
			{
				// Panel Principal
				panelPrincipal = new JPanel();
				panelPrincipal.setLayout(new BorderLayout());
				{
					// Panel Norte de iconos de opciones y demas.
					panelNorteIconos = new JPanel();
					FlowLayout panelNorteIconosLayout = new FlowLayout();
					panelNorteIconosLayout.setAlignment(FlowLayout.LEFT);
					panelNorteIconos.setLayout(panelNorteIconosLayout);
					panelNorteIconos.setBackground(Color.black);
					panelPrincipal.add(panelNorteIconos, BorderLayout.NORTH);
					{
						botonEditarPropiedades = new JButton();
						panelNorteIconos.add(botonEditarPropiedades);
						botonEditarPropiedades.setBorder(null);
						iconoEditar = new ImageIcon(getClass().getClassLoader()
								.getResource("imagenes/draw.png"));
						iconoEditar = new ImageIcon(iconoEditar.getImage()
								.getScaledInstance(48, 48,
										Image.SCALE_AREA_AVERAGING));
						botonEditarPropiedades.setIcon(iconoEditar);
					}
					panelNorteIconos.add(new JButton("2"));
					panelNorteIconos.add(new JButton("3"));
					panelNorteIconos.add(new JButton("4"));
					panelNorteIconos.add(new JButton("5"));
				}

				{
					// Panel sur de manejo de contenido de informacion.
					panelSurContenido = new JPanel();
					panelSurContenido.setLayout(new BorderLayout());
					panelPrincipal.add(panelSurContenido, BorderLayout.CENTER);
					{
						// Divisor
						divisor = new JSplitPane();
						panelSurContenido.add(divisor, BorderLayout.CENTER);
						{
							// Panel Izquierdo (Informacion del Grafo)
							scrollPanelIzquierdo = new JScrollPane();
							scrollPanelIzquierdo
									.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							scrollPanelIzquierdo
									.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							scrollPanelIzquierdo
									.setPreferredSize(new java.awt.Dimension(
											248, 290));

							panelIzquierdo = new JPanel();
							scrollPanelIzquierdo
									.setViewportView(panelIzquierdo);
							// panelIzquierdo.setBounds(31, 237, 62, 70);
							panelIzquierdo.setBackground(new java.awt.Color(
									255, 255, 255));
							panelIzquierdo.setBorder(BorderFactory
									.createEtchedBorder(BevelBorder.LOWERED));

						}
						{
							// Panel Derecho (Panel de visualizacion en 3D)
							panelVisualizacion = new JPanel();
							panelVisualizacion.setLayout(new BorderLayout());
							panelVisualizacion
									.setBackground(new java.awt.Color(128, 128,
											255));
							panelVisualizacion.setBorder(BorderFactory
									.createEtchedBorder(BevelBorder.LOWERED));
							
							panelVisualizacion.setPreferredSize(new java.awt.Dimension(580, 290));

						}

						divisor.setLeftComponent(scrollPanelIzquierdo);
						divisor.setRightComponent(panelVisualizacion);

					}
				}
				getContentPane().setLayout(new BorderLayout());
				this.setIconImage(new ImageIcon(getClass().getClassLoader()
						.getResource("imagenes/logo2.png")).getImage());
				getContentPane().add(panelPrincipal, BorderLayout.CENTER);
			}
			pack();
			// Trabajar con esa resolucion: 1024 x 728
			this.setSize(1024, 648);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void abrirMenuActionPerformed(ActionEvent evt) {
		Abrir ventanaAbrir = new Abrir(this);
		this.setEnabled(false);
		ventanaAbrir.setVisible(true);
	}

	public void cargarGrafo() {
		System.out.println("Cargando grafo!");
		boolean fueCreado = Ctrl.crearGrafo();
		impl.dibujarGrafo();

	}
	
	private void acercaDeMenuActionPerformed(ActionEvent evt) {
		Creditos creditos = new Creditos(this);
		this.setEnabled(false);
		creditos.setVisible(true);
	}

}
