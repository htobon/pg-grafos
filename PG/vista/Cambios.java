package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.jme.renderer.ColorRGBA;

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
public class Cambios extends JFrame {
	private JSplitPane jspTipos;
	private JPanel jpAristas;
	private JPanel jpNodos;
	private JComboBox jcbxTiposNodos;
	private JComboBox cbxTiposAristas;
	private JButton jbtnColorArista;
	private JButton jbtnColorNodo;
	private JLabel jlblColorArista;
	private JLabel jlblColorNodo;
	private JLabel jlblArista;
	private JButton jbtnCancelar;
	private JButton jbtnAceptar;
	private JPanel jpPrincipal;
	private JComboBox jcboxFigura;
	private JLabel jlblFiguraNodo;
	private JLabel jlblNodo;
	private JLabel jlblAristas;
	private JLabel jlblNodos;
	private JFrame principal;
	private JPanel panelPrincipal;

	public Cambios(JFrame principal) {
		this.principal = principal;
		initGUI();
		inicializarListas();
		inicializarColores();
		this.setTitle("Modificaciones");
	}

	private void inicializarColores() {
		ColorRGBA colorN= Ctrl.getColorTipoNodo((String)jcbxTiposNodos.getItemAt(0));
		jbtnColorNodo.setBackground(new Color(colorN.asIntARGB()));
		
		ColorRGBA colorA= Ctrl.getColorTipoArista((String)cbxTiposAristas.getItemAt(0));
		jbtnColorArista.setBackground(new Color(colorA.asIntARGB()));
	}

	private void thisWindowClosing(WindowEvent evt) {
		principal.setEnabled(true);
		this.dispose();
	}

