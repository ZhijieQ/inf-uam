package control;

import gui.*;

import java.awt.event.*;

import mailUam.MailUam;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase ControlVerMensaje
 */
public class ControlVerMensaje implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Construsctor de ControlVerMensaje
	 * @param v ventana de la aplicacion GUIVerMensaje
	 * @param modelo modelo de la aplicacion 
	 */
	public ControlVerMensaje(Ventana v, MailUam modelo) {
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
	 * @param modelo the modelo to set
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
	 * @param v the v to set
	 */
	public void setV(Ventana v) {
		this.v = v;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUIVerMensaje menu = getV().getVerMensaje();
		if (source.equals(menu.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(menu.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = getV().getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(menu.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(menu.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(menu.getBotonEliminar())) {
			System.out.println("Cambiando a menu mensajes");
			getModelo().getLogged().getBuzon().removeMensaje(getV().getVerMensaje().getMensaje());
			getModelo().guardarUsuario();
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(menu.getBotonResponder())) {
			System.out.println("Cambiando a Crear Mensaje");
			GUICrearMensaje crearMensaje = v.getCrearMensaje();
			v.cambiarPanelCrearMensajes();
		}
	}

}
