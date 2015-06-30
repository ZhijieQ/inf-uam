package pruebasJUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import usuario.Estudiante;
import usuario.Usuario;
import usuario.Visitante;
import mailUam.*;
import grupo.*;
public class testMailUam {

	@Test
	public void testGetListaTodosGrupos() {
		MailUam app = new MailUam();
		int tam= app.getListaGrupos().size();
		Grupo g = new Grupo(0, "testMailUam", false, null);
		if(tam>0)
			app.getListaGrupos().get(0).getSubGrupos().add(g);
		else
			app.getListaGrupos().add(g);
		assertSame(app.getListaTodosGrupos().size(), tam+1);
		assertTrue(app.getListaTodosGrupos().contains(g));
		
	}

	@Test
	public void testLogin() {
		MailUam app = new MailUam();
		app.login("maria.martin@ddm.es", "mamnds455");
		assertTrue(app.getLogged().getCorreo().equals("maria.martin@ddm.es"));
	}

	@Test
	public void testLoginVisitante() {
		MailUam app = new MailUam();
		Usuario u= new Visitante("testLogin", "testLogin", "testLogin", "testLogin");
		app.getListaUsuarios().add(u);
		app.loginVisitante("testLogin");
		assertTrue(app.getLogged().getCorreo().equals("testLogin"));
	}

	@Test
	public void testLogout() {
		MailUam app = new MailUam();
		app.login("maria.martin@ddm.es", "mamnds455");
		app.logout();
		assertNull(app.getLogged());
	}




	@Test
	public void testGuardarUsuarios() {
		MailUam app = new MailUam();
		app.getListaUsuarios().get(0).setNombre("testGuardarUsuarios");
		app.guardarUsuarios();
		app = new MailUam();
		String s2 =app.getListaUsuarios().get(0).getNombre();
		assertTrue(s2.equals("testGuardarUsuarios"));
	}

	@Test
	public void testBuscarUsuarios() {
		MailUam app = new MailUam();
		assertTrue(app.buscarUsuarios("maria.martin@ddm.es").size()>0);
	}

	@Test
	public void testBuscarUsuario() {
		MailUam app = new MailUam();
		assertTrue(app.buscarUsuario("maria.martin@ddm.es").getCorreo().equals("maria.martin@ddm.es"));
	}

	@Test
	public void testBuscarGrupoLista() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testBuscarGrupoLista" , false, null);
		app.crearGrupoDir(g.getNombre());
		app.addGrupo(g);
		app.guardarGrupos();
		assertTrue(app.buscarGrupoLista("testBuscarGrupoLista").size()>0);
	}

	@Test
	public void testBuscarGrupo() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testBuscarGrupo" , false, null);
		app.crearGrupoDir(g.getNombre());
		app.addGrupo(g);
		app.guardarGrupos();
		assertTrue(app.buscarGrupo("testBuscarGrupo").getNombre().equals("testBuscarGrupo"));
	}



	@Test
	public void testAddGrupo() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testBuscarGrupo" , false, null);
		app.addGrupo(g);
		assertTrue(app.getListaGrupos().contains(g));
	}

	@Test
	public void testAddUsuario() {
		MailUam app = new MailUam();
		Usuario u = new Estudiante("testAddUsuario", "testAddUsuario","testAddUsuario","testAddUsuario");
		app.addUsuario(u);
		assertTrue(app.getListaUsuarios().contains(u));
	}



	@Test
	public void testCargarUsuario() {
		MailUam app = new MailUam();
		assertTrue(app.cargarUsuario("maria.martin@ddm.es").getCorreo().equals("maria.martin@ddm.es"));
	}

	@Test
	public void testGuardarUsuario() {
		MailUam app = new MailUam();
		app.login("maria.martin@ddm.es", "mamnds455");
		app.getLogged().setNombre("testGuardarUsuario");
		app.guardarUsuario();
		app = new MailUam();
		app.login("maria.martin@ddm.es", "mamnds455");
		assertTrue(app.getLogged().getNombre().equals("testGuardarUsuario"));
	}

	@Test
	public void testGuardarUsuarioUsuario() {
		MailUam app = new MailUam();
		Usuario u=app.getListaUsuarios().get(0);
		u.setNombre("testGuardarUsuarioUsuario");
		app.guardarUsuario(u);
		app = new MailUam();
		u=app.getListaUsuarios().get(0);
		assertTrue(u.getNombre().equals("testGuardarUsuarioUsuario"));
	}

	@Test
	public void testCargarGrupo() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testCargarGrupo" , false, null);
		app.crearGrupoDir(g.getNombre());
		app.addGrupo(g);
		app.guardarGrupos();
		g=app.cargarGrupo("testCargarGrupo");
		assertTrue(g.getNombre().equals("testCargarGrupo"));
	}

	@Test
	public void testBorrarGrupo() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testBorrarGrupo" , false, null);
		app.getListaGrupos().add(g);
		assertTrue(app.getListaGrupos().contains(g));
		app.borrarGrupo("testBorrarGrupo");
		assertFalse(app.getListaGrupos().contains(g));
	}


	@Test
	public void testGuardarGrupo() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testGuardarGrupo" , false, null);
		app.crearGrupoDir(g.getNombre());
		app.addGrupo(g);
		app.guardarGrupos();
		app = new MailUam();
		assertTrue(app.buscarGrupo("testGuardarGrupo").getNombre().equals("testGuardarGrupo"));
	}

	@Test
	public void testGuardarGrupos() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testGuardarGrupos" , false, null);
		app.crearGrupoDir(g.getNombre());
		app.addGrupo(g);
		app.guardarGrupos();
		app = new MailUam();
		assertTrue(app.buscarGrupo("testGuardarGrupos").getNombre().equals("testGuardarGrupos"));
	}

	@Test
	public void testCargarGrupos() {
		MailUam app = new MailUam();
		Grupo g =new Grupo(0,"testCargarGrupos" , false, null);
		app.crearGrupoDir(g.getNombre());
		app.addGrupo(g);
		app.guardarGrupos();
		app = new MailUam();
		app.cargarGrupos();
		assertTrue(app.buscarGrupo("testCargarGrupos").getNombre().equals("testCargarGrupos"));
	}

	@Test
	public void testCargarUsuarios() {
		MailUam app = new MailUam();
		app.getListaUsuarios().get(0).setNombre("testCargarUsuarios");
		app.guardarUsuarios();
		app = new MailUam();
		app.cargarUsuarios();
		assertTrue(app.getListaUsuarios().get(0).getNombre().equals("testCargarUsuarios"));
	}

	@Test
	public void testBuscarListaGrupos() {
		MailUam app = new MailUam();
		Grupo g=app.getListaGrupos().get(0);
		assertTrue(app.buscarListaGrupos(g.getNombre()));
		
	}


}
