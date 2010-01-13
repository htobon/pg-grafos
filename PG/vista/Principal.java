package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;

import vista3d.Espacio3D;

import net.sourceforge.napkinlaf.NapkinLookAndFeel;
import net.sourceforge.napkinlaf.NapkinTheme;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import com.jme.input.InputSystem;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvas;
import com.jme.system.canvas.JMECanvasImplementor;
import com.jme.system.lwjgl.LWJGLSystemProvider;
import com.jme.util.GameTaskQueueManager;
import com.jmex.awt.input.AWTMouseInput;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;
import com.jmex.awt.lwjgl.LWJGLCanvas;
import com.jmex.awt.swingui.JMEDesktop;
import com.jmex.swt.input.SWTKeyInput;
import com.jmex.swt.input.SWTMouseInput;
import com.jmex.swt.lwjgl.LWJGLSWTCanvas;
import com.jmex.swt.lwjgl.LWJGLSWTCanvasConstructor;

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
	private ImageIcon iconoEditar, iconoGrafo, iconoGuardar, iconoReset;
	private JButton botonGuardar;
	public static JTextArea jtaInfo;
	private JButton botonAbrirGrafo;
	private JButton btnAbrirGrafo;
	private JButton botonReset;

	// JMONKEY
	private LWJGLCanvas canvas = null;
	// private LWJGLSWTCanvas canvas = null;

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
			NapkinTheme tema = NapkinTheme.Manager.getTheme("blueprint");
			// NapkinTheme tema = NapkinTheme.Manager.getTheme("napkin");
