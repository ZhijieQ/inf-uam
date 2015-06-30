package usuario;
import grupo.Grupo;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

import buzon.Buzon;
import mensaje.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Usuario
 */


public abstract class Usuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String correo;
	private String password;
	private ArrayList<Grupo> listaGrupos;
	private ArrayList<Usuario> contactos;
	private Buzon buzon;
	private Calendar lastLogin;
	
	/**
	 * Constructor de Usuario 
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @param password
	 */
	public Usuario(String nombre, String apellido, String correo,String password) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.password = password;
		this.contactos=new ArrayList<Usuario>();
		this.listaGrupos= new ArrayList<Grupo>();
		this.lastLogin=Calendar.getInstance();
		this.buzon=new Buzon();
	}
	
	/**
	 * @return the idUsuario
	 */	
	public int getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}
	
	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the lastLogin
	 */
	public Calendar getLastLogin() {
		return lastLogin;
	}
	
	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Calendar lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	/**
	 * @return the listaGrupos
	 */
	public ArrayList<Grupo> getListaGrupos() {
		return listaGrupos;
	}
	
	/**
	 * @param listaGrupos the listaGrupos to set
	 */
	public void setListaGrupos(ArrayList<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
	
	/**
	 * @return the contactos
	 */
	public ArrayList<Usuario> getContactos() {
		return contactos;
	}
	
	/**
	 * @param contactos the contactos to set
	 */
	public void setContactos(ArrayList<Usuario> contactos) {
		this.contactos = contactos;
	}
	
	/**
	 * @return the buzon
	 */
	public Buzon getBuzon() {
		return buzon;
	}
	
	/**
	 * @param buzon the buzon to set
	 */
	public void setBuzon(Buzon buzon) {
		this.buzon = buzon;
	}

	/** 
	 * @return the mensajes de un buzon
	 */
	public ArrayList<MensajeUsuario> listarMensajes(){
		return buzon.getMensajes();
	}	
	
	/**
	 * Mira el perfil de un usuario
	 * @return el perfil del usuario
	 */
	public String verPerfil(){
		return this.toString();
	}
	
	/**
	 * Enviar un mensaje a un grupo
	 * @param grupo
	 * @param mensaje
	 * @param enviarBuzon
	 * @return true si lo envia, false si no puede
	 */
	public boolean enviarMensajeGrupo(int id,Grupo grupo, String mensaje, boolean enviarBuzon){
		for(Grupo g: getListaGrupos()){
			if(g.getNombre().equals(grupo.getNombre()) && g.getIdGrupo()==grupo.getIdGrupo()){
				if(enviarBuzon){
					for(Usuario u:g.getListaUsuarios()){
						this.enviarMensaje(id,u, mensaje, "Mensaje de Grupo: "+g.getNombre(), this);
					}
				}
				return g.addMensaje(new MensajeGrupo(id, mensaje, this));
			}
		}
		return false;
	}
	
	/**
	 * Envia un mensaje al destinatario
	 * @param destinatario
	 * @param cuerpo
	 * @param fecha
	 * @param sujeto
	 * @param remitente
	 * @return true si lo envia, false si no
	 */
	private boolean enviarMensaje(int id,Usuario destinatario, String cuerpo, String sujeto, Usuario remitente) {
		return destinatario.addMensajeBuzon(new MensajeUsuario(id,destinatario, cuerpo, sujeto, remitente));
		
	}

	/**
	 * Sale de un grupo
	 * @param grupo
	 * @return true si ha salido del grupo, false si no ha podido
	 */
	public boolean salirGrupo(Grupo grupo){
		for(Grupo g: getListaGrupos()){
			if(g.getNombre().equals(grupo.getNombre()) && g.getIdGrupo()==grupo.getIdGrupo()){
				return g.removeUsuario(this) && getListaGrupos().remove(g);
			}
		}
		return false;
	}
	
	/**
	 * Modera un mensaje
	 * @param mensaje
	 * @param aceptar
	 * @return si el mensaje se ha moderado false si no se podido moderar
	 */
	public boolean moderarMensaje(MensajeUsuario mensaje, boolean aceptar){
		if(aceptar){
			String nombre=mensaje.getSujeto().split(":")[1];//TODO Pasar el id o el nombre
			//Grupo g=buscarGrupo(nombre);//TODO hacer funcion o bucle?¿?
			return true;
		}
		return false;
	}
	
	/**
	 * Anade un grupo al usuario
	 * @param grupo
	 * @return true si lo anade, faslse si no
	 */
	public boolean addGrupo(Grupo grupo){//TODO comprobar si el grupo ya esta
		if(grupo.isPrivado()){
			return false;
		}
		if(listaGrupos.contains(grupo)){
			return true;
		}
		listaGrupos.addAll(grupo.getTodosSubGrupos());
		return listaGrupos.add(grupo);
	}	
	
	/**
	 * Elimina un grupo 
	 * @param grupo
	 * @return true si ha podido false  si no
	 */
	public boolean removeGrupo(Grupo grupo){
		return listaGrupos.remove(grupo);
	}
	/**
	 * Anade un contacto a tu usuario
	 * @param contacto
	 * @return true si anade el contacto false si no
	 */
	public boolean addContacto(Usuario contacto){//TODO comprobar si el usuario ya esta
		if(contacto==null){
			contactos = new ArrayList<Usuario>();
		}
		this.contactos.add(contacto);
		return true;
	}
	
	/**
	 * Elimina un contacto 
	 * @param contacto
	 * @return true si ha podido false  si no
	 */
	public boolean removeContacto(Usuario contacto){
		return contactos.remove(contacto);
	}
	
	/**
	 * @return true si es un profesor false en otro caso
	 */
	public abstract boolean isProfesor();
	/**
	 * @return true si es un visitante false en otro caso
	 */
	public boolean isVisitante(){
		return false;
	}
	/**
	 * Anade un mensaje a un buzon
	 * @param mensaje
	 * @return true si ha podido, false si no
	 */
	public boolean addMensajeBuzon(MensajeUsuario mensaje ){
		return  buzon.addMensajeBuzon(mensaje);
	}
	

	
	/**
	 * Guarda un usuario en la aplicacion
	 * @return true si lo ha guardado, false si no lo ha podido guardar
	 * @throws IOException
	 */
	public void guardarUsuario(String barra) throws IOException{
		FileOutputStream fos = new FileOutputStream("aplicacion"+barra+"usuario"+
									barra+getCorreo()+barra+"datos.obj");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
	}
	
	/**
	 * @param mensaje
	 * @return muestra el mensaje Seleccionado
	 */
	public String verMensaje(Mensaje mensaje){
		for(Mensaje m:listarMensajes())
			if (m.equals(mensaje))
				return m.toString();
		return null;
	}
	
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre
				+ ", apellido=" + apellido + ".\nCorreo=" + correo
				+ ".\nListaGrupos=" + listaGrupos
				+ ".\nContactos=" + contactos 
				+ ".\nLastLogin=" + lastLogin.getTime() + "]";
	}

	public boolean removeMensaje(MensajeUsuario mensaje) {
		return getBuzon().removeMensaje(mensaje);
	}
	
	
	
}

