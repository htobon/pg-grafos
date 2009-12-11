package vista;
import info.clearthought.layout.TableLayout;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.GridBagLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Cambios extends JFrame{
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
	private JComboBox jcboxFigura;
	private JLabel jlblFiguraNodo;
	private JLabel jlblNodo;
	private JLabel jlblAristas;
	private JLabel jlblNodos;

	private void initGUI() {
		try {
			{
				jspTipos = new JSplitPane();
				getContentPane().add(jspTipos, BorderLayout.CENTER);
				jspTipos.setPreferredSize(new java.awt.Dimension(603, 361));
				{
					jpAristas = new JPanel();
					jspTipos.add(jpAristas, JSplitPane.RIGHT);
					jpAristas.setBackground(new java.awt.Color(255,255,255));
					jpAristas.setLayout(null);
					{
						ComboBoxModel cbxTiposAristasModel = 
							new DefaultComboBoxModel(
									new String[] { "Item One", "Item Two" });
						cbxTiposAristas = new JComboBox();
						jpAristas.add(cbxTiposAristas);
						cbxTiposAristas.setModel(cbxTiposAristasModel);
						cbxTiposAristas.setBounds(60, 93, 182, 23);
					}
					{
						jlblAristas = new JLabel();
						jpAristas.add(jlblAristas);
						jlblAristas.setText("Aristas");
						jlblAristas.setBounds(34, 23, 120, 16);
					}
					{
						jlblArista = new JLabel();
						jpAristas.add(jlblArista);
						jlblArista.setText("Tipo");
						jlblArista.setBounds(60, 66, 24, 16);
					}
					{
						jlblColorArista = new JLabel();
						jpAristas.add(jlblColorArista);
						jlblColorArista.setText("Color");
						jlblColorArista.setBounds(60, 145, 29, 16);
					}
					{
						jbtnColorArista = new JButton();
						jpAristas.add(jbtnColorArista);
						jbtnColorArista.setBounds(101, 143, 35, 35);
						jbtnColorArista.setBackground(new java.awt.Color(0,0,255));
					}
				}
				{
					jpNodos = new JPanel();
					jspTipos.add(jpNodos, JSplitPane.LEFT);
					jpNodos.setPreferredSize(new java.awt.Dimension(297, 353));
					jpNodos.setBackground(new java.awt.Color(255,255,255));
					jpNodos.setLayout(null);
					{
						ComboBoxModel jcbxFigurasNodosModel = 
							new DefaultComboBoxModel(
									new String[] { "Item One", "Item Two" });
						jcbxTiposNodos = new JComboBox();
						jpNodos.add(jcbxTiposNodos);
						jcbxTiposNodos.setModel(jcbxFigurasNodosModel);
						jcbxTiposNodos.setBounds(62, 93, 170, 23);
					}
					{
						jlblNodos = new JLabel();
						jpNodos.add(jlblNodos);
						jlblNodos.setText("Nodos");
						jlblNodos.setBounds(25, 23, 83, 16);
					}
					{
						jlblNodo = new JLabel();
						jpNodos.add(jlblNodo);
						jlblNodo.setText("Tipo");
						jlblNodo.setBounds(62, 66, 30, 16);
					}
					{
						jlblColorNodo = new JLabel();
						jpNodos.add(jlblColorNodo);
						jlblColorNodo.setText("Color");
						jlblColorNodo.setBounds(62, 145, 29, 16);
					}
					{
						jbtnColorNodo = new JButton();
						jpNodos.add(jbtnColorNodo);
						jbtnColorNodo.setBounds(103, 143, 35, 35);
						jbtnColorNodo.setBackground(new java.awt.Color(255,0,0));
					}
					{
						jlblFiguraNodo = new JLabel();
						jpNodos.add(jlblFiguraNodo);
						jlblFiguraNodo.setText("Figura");
						jlblFiguraNodo.setBounds(62, 197, 33, 16);
					}
					{
						ComboBoxModel jcboxFiguraModel = 
							new DefaultComboBoxModel(
									new String[] { "Cubo", "Esfera", "Pirámide" });
						jcboxFigura = new JComboBox();
						jpNodos.add(jcboxFigura);
						jcboxFigura.setModel(jcboxFiguraModel);
						jcboxFigura.setBounds(62, 221, 170, 23);
					}
				}
			}
			pack();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