//			for (String t : NapkinTheme.Manager.themeNames()) {
//				System.out.println(t);
//			}
			NapkinTheme.Manager.setCurrentTheme(tema);
			// SwingUtilities.updateComponentTreeUI(this);
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

		// canvas = (LWJGLSWTCanvas) display.createCanvas(panelVisualizacion
		// .getWidth(), panelVisualizacion.getHeight());
		canvas.setUpdateInput(true);
		canvas.setTargetRate(60);

		// add a listener... if window is resized, we can do something about
		// it.

		canvas.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				doResize();
			}
		});

		// ------------------------------------------------------------
		if (!KeyInput.isInited())
			KeyInput.setProvider(InputSystem.INPUT_SYSTEM_AWT);
		KeyListener kl = (KeyListener) KeyInput.get();
		canvas.addKeyListener(kl);

		if (!MouseInput.isInited())
			MouseInput.setProvider(InputSystem.INPUT_SYSTEM_AWT);
		((AWTMouseInput) MouseInput.get()).setDragOnly(true);
		canvas.addMouseListener((MouseListener) MouseInput.get());
		canvas.addMouseWheelListener((MouseWheelListener) MouseInput.get());
		canvas.addMouseMotionListener((MouseMotionListener) MouseInput.get());
		// ------------------------------------------------------------

		// Setup key and mouse input

		// KeyInput.setProvider(KeyInput.INPUT_AWT);
		// KeyListener kl = (KeyListener) KeyInput.get();
		// canvas.addKeyListener(kl);
		// AWTMouseInput.setup(canvas, false);

		// Important! Here is where we add the guts to the panel:
		impl = new Espacio3D(panelVisualizacion.getWidth(), panelVisualizacion
				.getHeight());

		canvas.setImplementor(impl);

		Callable<?> call = new Callable<Object>() {
			public Object call() throws Exception {
				canvas.setBackground(new Color(0, 0, 72));
				return null;
			}
		};
		GameTaskQueueManager.getManager().render(call);

		// canvas.setBounds(0, 0, panelVisualizacion.getWidth(),
		// panelVisualizacion.getHeight());
		panelVisualizacion.add(canvas, BorderLayout.CENTER);
	}

	protected void doResize() {
		impl.resizeCanvas(canvas.getWidth(), canvas.getHeight());
		((JMECanvas) canvas).makeDirty();
		impl.dibujarCruz();

	}

	private void initGUI() {

		try {
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this
					.setTitle("Modelado, Visualizaci�n e Interacci�n de Grafos en 3D ");
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
					informacinoMenu.setText("Informaci�n");
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
						botonAbrirGrafo = new JButton("");
						botonAbrirGrafo.setBorder(null);
						panelNorteIconos.add(botonAbrirGrafo);
						iconoGrafo = new ImageIcon(getClass().getClassLoader()
								.getResource("imagenes/icono-grafo.png"));
						iconoGrafo = new ImageIcon(iconoGrafo.getImage()
								.getScaledInstance(43, 46,
										Image.SCALE_AREA_AVERAGING));
						botonAbrirGrafo.setIcon(iconoGrafo);
						botonAbrirGrafo.setToolTipText("Generar grafo");
						botonAbrirGrafo.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								botonAbrirGrafoActionPerformed(evt);
							}
						});
					}
					{

						botonEditarPropiedades = new JButton();
						panelNorteIconos.add(botonEditarPropiedades);
						botonEditarPropiedades.setBorder(null);
						iconoEditar = new ImageIcon(getClass().getClassLoader()
								.getResource("imagenes/draw.png"));
						iconoEditar = new ImageIcon(iconoEditar.getImage()
								.getScaledInstance(43, 46,
										Image.SCALE_AREA_AVERAGING));
						botonEditarPropiedades.setIcon(iconoEditar);
						botonEditarPropiedades.setToolTipText("Editar");
						botonEditarPropiedades
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										botonEditarPropiedadesActionPerformed(evt);
									}
								});
					}
					{
						botonGuardar = new JButton("");
						botonGuardar.setBorder(null);
						panelNorteIconos.add(botonGuardar);
						iconoGuardar = new ImageIcon(getClass()
								.getClassLoader().getResource(
										"imagenes/icono-guardar.png"));
						iconoGuardar = new ImageIcon(iconoGuardar.getImage()
								.getScaledInstance(43, 46,
										Image.SCALE_AREA_AVERAGING));
						botonGuardar.setIcon(iconoGuardar);
						botonGuardar.setToolTipText("Guardar grafo");
						botonGuardar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								botonGuardarActionPerformed(evt);
							}
						});
					}

					{
						btnAbrirGrafo = new JButton("");
						btnAbrirGrafo.setBorder(null);
						panelNorteIconos.add(btnAbrirGrafo);
						iconoGrafo = new ImageIcon(getClass().getClassLoader()
								.getResource("imagenes/icono-abrir.png"));
						iconoGrafo = new ImageIcon(iconoGrafo.getImage()
								.getScaledInstance(43, 46,
										Image.SCALE_AREA_AVERAGING));
						btnAbrirGrafo.setIcon(iconoGrafo);
						btnAbrirGrafo.setToolTipText("Abrir grafo");
						btnAbrirGrafo.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnAbrirGrafoActionPerformed(evt);
							}
						});
					}
					botonReset = new JButton();
					botonReset.setBorder(null);
					panelNorteIconos.add(botonReset);
					iconoReset = new ImageIcon(getClass().getClassLoader()
							.getResource("imagenes/icono-reset.png"));
					iconoReset = new ImageIcon(iconoReset.getImage()
							.getScaledInstance(43, 46,
									Image.SCALE_AREA_AVERAGING));
					botonReset.setIcon(iconoReset);
					botonReset.setToolTipText("Reestablecer C�mara");
					botonReset.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							botonResetActionPerformed(evt);
						}
					});
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
						divisor.setContinuousLayout(true);
						{
							// Panel Izquierdo (Informacion del Grafo)
							scrollPanelIzquierdo = new JScrollPane();
							scrollPanelIzquierdo
									.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
							{
								jtaInfo = new JTextArea();
								panelIzquierdo.add(jtaInfo);
								jtaInfo.setText("Informaci�n");
								// jtaInfo
								// .setPreferredSize(new java.awt.Dimension(
								// 202, 466));
								jtaInfo.setBorder(BorderFactory
										.createEmptyBorder(0, 0, 0, 0));
								jtaInfo.setEditable(false);
							}

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

							panelVisualizacion
									.setPreferredSize(new java.awt.Dimension(
											580, 290));

						}

						divisor.setLeftComponent(scrollPanelIzquierdo);
						divisor.setRightComponent(panelVisualizacion);
						panelVisualizacion
								.addComponentListener(new ComponentAdapter() {
									public void componentResized(
											ComponentEvent evt) {
										panelVisualizacionComponentResized(evt);
									}
								});

					}
				}
				getContentPane().setLayout(new BorderLayout());
				this.setIconImage(new ImageIcon(getClass().getClassLoader()
						.getResource("imagenes/logo2.png")).getImage());
				this.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent evt) {
						thisComponentResized(evt);
					}
				});
				getContentPane().add(panelPrincipal, BorderLayout.CENTER);
			}
			pack();
			// Trabajar con esa resolucion: 1024 x 728
			this.setSize(1024, 648);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void botonGuardarActionPerformed(ActionEvent evt) {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Guardar");
		jfc.setApproveButtonText("Guardar");
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return (f.getName().endsWith(".grafo") || f.isDirectory());
			}

			public String getDescription() {
				return ".grafo";
			}
		});

		int opci�n = jfc.showOpenDialog(null);
		if (opci�n == JFileChooser.APPROVE_OPTION) {

			File file = jfc.getSelectedFile();

			// Verificar existencia
			int respuesta = -1;
			if ((new File(file.getAbsoluteFile() + ".grafo")).exists()
					|| file.exists()) {
				respuesta = JOptionPane.showConfirmDialog(null,
						"El archivo ya existe.�Qui�res reemplazarlo?",
						"Confirmar Reemplazo", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
			} else {
				respuesta = JOptionPane.YES_OPTION;
			}

			// Si el archivo no existe o si se va a sobreescribir
			if (respuesta == JOptionPane.YES_OPTION) {
				Ctrl.guardarGrafo(file);
			}

		}
	}

	private void abrirMenuActionPerformed(ActionEvent evt) {
		Abrir ventanaAbrir = new Abrir(this);
		this.setEnabled(false);
		ventanaAbrir.setVisible(true);
	}

	public void cargarGrafo() {
		//System.out.println("Cargando grafo!");
		boolean fueCreado = Ctrl.crearGrafo();
		impl.dibujarGrafo();

	}

	private void acercaDeMenuActionPerformed(ActionEvent evt) {
		Creditos creditos = new Creditos(this);
		this.setEnabled(false);
		creditos.setVisible(true);
	}

	private void botonEditarPropiedadesActionPerformed(ActionEvent evt) {
		if (Ctrl.getGrafo() != null) {
			Cambios cambios = new Cambios(this);
			this.setEnabled(false);
			cambios.setVisible(true);
		}
	}

	private void botonAbrirGrafoActionPerformed(ActionEvent evt) {
		abrirMenuActionPerformed(evt);
	}

	private void btnAbrirGrafoActionPerformed(ActionEvent evt) {

		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return (f.getName().endsWith(".grafo") || f.isDirectory());
			}

			public String getDescription() {
				return ".grafo";
			}
		});
		int opci�n = jfc.showOpenDialog(null);
		if (opci�n == JFileChooser.APPROVE_OPTION) {

			File file = jfc.getSelectedFile();
			boolean ret = Ctrl.abrirGrafo(file);
			if (ret) {
				impl.dibujarGrafo();
			}
		}
	}

	public void cambiarPropiedadesGrafos() {
		// TODO
		// impl.dibujarGrafo();
		// Callable<?> call = new Callable<Object>() {
		// public Object call() throws Exception {
		int[] codigosNodos = Ctrl.getCodigosNodos();
		for (int codigoNodo : codigosNodos) {
			impl.cambiarColorNodo("Nodo " + codigoNodo, Ctrl
					.getColorNodo(codigoNodo));
		}

		int[] codigosAristas = Ctrl.getCodigosAristas();
		for (int codigoArista : codigosAristas) {
			impl.cambiarColorArista("Arista " + codigoArista, Ctrl
					.getColorArista(codigoArista));
		}
		// return null;
		// }
		// };
		// GameTaskQueueManager.getManager().render(call);
		impl.doUpdate();

	}

	private void botonResetActionPerformed(ActionEvent evt) {
		impl.resetCam();
	}

	private void panelVisualizacionComponentResized(ComponentEvent evt) {

		if (impl != null) {
			impl.dibujarCruz();
			impl.doUpdate();
		}

	}

	private void thisComponentResized(ComponentEvent evt) {
		if (impl != null)
			impl.dibujarCruz();
	}

}
