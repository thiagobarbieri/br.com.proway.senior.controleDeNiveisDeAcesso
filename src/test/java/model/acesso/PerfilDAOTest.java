package model.acesso;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 */

public class PerfilDAOTest {

	@Test
	public void verificaSeOcorreACriacaoDeUmPerfilVazioDAO() {

		PerfilDAO perfilDAO = new PerfilDAO();
		PerfilModel perfilEsperado = new PerfilModel(10, "Ponto", null);

		Integer idDoPerfil = 10;
		String nomeDoPerfil = "Ponto";
		
		PerfilModel perfilCriado = perfilDAO.criarPerfilVazio(idDoPerfil, nomeDoPerfil);

		assertEquals(perfilEsperado.getIdDoPerfil(), perfilCriado.getIdDoPerfil());
		assertEquals(perfilEsperado.getNomeDoPerfil(), perfilCriado.getNomeDoPerfil());
	}

	@Test
	public void verificaSeOcorreACriacaoDeDoisPerfisVaziosDAO() {

		PerfilDAO perfilDAO = new PerfilDAO();

		Integer idDoPerfil1 = 10;
		String nomeDoPerfil1 = "Ponto";

		Integer idDoPerfil2 = 20;
		String nomeDoPerfil2 = "F�rias";

		perfilDAO.criarPerfilVazio(idDoPerfil1, nomeDoPerfil1);
		perfilDAO.criarPerfilVazio(idDoPerfil2, nomeDoPerfil2);

		assertEquals(2, perfilDAO.lerListaDePerfisCriados().size());
	}

	
	@Test
	public void verificaSeOPerfilVazioFoiExcluidoDAO() {
		
		PerfilDAO perfilDAO = new PerfilDAO();

		Integer idDoPerfil1 = 10;
		String nomeDoPerfil1 = "Ponto";
		
		perfilDAO.criarPerfilVazio(idDoPerfil1, nomeDoPerfil1);
		perfilDAO.deletarPerfil(idDoPerfil1);
		
		assertEquals(0, perfilDAO.lerListaDePerfisCriados().size());
	}
	
	@Test
	public void verificaSeOPerfilVazioFoiAlteradoDAO() {
		PerfilDAO perfilDAO = new PerfilDAO();
		
		Integer idDoPerfil = 10;
		String nomeDoPerfilAntigo = "Ponto";
		String nomeDoPerfilNovo = "Cadastro";
		
		perfilDAO.criarPerfilVazio(idDoPerfil, nomeDoPerfilAntigo);
		PerfilModel perfilAlterado = perfilDAO.alterarNomePerfil(idDoPerfil, nomeDoPerfilNovo);
		
		assertEquals(nomeDoPerfilNovo, perfilAlterado.getNomeDoPerfil());
		
		
	}
	
}