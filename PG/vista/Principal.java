package vista;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.eclipse.swt.internal.theme.Theme;

import net.sourceforge.napkinlaf.NapkinLookAndFeel;
import net.sourceforge.napkinlaf.NapkinTheme;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import ctrl.Ctrl;

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

	{
		// Set Look & Feel
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel panelPrincipal;
	private JPanel panelVisualizacion;
	private JScrollPane scrollPanelIzquierdo;
	private JMenu acercaDeMenu;
	private JPanel panelIzquierdo;
	private JMenuItem salirMenu;
	private JSeparator separadorMenu;
	private JMenuItem cerrarMenu;
	private JMenuItem guardarMenu;
	private JMenuItem abrirMenu;
	private JMenu jMenu1;
	private JMenuBar menuPrincipal;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {

		//establecerLookAndFeel();
		Principal inst = new Principal();
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		inst.getJMenuBar().setBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED));

	}

	private void establecerLookAndFeel() {
		
		Toolkit.getDefaultToolkit().setDynamicLayout(true);
		//System.setProperty("sun.awt.noerasebackground", "true");
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			NapkinLookAndFeel laf = new NapkinLookAndFeel();
			
			NapkinTheme tema = NapkinTheme.Manager.getTheme("blueprint");
			
			for(String t : NapkinTheme.Manager.themeNames()){
				System.out.println(t);
			}
			//NapkinTheme.Manager.setCurrentTheme(tema);
			NapkinTheme.Manager.setCurrentTheme(tema);
			UIManager
					.setLookAndFeel(laf);
			
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

//		Toolkit.getDefaultToolkit().setDynamicLayout(true);
//		System.setProperty("sun.awt.noerasebackground", "true");
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		JDialog.setDefaultLookAndFeelDecorated(true);
//		try {
//			TinyLookAndFeel laf = new TinyLookAndFeel();			
//			ThemeDescription[] temas = Theme.getAvailableThemes();
//			Theme.loadTheme(temas[3]);			
//			System.out.println(temas[3]);
//			UIManager
//					.setLookAndFeel(laf);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

	}

	public Principal() {
		super();
		establecerLookAndFeel();
		initGUI();
	}

	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this
					.setTitle("Modelado, Visualización e Interacción de Grafos en 3D ");
			{
				menuPrincipal = new JMenuBar();
				setJMenuBar(menuPrincipal);
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
						guardarMenu = new JMenuItem();
						jMenu1.add(guardarMenu);
						guardarMenu.setText("Guardar Cambios");
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
					acercaDeMenu = new JMenu();
					menuPrincipal.add(acercaDeMenu);
					acercaDeMenu.setText("Acerca de...");
				}
			}
			{
				panelPrincipal = new JPanel();
				AnchorLayout panelPrincipalLayout = new AnchorLayout();
				getContentPane().add(panelPrincipal);
				panelPrincipal.setLayout(panelPrincipalLayout);

				// Trabajar con esa resolucion: 1024 x 728
				panelPrincipal.setPreferredSize(new java.awt.Dimension(1024,
						728));
				panelPrincipal.setBounds(0, 0, 864, 346);
				{
					panelVisualizacion = new JPanel();
					AnchorLayout panelVisualizacionLayout = new AnchorLayout();
					panelVisualizacion.setLayout(panelVisualizacionLayout);
					panelPrincipal.add(panelVisualizacion,
							new AnchorConstraint(50, 985, 888, 315,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL));
					panelVisualizacion.setBackground(new java.awt.Color(128,
							128, 255));
					panelVisualizacion.setBorder(BorderFactory
							.createEtchedBorder(BevelBorder.LOWERED));
					panelVisualizacion.setPreferredSize(new java.awt.Dimension(
							579, 290));
				}
				{
					scrollPanelIzquierdo = new JScrollPane();
					panelPrincipal.add(scrollPanelIzquierdo,
							new AnchorConstraint(50, 301, 888, 14,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL));
					scrollPanelIzquierdo
							.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					scrollPanelIzquierdo
							.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPanelIzquierdo
							.setPreferredSize(new java.awt.Dimension(248, 290));
					{
						panelIzquierdo = new JPanel();
						AnchorLayout panelIzquierdoLayout = new AnchorLayout();
						scrollPanelIzquierdo.setViewportView(panelIzquierdo);
						panelIzquierdo.setBounds(31, 237, 62, 70);
						panelIzquierdo.setBackground(new java.awt.Color(255,
								255, 255));
						panelIzquierdo.setBorder(BorderFactory
								.createEtchedBorder(BevelBorder.LOWERED));
						panelIzquierdo.setLayout(panelIzquierdoLayout);
					}
				}
			}
			pack();
			this.setSize(872, 430);
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

	}

}
