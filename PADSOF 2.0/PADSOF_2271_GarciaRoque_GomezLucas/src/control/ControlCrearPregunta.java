package control;

import gui.*;

import java.awt.event.*;

import javax.swing.JOptionPane;

import usuario.Profesor;
import mailUam.MailUam;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase GUIBuscarGrupo
 */
public class ControlCrearPregunta implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlCrearPregunta
	 * 
	 * @param v
	 *            Modelo de las ventanas
	 * @param modelo
	 *            Aplicacion donde estan los datos
	 */
	public ControlCrearPregunta(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	/**
	 * @return the modelo
	 */
	public MailUam getModelo() {
		return modelo;
	}

	/**
	 * @param modelo
	 *            the modelo to set
	 */
	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the v
	 */
	public Ventana getV() {
		return v;
	}

	/**
	 * @param v
	 *            the v to set
	 */
	public void setV(Ventana v) {
		this.v = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUICrearPregunta crearPregunta = v.getCrearPregunta();
		if (source.equals(crearPregunta.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(crearPregunta.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(crearPregunta.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(crearPregunta.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(crearPregunta.getButtonCrear())) {
			getModelo().cargarGrupos();
			getModelo().cargarUsuarios();
			if (crearPregunta.getTextPregunta() == null
					|| crearPregunta.getTextPreguntaText().equals("")) {
				JOptionPane.showMessageDialog(crearPregunta,
						"No se puede crear una pregunta sin contenido",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				if (!(getModelo().getLogged() instanceof Profesor)) {
					JOptionPane.showMessageDialog(crearPregunta,
							"Es necesario ser profesor para crear preguntas",
							"Error", JOptionPane.ERROR_MESSAGE);
					getV().getMenuGrupos().setValores();
					getV().cambiarPanelMenuGrupos();
					return;
				}

				System.out.println("Grupo:" + crearPregunta.getGrupo());
				System.out.println(crearPregunta.getTextPreguntaText());
				crearPregunta.getGrupo().crearPregunta(
						crearPregunta.getTextPreguntaText(),
						(Profesor) getModelo().getLogged());
				System.out
						.println(crearPregunta.getGrupo().getListaPreguntas());
				getModelo().guardarGrupo(crearPregunta.getGrupo());
				getModelo().guardarUsuario();
				getV().getMenuGrupos().setValores();
				getV().cambiarPanelMenuGrupos();
			}
		}
	}

}
