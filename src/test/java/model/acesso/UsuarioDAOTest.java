package model.acesso;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.PermissaoController;
import model.dao.PerfilDAO;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class UsuarioDAOTest {

	@Before
	public void deletarTudo() {
		UsuarioDAO.getInstance().deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	
	}

	public static void popularTabelas() {
		Perfil perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		
		PermissaoController.getInstance().criarPermissao("Relatório de compras.");
		Permissao permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras.");

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
	}

	@Test
	public void testCriarUsuario() {
		assertEquals(0, UsuarioDAO.getInstance().listar().size());
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		UsuarioDAO.getInstance().criar(usuario);
		assertEquals(1, UsuarioDAO.getInstance().listar().size());
	}
	
	@Test
	public void testConsultarUsuarioPorId() {
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		UsuarioDAO.getInstance().criar(usuario);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuario.getIdUsuario());
		assertEquals("thiago@gmail.com", usuarioEncontrado.getLogin());
	}

	@Test
	public void testAlterarUsuario() {
		Usuario usuario = new Usuario("antes@gmail.com", "senha2");
		UsuarioDAO.getInstance().criar(usuario);
		usuario.setLogin("depois@gmail.com");
		UsuarioDAO.getInstance().alterar(usuario);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuario.getIdUsuario());
		assertEquals("depois@gmail.com", usuarioEncontrado.getLogin());
	}

	@Test
	public void testConsultarUsuarioPorLogin() {
		Usuario usuario = new Usuario("login@gmail.com", "senha3");
		UsuarioDAO.getInstance().criar(usuario);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorLogin("login@gmail.com");
		assertEquals(usuario.getIdUsuario(), usuarioEncontrado.getIdUsuario());
	}

	@Test
	public void testDeletarUsuario() {
		assertEquals(0, UsuarioDAO.getInstance().listar().size());
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		UsuarioDAO.getInstance().criar(usuario);
		assertEquals(1, UsuarioDAO.getInstance().listar().size());
		UsuarioDAO.getInstance().deletar(usuario);
		assertEquals(0, UsuarioDAO.getInstance().listar().size());
	}
	
	
	
}
