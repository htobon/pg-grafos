package vista;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



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
public class Creditos extends javax.swing.JFrame{
	
	private JLabel autores;
	private JLabel  tutor;
	private JLabel  objetivo;
	private JLabel  universidad;
	private JLabel  fecha;	
	private JPanel  creditos;
	private JLabel imagen;
	private ImageIcon icono;
	
	public Creditos(){
		super();
		inicializar();
		pack();
	}
	
	private void thisWindowClosing(WindowEvent evt) {
		this.dispose();
	}
	
	public void inicializar(){
		
		
		this.setTitle("Acerca de..");
		{
			creditos = new JPanel();
			getContentPane().add(creditos, BorderLayout.CENTER);
			creditos.setLayout(null);
			creditos.setPreferredSize(new java.awt.Dimension(398, 231));
			creditos.setBackground(new java.awt.Color(255,255,255));
			{
				imagen = new JLabel();
				creditos.add(imagen);
				icono = new ImageIcon(getClass().getClassLoader().getResource("imagenes/logo2.png"));
				imagen.setIcon(icono);
				imagen.setBounds(12, 12, 184, 177);
				
			}
			{
				autores = new JLabel();
				creditos.add(autores);
				autores.setText("<html>&nbsp &nbsp Proyecto Realizado Por " +
						"<br> Héctor Fabio Tobón Rincón <br>" +
						"Verónica Ramírez Higuera</html>");
				autores.setBounds(200, 10, 163, 61);
			}
			{
				tutor = new JLabel();
				creditos.add(tutor);
				tutor.setText("<html>&nbsp &nbsp &nbsp Supervisado Por<br>" +
						"Juan Diego Urán Correa</html>");
				tutor.setBounds(211, 65, 140, 43);
			}
			{
				objetivo = new JLabel();
				creditos.add(objetivo);
				objetivo.setText("<html>Proyecto de Grado para &nbsp &nbsp &nbsp optar al grado de<br>" +
						"Ingeniero de Sistemas</html>");
				objetivo.setBounds(211, 107, 152, 60);
			}
			{
				universidad = new JLabel();
				creditos.add(universidad);
				universidad.setText("Universidad Icesi");
				universidad.setBounds(231, 164, 101, 22);
			}	
		{
			fecha = new JLabel();
			creditos.add(fecha);
			fecha.setText("2009");
			fecha.setBounds(264, 177, 35, 26);
		}
		}
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				thisWindowClosing(evt);
			}
		});

	}
	
	public static void main(String[] arg){
		Creditos ventana=new Creditos();
		ventana.setSize(400, 240);
		ventana.setResizable(false);
		ventana.setVisible(true);	
	}

}
