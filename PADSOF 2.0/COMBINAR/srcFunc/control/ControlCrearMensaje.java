package control;

import gui.*;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.JOptionPane;

import usuario.Usuario;
import mailUam.MailUam;
import mensaje.Mensaje;
import mensaje.MensajeUsuario;

public class ControlCrearMensaje implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;
	
	public ControlCrearMensaje(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	
	
	public MailUam getModelo() {
		return modelo;
	}



	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}



	public Ventana getV() {
		return v;
	}



	public void setV(Ventana v) {
		this.v = v;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		System.out.println("Activando controlador: "+getClass());
		GUICrearMensaje crearMensaje = v.getCrearMensaje();
		if(source.equals(crearMensaje.getBotonMensajes())){
			System.out.println("Cambiando a Mensajes");
			GUIMensaje menuMensaje = v.getMensajes();
			menuMensaje.setValores();
			v.cambiarPanelMenuMensajes();
		}else if(source.equals(crearMensaje.getBotonGrupos())){
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		}else if(source.equals(crearMensaje.getBotonVerPrefil())){
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		}else if(source.equals(crearMensaje.getBotonSalir())){
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		}
		else if (source.equals(crearMensaje.getBotonEnviar())){
			Usuario dest= null;
			getModelo().cargarUsuarios();
			if((crearMensaje.getListContactos().getSelectedValue()==null || 
					crearMensaje.getListContactos().getSelectedValue().toString().equals("")) &&
					crearMensaje.getTextCorreoText().equals("")){
				JOptionPane.showMessageDialog(crearMensaje,
						"No se especifico ningun destinatario", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if(crearMensaje.getListContactos().getSelectedValue()== null ||
					crearMensaje.getListContactos().getSelectedValue().toString().equals("")){
				dest =getModelo().buscarUsuario(crearMensaje.getTextCorreoText());
			}
			else{
				dest = getModelo().buscarUsuario(crearMensaje.getListContactos().
						getSelectedValue().toString());
			}
			if (dest== null){
				JOptionPane.showMessageDialog(crearMensaje,
						"El usuario especificado no existe", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			MensajeUsuario msg = new MensajeUsuario(0, dest, crearMensaje.getTextAsuntoText()
					, crearMensaje.getTextMensajeText(), getModelo().getLogged());
			dest.addMensajeBuzon(msg);
			System.out.println(dest.getBuzon().getMensajes());
			getModelo().guardarUsuarios();
			/*try {
				dest.guardarUsuario(getModelo().getBarra());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			getModelo().cargarUsuarios();
			v.getMensajes().setValores();
			v.cambiarPanelMenuMensajes();
		}
	}

}
