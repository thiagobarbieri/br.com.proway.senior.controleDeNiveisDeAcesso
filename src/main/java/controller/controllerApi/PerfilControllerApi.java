package controller.controllerApi;


import java.util.ArrayList;
import java.util.List;

import controller.PerfilController;
import model.dao.PerfilDAO;
import model.dto.PerfilDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;

public class PerfilControllerApi {
	
	PerfilController controllerPerfil = new PerfilController();
	
	/** PerfilController
	 * Criacao de um {@link Perfil} no objeto.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Perfil} com o atributo
	 * nomePerfil. O objeto {@link Perfil} e enviado ao {@link PerfilDAO} para ser
	 * persistido no banco de dados.
	 * 
	 * @param String - nomePerfil
	 * 
	 */
	public void criarPerfil(String nomePerfil) {
		Perfil perfil = new Perfil(nomePerfil);
		PerfilDAO.getInstance().criar(perfil);
	}

	/** PerfilController
	 * Alteracao de um {@link Perfil}.
	 * 
	 * Responsavel por alterar um {@link Perfil} pre existente com os atributos
	 * idPerfil e nomePerfil. A busca eh realizada pelo idPerfil por intermedio do
	 * PerfilDAO,o nome do perfil e adicionado ao objeto e alterado no banco de
	 * dados.
	 * 
	 * @param Integer idPerfil
	 * @param String  nomePerfil
	 */
	public void alterarPerfil(Integer idPerfil, String nomePerfil) {
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		perfil.setNomePerfil(nomePerfil);
		PerfilDAO.getInstance().alterar(perfil);
	}

	/** PerfilController
	 * Remocao de um {@link Perfil} pelo id.
   *  
	 * Responsavel por deletar um objeto do tipo {@link Perfil} com os atributos idPerfil.
	 * O objeto {@link Perfil} eh enviado ao {@link PerfilDAO} para ser removido no banco de dados.
	 * 
	 * @param idPerfil - Integer
	 */
	public void deletarPerfil(Integer idPerfil) {
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		PerfilDAO.getInstance().deletar(perfil);
	}

	/** PerfilController
	 * Consulta de {@link Perfil} pelo id.
	 * 
	 * Responsavel por consultar um {@link Perfil} existente com o 
	 * idPerfil. A busca e realizada pelo idPerfil por intermedio do
	 * {@link PerfilDAO}.
	 * 
	 * 
	 * @param Integer 
	 * @throws NullPointerException Caso nao exista o {@link Perfil} no banco de dados.
	 * @return Perfil
	 */
	public PerfilDTO consultarPerfilPorId(Integer idPerfil) {
		try {
			return new PerfilDTO(controllerPerfil.consultarPerfil(idPerfil));
		} catch (NullPointerException e) {
			return null;
		}
	}

	/** PerfilController
	 * Tem a funcao de consultar por nome um objeto do tipo {@link Perfil} com o atributo nomePerfil.
	 * O objeto {@link Perfil} vai ser consultado no banco de dados pelo nome.
	 * 
	 * @param nome - String
	 * @return {@link Perfil} 
	 */
	public PerfilDTO consultarPerfil(String nome) {
		try {
			return new PerfilDTO(controllerPerfil.consultarPerfil(nome));
		} catch (NullPointerException e) {
			return null;
		}

	}

	/** PerfilController
	 * Consulta todos os perfis no banco de dados.
	 * 
	 * @return resultado - ArrayList<Perfil>
	 */
	public ArrayList<PerfilDTO> listarTodosOsPerfis() {
		ArrayList<Perfil> perfisEncontrados = (ArrayList<Perfil>) PerfilDAO.getInstance().listar();
		
		ArrayList<PerfilDTO> listaDto = new ArrayList<PerfilDTO>();
		
		for(Perfil perfis : perfisEncontrados) 
			listaDto.add(new PerfilDTO(perfis));
		
		//listaDto = !perfisEncontrados.isEmpty() ? perfisEncontrados : null;
		return listaDto;

	}

	/** PerfilController
	 * Vai ser feita uma consulta no banco de dados pelo ID do perfil para saber se o {@link Perfil} 
	 * tem permissoes.
	 * 
	 * @param perfil - Perfil
	 * @param permissao - Permissao
	 * @return ArrayList<Permissao>
	 */
	public boolean possuiPermissoes(Perfil perfil, Permissao permissao) {
		List<Permissao> listaDePermissoesDessePerfil = listarPermissoesDeUmPerfil(perfil.getIdPerfil());
		if (listaDePermissoesDessePerfil.contains(permissao)) {
			return true;
		} else {
			return false;
		}
	}
	
	/** PerfilController
	 * Lista todas as {@link Permissao} de um {@link Perfil}.
	 * 
	 * O método irá pesquisar todas as {@link Permissao} que o {@link Perfil} possui fazendo uma busca
	 * pelo idPerfil, o qual será enviado ao {@link PerfilDAO}. O retorno será todas as {@link Permissao}
	 * que um {@link Perfil} possui.
	 * 
	 * @param idPerfil
	 * @return List<Permissao>
	 */
	public List<Permissao> listarPermissoesDeUmPerfil(int idPerfil) {
		return PerfilDAO.getInstance().listarPermissoesDeUmPerfil(idPerfil);
	}
 
	/** PerfilController
	 *  Eh atribuido uma {@link Permissao} a um {@link Perfil} quando eh passado uma {@link Permissao} 
	 *  para aquele {@link Perfil}.
	 * 
	 * @param permissao Permissao
	 * @param perfil Perfil
	 */
	public void atribuirPermissaoAUmPerfil(Permissao permissao, Perfil perfil) {
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
	}

	/**
	 * Deleta todos os registros da tabela {@link Perfil}.
	 */
	public void deletarTodos() {
		PerfilDAO.getInstance().deletarTodos();
	}
	


}