	private void inicializarListas() {
		DefaultComboBoxModel modeloNodos = (DefaultComboBoxModel) (jcbxTiposNodos
				.getModel());
		modeloNodos.removeAllElements();
		String[] tiposNodos = Ctrl.getTiposNodos();
		for (int i = 0; i < tiposNodos.length; i++) {
			modeloNodos.addElement(tiposNodos[i]);
		}
		jcbxTiposNodos.setSelectedIndex(0);

		jcbxTiposNodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {								
												
				ColorRGBA colorN= Ctrl.getColorTipoNodo(jcbxTiposNodos.getSelectedItem().toString());
				jbtnColorNodo.setBackground(new Color(colorN.asIntARGB()));
				
				String figura = Ctrl.getFiguraNodo((String)jcbxTiposNodos.getSelectedItem().toString());
				jcboxFigura.setSelectedItem(figura);
				
			}
		});
		
		DefaultComboBoxModel modeloAristas = (DefaultComboBoxModel) (cbxTiposAristas
				.getModel());
		modeloAristas.removeAllElements();
		String[] tiposAristas = Ctrl.getTiposAristas();
		for (int i = 0; i < tiposAristas.length; i++) {
			modeloAristas.addElement(tiposAristas[i]);
		}
		cbxTiposAristas.setSelectedIndex(0);
		
		cbxTiposAristas.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e) {
				ColorRGBA colorA= Ctrl.getColorTipoArista((String)cbxTiposAristas.getSelectedItem());
				jbtnColorArista.setBackground(new Color(colorA.asIntARGB()));
			}
		});
		
		String figura = Ctrl.getFiguraNodo((String)jcbxTiposNodos.getItemAt(0));
		jcboxFigura.setSelectedItem(figura);
		
	}

	private void initGUI() {
		try {
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(new BorderLayout());
			getContentPane().add(panelPrincipal);
			panelPrincipal.setPreferredSize(new java.awt.Dimension(603, 238));
			{
				jspTipos = new JSplitPane();
				panelPrincipal.add(jspTipos, BorderLayout.CENTER);
				jspTipos.setPreferredSize(new java.awt.Dimension(603, 302));
				{
					jpAristas = new JPanel();
					jspTipos.add(jpAristas, JSplitPane.RIGHT);
					jpAristas.setLayout(null);
					{
						ComboBoxModel cbxTiposAristasModel = new DefaultComboBoxModel(
								new String[] { "Item One", "Item Two" });
						cbxTiposAristas = new JComboBox();
						jpAristas.add(cbxTiposAristas);
						cbxTiposAristas.setModel(cbxTiposAristasModel);
						cbxTiposAristas.setBounds(84, 63, 182, 23);
					}
					{
						jlblAristas = new JLabel();
						jpAristas.add(jlblAristas);
						jlblAristas.setText("Aristas");
						jlblAristas.setBounds(86, 20, 120, 21);
						jlblAristas.setFont(jlblAristas.getFont().deriveFont(18f));
						jlblAristas.setFont(jlblAristas.getFont().deriveFont(Font.BOLD));
						
						//jlblAristas.setFont(new java.awt.Font("Tahoma",1,16));
						jlblAristas.setHorizontalAlignment(SwingConstants.CENTER);
					}
					{
						jlblArista = new JLabel();
						jpAristas.add(jlblArista);
						jlblArista.setText("Tipo");
						jlblArista.setBounds(26, 66, 34, 16);
					}
					{
						jlblColorArista = new JLabel();
						jpAristas.add(jlblColorArista);
						jlblColorArista.setText("Color");
						jlblColorArista.setBounds(26, 107, 40, 16);
					}
					{
						jbtnColorArista = new JButton();
						jpAristas.add(jbtnColorArista);
						jbtnColorArista.setBounds(84, 106, 119, 26);
						jbtnColorArista.setBackground(new java.awt.Color(0, 0,
								255));
						jbtnColorArista.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jbtnColorAristaActionPerformed(evt);
							}
						});
					}
				}
				{
					jpNodos = new JPanel();
					jspTipos.add(jpNodos, JSplitPane.LEFT);
					jpNodos.setPreferredSize(new java.awt.Dimension(297, 309));
					jpNodos.setLayout(null);
					{
						ComboBoxModel jcbxFigurasNodosModel = new DefaultComboBoxModel(
								new String[] { "Item One", "Item Two" });
						jcbxTiposNodos = new JComboBox();
						jpNodos.add(jcbxTiposNodos);
						jcbxTiposNodos.setModel(jcbxFigurasNodosModel);
						jcbxTiposNodos.setBounds(93, 65, 170, 23);

					}
					{
						jlblNodos = new JLabel();
						jpNodos.add(jlblNodos);
						jlblNodos.setText("Nodos");
						jlblNodos.setBounds(74, 22, 142, 18);
						jlblNodos.setHorizontalAlignment(SwingConstants.CENTER);
						jlblNodos.setFont(jlblNodos.getFont().deriveFont(18f));
						jlblNodos.setFont(jlblNodos.getFont().deriveFont(Font.BOLD));
					}
					{
						jlblNodo = new JLabel();
						jpNodos.add(jlblNodo);
						jlblNodo.setText("Tipo");
						jlblNodo.setBounds(42, 68, 35, 16);
					}
					{
						jlblColorNodo = new JLabel();
						jpNodos.add(jlblColorNodo);
						jlblColorNodo.setText("Color");
						jlblColorNodo.setBounds(42, 107, 44, 16);
					}
					{
						jbtnColorNodo = new JButton();
						jpNodos.add(jbtnColorNodo);
						jbtnColorNodo.setBounds(93, 106, 116, 26);
						jbtnColorNodo.setBackground(new java.awt.Color(255, 0,
								0));
						jbtnColorNodo.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jbtnColorNodoActionPerformed(evt);
							}
						});
					}
					{
						jlblFiguraNodo = new JLabel();
						jpNodos.add(jlblFiguraNodo);
						jlblFiguraNodo.setText("Figura");
						jlblFiguraNodo.setBounds(42, 151, 44, 16);
					}
					{
						ComboBoxModel jcboxFiguraModel = new DefaultComboBoxModel(
								new String[] { "Cubo", "Esfera", "Pirámide" });
						jcboxFigura = new JComboBox();
						jpNodos.add(jcboxFigura);
						jcboxFigura.setModel(jcboxFiguraModel);
						jcboxFigura.setBounds(93, 148, 170, 23);
					}
				}
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						thisWindowClosing(evt);
					}
				});
			}
			{
				jpPrincipal = new JPanel();
				panelPrincipal.add(jpPrincipal, BorderLayout.SOUTH);
				jpPrincipal.setPreferredSize(new java.awt.Dimension(603, 40));
				jpPrincipal.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				jpPrincipal.setLayout(null);
				{
					jbtnAceptar = new JButton();
					jpPrincipal.add(jbtnAceptar);
					jbtnAceptar.setText("Aceptar");
					jbtnAceptar.setBounds(171, 9, 89, 23);
					jbtnAceptar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jbtnAceptarActionPerformed(evt);
						}
					});
				}
				{
					jbtnCancelar = new JButton();
					jpPrincipal.add(jbtnCancelar);
					jbtnCancelar.setText("Cancelar");
					jbtnCancelar.setBounds(342, 9, 87, 23);
					jbtnCancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jbtnCancelarActionPerformed(evt);
						}
					});
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbtnColorNodoActionPerformed(ActionEvent evt) {

		Color elegido = JColorChooser.showDialog(null, "Color Nodo", jbtnColorNodo
				.getBackground());
		if (elegido != null) {
			jbtnColorNodo.setBackground(elegido);
		}
	}

	private void jbtnColorAristaActionPerformed(ActionEvent evt) {
		Color elegido = JColorChooser.showDialog(null, "Color Arista", jbtnColorArista
				.getBackground());
		if (elegido != null) {
			jbtnColorArista.setBackground(elegido);
		}
	}
	
	private void jbtnAceptarActionPerformed(ActionEvent evt) {
		//Cambios en Nodos
		Ctrl.setFiguraNodos(jcbxTiposNodos.getSelectedItem().toString(), jcboxFigura.getSelectedItem().toString());
		Color colorNodo= jbtnColorNodo.getBackground();
		Ctrl.setColoresNodos(jcbxTiposNodos.getSelectedItem().toString(), new ColorRGBA(colorNodo.getRed() / 255f, 
				colorNodo.getGreen() / 255f, colorNodo.getBlue() / 255f, colorNodo.getAlpha() / 255f));

		
		//Cambios en Aristas
		Color colorArista= jbtnColorArista.getBackground();
		Ctrl.setColoresAristas(cbxTiposAristas.getSelectedItem().toString(),  new ColorRGBA(colorArista.getRed() / 255f, 
				colorArista.getGreen() / 255f, colorArista.getBlue() / 255f, colorArista.getAlpha() / 255f));
		
		((Principal)principal).cambiarPropiedadesGrafos();
		
		//Cerrar Ventana
		principal.setEnabled(true);
		this.dispose();
	}
	
	private void jbtnCancelarActionPerformed(ActionEvent evt) {
		//Cerrar Ventana
		principal.setEnabled(true);
		this.dispose();
	}

}