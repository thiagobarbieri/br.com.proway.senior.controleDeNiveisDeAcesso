package model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import controller.PermissaoController;
import model.dao.PerfilDAO;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.PermissaoDAO;
import model.entidades.Perfil;
import model.entidades.Permissao;

public class PerfilDAOTest {

	
	@Before
	public void deletarTudo() {
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
		
		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}
	
	static Permissao permissao;
	static Perfil perfil;
	
	public static void popularTabelas() {
		PerfilDAO.getInstance().criar(new Perfil("Vendedor"));
		perfil = PerfilDAO.getInstance().consultarPorNome("Vendedor"); 
		
		PermissaoDAO.getInstance().criar(new Permissao("Relatório de compras"));
		permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras");
	}
	
	@Test
	public void testAtribuirPermissaoAUmPerfil() {
		assertEquals(0, perfil.getPermissoes().size());
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		assertEquals(1, perfil.getPermissoes().size());
	}
	
	@Test
	public void testSalvarPerfil() {
		assertEquals(0, PerfilDAO.getInstance().listar().size());
		PerfilDAO.getInstance().criar(new Perfil("Administrador"));
		assertEquals(1, PerfilDAO.getInstance().listar().size());
	}

	@Test
	public void testAtualizarUmPerfil() {
		Perfil perfilOriginal = new Perfil("Administrador");
		PerfilDAO.getInstance().criar(perfilOriginal);
		assertEquals(1, PerfilDAO.getInstance().listar().size());
		perfilOriginal.setNomePerfil("Administrador Alterado");
		PerfilDAO.getInstance().alterar(perfilOriginal);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfilOriginal.getIdPerfil());
		assertEquals("Administrador Alterado", perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testDeletarUmPerfil() {
		assertEquals(0, PerfilDAO.getInstance().listar().size());
		Perfil perfil = new Perfil("Administrador");
		PerfilDAO.getInstance().criar(perfil);
		assertEquals(1, PerfilDAO.getInstance().listar().size());
		PerfilDAO.getInstance().deletar(perfil);
		assertEquals(0, PerfilDAO.getInstance().listar().size());
	}

	@Test
	public void testBuscarPerfilPorNome() {
		Perfil perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorNome("Vendedor");
		assertEquals("Vendedor", perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testListarTodosOsPerfis() {
		assertEquals(0, PerfilDAO.getInstance().listar().size());
		PerfilDAO.getInstance().criar(new Perfil("Administrador Desenvolvimento"));
		PerfilDAO.getInstance().criar(new Perfil("Administrador Compras"));
		PerfilDAO.getInstance().criar(new Perfil("Administrador RH"));
		assertEquals(3, PerfilDAO.getInstance().listar().size());
	}


}