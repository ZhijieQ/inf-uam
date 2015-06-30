package gui;

import java.awt.event.ActionListener;
import layout.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import usuario.*;
import mailUam.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUIVerPerfil
 */
public class GUIVerPerfil extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelNombre;
	private JTextField campoNombre;
	private JLabel labelApellido;
	private JTextField campoApellido;
	private JLabel labelCorreo;
	private JTextField campoCorreo;
	private JLabel labelContrasena;
	private JTextField campoContrasena;
	private JButton botonGuardar;

	/**
	 * Constructor de la GUI
	 * @param modelo
	 * 		todos los datos de la aplizacion
	 */
	public GUIVerPerfil(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Ver Perfil");
		labelNombre = new JLabel("Nombre: ");
		campoNombre = new JTextField(17);
		labelApellido = new JLabel("Apellido: ");
		campoApellido = new JTextField();
		labelCorreo = new JLabel("Correo: ");
		campoCorreo = new JTextField(17);
		campoCorreo.setEditable(false);
		labelContrasena = new JLabel("Contraseña: ");
		campoContrasena = new JTextField(17);
		botonGuardar = new JButton("Guardar");

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		SpringLayout layout = new SpringLayout();
		p2.setLayout(layout);
		p2.add(labelNombre);
		p2.add(campoNombre);
		p2.add(labelApellido);
		p2.add(campoApellido);
		p2.add(labelCorreo);
		p2.add(campoCorreo);
		p2.add(labelContrasena);
		p2.add(campoContrasena);
		p1.add(p2);
		p1.add(botonGuardar);
		SpringUtilities.makeCompactGrid(p2,	4, 2,6, 6,6, 6);
		this.setVisible(true);
		add(p1);

	}

	/**
	 * @return the labelNombre
	 */
	public JLabel getLabelNombre() {
		return labelNombre;
	}

	/**
	 * @param labelNombre
	 *            the labelNombre to set
	 */
	public void setLabelNombre(JLabel labelNombre) {
		this.labelNombre = labelNombre;
	}

	/**
	 * @return the campoNombre
	 */
	public JTextField getCampoNombre() {
		return campoNombre;
	}

	/**
	 * @return the campoNombre
	 */
	public String getCampoNombreText() {
		return campoNombre.getText();
	}

	/**
	 * @param campoNombre
	 *            the campoNombre to set
	 */
	public void setCampoNombre(JTextField campoNombre) {
		this.campoNombre = campoNombre;
	}

	/**
	 * @param campoNombre
	 *            the campoNombre to set
	 */
	public void setCampoNombreText(String campoNombre) {
		this.campoNombre.setText(campoNombre);
	}

	/**
	 * @return the labelApellido
	 */
	public JLabel getLabelApellido() {
		return labelApellido;
	}

	/**
	 * @param labelApellido
	 *            the labelApellido to set
	 */
	public void setLabelApellido(JLabel labelApellido) {
		this.labelApellido = labelApellido;
	}

	/**
	 * @return the campoApellido
	 */
	public JTextField getCampoApellido() {
		return campoApellido;
	}

	/**
	 * @return the campoApellido
	 */
	public String getCampoApellidoText() {
		return campoApellido.getText();
	}

	/**
	 * @param campoApellido
	 *            the campoApellido to set
	 */
	public void setCampoApellido(JTextField campoApellido) {
		this.campoApellido = campoApellido;
	}

	/**
	 * @param campoApellido
	 *            the campoApellido to set
	 */
	public void setCampoApellidoText(String campoApellido) {
		this.campoApellido.setText(campoApellido);
	}

	/**
	 * @return the labelCorreo
	 */
	public JLabel getLabelCorreo() {
		return labelCorreo;
	}

	/**
	 * @param labelCorreo
	 *            the labelCorreo to set
	 */
	public void setLabelCorreo(JLabel labelCorreo) {
		this.labelCorreo = labelCorreo;
	}

	/**
	 * @return the campoCorreo
	 */
	public JTextField getCampoCorreo() {
		return campoCorreo;
	}

	/**
	 * @return the campoCorreo
	 */
	public String getCampoCorreoText() {
		return campoCorreo.getText();
	}

	/**
	 * @param campoCorreo
	 *            the campoCorreo to set
	 */
	public void setCampoCorreo(JTextField campoCorreo) {
		this.campoCorreo = campoCorreo;
	}

	/**
	 * @param campoCorreo
	 *            the campoCorreo to set
	 */
	public void setCampoCorreoText(String campoCorreo) {
		this.campoCorreo.setText(campoCorreo);
	}

	/**
	 * @return the labelContrasena
	 */
	public JLabel getLabelContrasena() {
		return labelContrasena;
	}

	/**
	 * @param labelContrasena
	 *            the labelContrasena to set
	 */
	public void setLabelContrasena(JLabel labelContrasena) {
		this.labelContrasena = labelContrasena;
	}

	/**
	 * @return the campoContrasena
	 */
	public JTextField getCampoContrasena() {
		return campoContrasena;
	}

	/**
	 * @return the campoContrasena
	 */
	public String getCampoContrasenaText() {
		return campoContrasena.getText();
	}

	/**
	 * @param campoContrasena
	 *            the campoContrasena to set
	 */
	public void setCampoContrasena(JTextField campoContrasena) {
		this.campoContrasena = campoContrasena;
	}

	/**
	 * @param campoContrasena
	 *            the campoContrasena to set
	 */
	public void setCampoContrasenaText(String campoContrasena) {
		this.campoContrasena.setText(campoContrasena);
	}

	/**
	 * @return the botonGuardar
	 */
	public JButton getBotonGuardar() {
		return botonGuardar;
	}

	/**
	 * @param botonGuardar
	 *            the botonGuardar to set
	 */
	public void setBotonGuardar(JButton botonGuardar) {
		this.botonGuardar = botonGuardar;
	}

	/**
	 * Establece los valores para que la vista de la GUI sea la adecuada
	 * 
	 * @param logged
	 *            usuario que se ha loggeado
	 */

	public void setValores(Usuario logged) {
		setCampoNombreText(logged.getNombre());
		setCampoApellidoText(logged.getApellido());
		setCampoCorreoText(logged.getCorreo());
		getCampoCorreo().setEditable(false);
		setCampoContrasenaText(logged.getPassword());
	}

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		this.botonGuardar.addActionListener(c);
	}

}
