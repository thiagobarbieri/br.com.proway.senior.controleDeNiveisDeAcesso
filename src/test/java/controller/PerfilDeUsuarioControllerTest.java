package controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.dao.PerfilDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class PerfilDeUsuarioControllerTest {

	@Before
	public void deletarTudoDoPerfilDeUsuario() {
		controller.deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		controller.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilDAO.deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		controller.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilDAO.deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	static PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
	static PerfilDAO perfilDAO = PerfilDAO.getInstance();
	static UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	static Usuario usuario;
	static Permissao permissao;
	static Perfil perfil;

	public static void popularTabelas() {
		perfilDAO.criar(new Perfil("Vendedor"));
		perfil = perfilDAO.consultarPorNome("Vendedor");

		PermissaoDAO.getInstance().criar(new Permissao("Relatório de compras"));
		permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras");

		usuarioDAO.criar(new Usuario("thiago@gmail.com", "admin"));
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");

		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		assertEquals(1, controller.listarPerfisDeUmUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testListarPermissoesDeUmUsuario() {
		perfilDAO.atribuirPermissaoAUmPerfil(perfil, permissao);
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		Set<Permissao> permissoes = controller.listarPermissoesDeUmUsuario(usuario.getIdUsuario());

		List<Permissao> listaPermissoes = new ArrayList<Permissao>();
		for (Permissao permissao : permissoes)
			listaPermissoes.add(permissao);

		assertEquals(1, listaPermissoes.size());
		assertEquals("Relatório de compras", listaPermissoes.get(0).getNomePermissao());
	}

	@Test
	public void testConsultarPorIdDoPerfil() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		assertEquals(1, controller.listar().size());

		assertEquals(1, controller.consultarPorIdDoPerfil(perfil.getIdPerfil()).size());
	}

	@Test
	public void testDeletar() {
		assertEquals(0, controller.listar().size());
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controller.listar().size());

		controller.deletar(ligacao);
		assertEquals(0, controller.listar().size());
	}

	@Test
	public void testAlterar() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controller.listar().size());

		ligacao.setDataExpiracao(LocalDate.now().plusMonths(3));
		controller.alterar(ligacao);
		assertEquals(LocalDate.now().plusMonths(3), controller.consultarPorId(ligacao.getId()).getDataExpiracao());
	}

	@Test
	public void testConsultarPorId() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controller.listar().size());
		assertEquals("Vendedor", controller.consultarPorId(ligacao.getId()).getPerfil().getNomePerfil());
	}

	@Test
	public void testListar() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao1.getUsuario(), ligacao1.getPerfil(), ligacao1.getDataExpiracao());

		PerfilDeUsuario ligacao2 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao2.getUsuario(), ligacao2.getPerfil(), ligacao2.getDataExpiracao());

		PerfilDeUsuario ligacao3 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao3.getUsuario(), ligacao3.getPerfil(), ligacao3.getDataExpiracao());

		assertEquals(3, controller.listar().size());
	}

}
