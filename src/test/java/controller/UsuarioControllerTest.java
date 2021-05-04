package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import controller.UsuarioController;
import model.acesso.HashSenha;
import model.acesso.PerfilModel;
import model.acesso.PermissaoModel;
import model.acesso.UsuarioModel;

/**
 * 
 * @author Vitor Peres
 * @author David Willian
 * @author Leonardo Pereira
 * vitor.peres@senior.com.br
 * leonardo.pereira@senior.com.br
 * david.oliveira@senior.com.br
 * 
 */
public class UsuarioControllerTest {
	
	@Test
	public void testeCriarNovoUsuario() {
		UsuarioController userControl = new UsuarioController();
		String senha = "Aa12345678/";
		UsuarioModel um = userControl.criarUsuarioModel(0, "abc@test.com.br", senha);
		assertEquals(um.getHashSenhaDoUsuario(), "ac28bd3afad70aa"
				+ "d355697ac144f1f90786f8de54cd5336aeffec2b6940"
				+ "fe9171d5a9fe776f98c188d0e28c1dfbfa6cae54d0"
				+ "c97473c3d57a21b358aaf5d15ec");
		assertEquals(um.getIdDoUsuario(), 0);
		assertEquals(um.getLoginDoUsuario(), "abc@test.com.br");
	}

	@Test
	public void testeSeEmailValido() {
		UsuarioController userControl = new UsuarioController();
		UsuarioModel user = new UsuarioModel();
		user.setLoginDoUsuario("vitorperes1104@gmail.com");
		assertTrue(userControl.validarEmail(user.getLoginDoUsuario()));
	}

	@Test
	public void testeSeEmailInvalido() {
		UsuarioController userControl = new UsuarioController();
		UsuarioModel user = new UsuarioModel();
		user.setLoginDoUsuario("vitorperes110gmail.com");
		assertFalse(userControl.validarEmail(user.getLoginDoUsuario()));
	}

	@Test
	public void testeSeEmailVazio() {
		UsuarioController userControl = new UsuarioController();
		UsuarioModel user = new UsuarioModel();
		user.setLoginDoUsuario("");
		assertFalse(userControl.validarEmail(user.getLoginDoUsuario()));
	}

	@Test
	public void testeSeSenhaValida() {
		UsuarioController userControl = new UsuarioController();
		assertTrue(userControl.validarSenha("1Vaaaa"));
	}

	@Test
	public void testeSeSenhaInvalida() {
		UsuarioController userControl = new UsuarioController();
		assertFalse(userControl.validarSenha("123124321"));
	}

	@Test
	public void testeSeSenhaMenor() {
		UsuarioController userControl = new UsuarioController();
		assertFalse(userControl.validarSenha("1Pa"));
	}

	@Test
	public void testeSeSenhaMaior() {
		UsuarioController userControl = new UsuarioController();
		assertFalse(userControl.validarSenha("1Pa124126124131364346413523342433244143143131413311"));
	}

	@Test
	public void testeAlteraSenhaComSenhaAnteriorNula() {
		UsuarioController userControl = new UsuarioController();
		UsuarioModel user = new UsuarioModel();
		user.setIdDoUsuario(0);
		user.setHashSenhaDoUsuario("");
		userControl.daoUsuario.create(user);
		userControl.alteraSenha(user.getIdDoUsuario(), "1Pabcde");
		assertEquals("6d84bd7f79f63cc7f2f519547d5837d3920bbcf42db70"
				+ "b4cd5bb0f22775b4d897ce9faefef9ad37aeee7e1320c0f0"
				+ "fa5dcb0232eac6619213fc78d5e44808c48", user.getHashSenhaDoUsuario());
	}

	@Test
	public void testeAlteraLogin() {
		UsuarioController userControl = new UsuarioController();
		UsuarioModel user = new UsuarioModel();
		user.setLoginDoUsuario("vitorperes1104@gmail.com");
		user.setIdDoUsuario(0);
		userControl.daoUsuario.user.add(user);
		userControl.alteraLogin(0, "dwillian676@gmail.com");
		assertEquals("dwillian676@gmail.com", userControl.daoUsuario.get(0).getLoginDoUsuario());
	}

	@Test
	public void testeGerarCodigo() {
		UsuarioController userControl = new UsuarioController();
		assertTrue(userControl.gerarCodigo());
	}

	@Test
	public void testeAlteraPerfil() {

		UsuarioController userControl = new UsuarioController();
		UsuarioModel user = new UsuarioModel();

		PermissaoModel permissaoNormal = new PermissaoModel(1, "Permissao altera��o de perfil");
		ArrayList<PermissaoModel> listaPermissaoNormal = new ArrayList<PermissaoModel>();
		listaPermissaoNormal.add(permissaoNormal);
		PermissaoModel permissaoTest = new PermissaoModel(2, "Permissao alterada Test");
		ArrayList<PermissaoModel> listaPermissaoTest = new ArrayList<PermissaoModel>();
		listaPermissaoTest.add(permissaoTest);

		PerfilModel perfilNormal = new PerfilModel(1, "Teste", listaPermissaoNormal);
		PerfilModel perfilTest = new PerfilModel(2, "Teste alterado", listaPermissaoTest);

		ArrayList<PerfilModel> listaPerfilNormal = new ArrayList<PerfilModel>();
		listaPerfilNormal.add(perfilTest);
		ArrayList<PerfilModel> listaPerfilTest = new ArrayList<PerfilModel>();
		listaPerfilTest.add(perfilTest);
		
		
		user.setListaDePerfisDoUsuario(listaPerfilNormal);
		userControl.daoUsuario.user.add(user);
		userControl.alteraPerfil(0, listaPerfilTest);

		assertEquals(perfilTest, userControl.daoUsuario.user.get(0).getListaDePerfisDoUsuario().get(0));
	}


	@Test
	public void testeRemoveUsuario() {
		UsuarioController userControl = new UsuarioController();
		PermissaoModel permissao = new PermissaoModel(1, "Permissao altera��o de perfil");
		ArrayList<PermissaoModel> listaPermissao = new ArrayList<PermissaoModel>();
		listaPermissao.add(permissao);

		
		PerfilModel perfilTest = new PerfilModel(1, "Perfil teste", listaPermissao);
		
		ArrayList<PerfilModel> listaPerfil = new ArrayList<PerfilModel>();
		listaPerfil.add(perfilTest);

		UsuarioModel userUm = new UsuarioModel(0, "vcperes@furb.br", "Va123456", listaPerfil);
		UsuarioModel userDois = new UsuarioModel(1, "vitorperes1104@gmail.com", "Ca123456", listaPerfil);

		userControl.daoUsuario.user.add(userUm);
		userControl.daoUsuario.user.add(userDois);
		userControl.daoUsuario.remove(0);
	
		assertEquals(1, userControl.daoUsuario.user.get(0).getIdDoUsuario());
	}

}