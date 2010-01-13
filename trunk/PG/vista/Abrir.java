package vista;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

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
public class Abrir extends javax.swing.JFrame {
	private JTextField servidor;
	private JButton aceptar;
	private JLabel pincorrecto;
	private JLabel lcontraseña;
	private JLabel lpuerto;
	private JLabel lbd;
	private JLabel lusuario;
	private JLabel lservidor;
	private JButton cancelar;
	private JTextField puerto;
	private JTextField nombreBD;
	private JPasswordField contraseña;
	private JTextField usuario;
	private JPanel PanelAbrir;
	private Principal ventanaPrincipal;
	private ImageIcon iconoBad;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Abrir inst = new Abrir();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public Abrir() {
		super();
		initGUI();
	}

	public Abrir(Principal principal) {
		super();
		ventanaPrincipal = principal;
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Abrir Base de Datos");
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});

			{
				PanelAbrir = new JPanel();
				PanelAbrir.setLayout(null);
				getContentPane().add(PanelAbrir, BorderLayout.CENTER);
				PanelAbrir.setPreferredSize(new java.awt.Dimension(623, 231));
				// PanelAbrir.setFocusable(false);
				{
					servidor = new JTextField();
					PanelAbrir.add(servidor, "servidor");
					servidor.setBounds(124, 12, 191, 23);
				}
				{
					usuario = new JTextField();
					PanelAbrir.add(usuario, "usuario");
					usuario.setBounds(124, 54, 191, 23);
				}
				{
					contraseña = new JPasswordField();
					PanelAbrir.add(contraseña, "contraseña");
					contraseña.setBounds(438, 54, 142, 23);
				}
				{
					nombreBD = new JTextField();
					PanelAbrir.add(nombreBD, "nombreBD");
					nombreBD.setBounds(124, 97, 191, 23);
				}
				{
					puerto = new JTextField();
					puerto.setText("3306");
					PanelAbrir.add(puerto, "puerto");
					puerto.setBounds(438, 12, 142, 23);
				}
				{
					aceptar = new JButton();
					PanelAbrir.add(aceptar, "aceptar");
					aceptar.setText("Aceptar");
					aceptar.setBounds(124, 143, 81, 23);
					aceptar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							aceptarActionPerformed(evt);
						}
					});

				}
				{
					cancelar = new JButton();
					PanelAbrir.add(cancelar, "cancelar");
					cancelar.setText("Cancelar");
					cancelar.setBounds(233, 143, 82, 23);
					cancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							cancelarActionPerformed(evt);
						}
					});
				}
				{
					lservidor = new JLabel();
					PanelAbrir.add(lservidor);
					lservidor.setText("Servidor");
					lservidor.setBounds(60, 15, 51, 16);
				}
				{
					lusuario = new JLabel();
					PanelAbrir.add(lusuario);
					lusuario.setText("Usuario");
					lusuario.setBounds(63, 57, 45, 16);
				}
				{
					lbd = new JLabel();
					PanelAbrir.add(lbd);
					lbd.setText("Nombre BD");
					lbd.setBounds(37, 102, 70, 12);
				}
				{
					lcontraseña = new JLabel();
					PanelAbrir.add(lcontraseña);
					lcontraseña.setText("Contraseña");
					lcontraseña.setBounds(351, 59, 73, 13);
				}
				{
					lpuerto = new JLabel();
					PanelAbrir.add(lpuerto);
					lpuerto.setText("Puerto");
					lpuerto.setBounds(379, 15, 42, 16);
				}
				{
					pincorrecto = new JLabel();
					PanelAbrir.add(pincorrecto);
					iconoBad = new ImageIcon(getClass().getClassLoader()
							.getResource("imagenes/Error.png"));
					iconoBad = new ImageIcon(iconoBad.getImage()
							.getScaledInstance(48, 48,
									Image.SCALE_AREA_AVERAGING));
					pincorrecto.setBounds(450, 119, 51, 47);
					pincorrecto.setVisible(false);
				}
			}
			pack();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void cambiarIcono(boolean isOk) {
		if (!isOk) {
			pincorrecto.setIcon(iconoBad);
		}
		pincorrecto.repaint();
		pincorrecto.updateUI();
	}

	private void aceptarActionPerformed(ActionEvent evt) {	
		
		boolean creaConexion=true;
		if (puerto.getText().equals("") || usuario.getText().equals("")
				|| nombreBD.getText().equals("")) {
			creaConexion = false;
		} else {
			creaConexion = Ctrl.probarConexion(servidor.getText(), puerto
					.getText(), usuario.getText(), contraseña.getPassword(),
					nombreBD.getText());
			if (creaConexion) {
				ventanaPrincipal.setEnabled(true);
				this.dispose();
				ventanaPrincipal.cargarGrafo();
			} else {
				this.cambiarIcono(false);
				pincorrecto.setVisible(true);
				//cancelar.setEnabled(false);
			}			
		}
	}

	private void cancelarActionPerformed(ActionEvent evt) {
		
			ventanaPrincipal.setEnabled(true);
			this.dispose();
			//ventanaPrincipal.cargarGrafo();
		
	}

	private void thisWindowClosing(WindowEvent evt) {
		ventanaPrincipal.setEnabled(true);
	}

}
