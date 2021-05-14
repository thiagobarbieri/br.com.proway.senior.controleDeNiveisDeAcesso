package model.acesso;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import db.DBConnection;
import model.interfaces.ICrud;

/**
 * Classe PermissaoDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de
 * permissoes
 * 
 * @author Sprint 3
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 * @author Sprint 4
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Goncalves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */

public class PermissaoDAO implements ICrud<Permissao> {

	private static PermissaoDAO instance;
	private Session session;

	private PermissaoDAO(Session session) {
		this.session = session;
	}

	public static PermissaoDAO getInstance() {
		if (instance == null) {
			instance = new PermissaoDAO(DBConnection.getSession());
		}
		return instance;
	}

	public void criar(Permissao object) {
		try {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.getStackTrace();
			session.getTransaction().rollback();
		}
	}

	public boolean alterar(Permissao object) {
		try {
			session.beginTransaction();
			session.update(object);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletar(Permissao object) {
		try {
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}

	}

	public Permissao consultarPorId(int id) {
		try {
			session.beginTransaction();
			Permissao u = session.find(Permissao.class, id);
			session.getTransaction().commit();
			return u;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	public List<Permissao> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		criteria.from(Permissao.class);
		Query query = session.createQuery(criteria);
		@SuppressWarnings("unchecked")
		List<Permissao> listaPermissoes = query.getResultList();
		return listaPermissoes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Permissao consultarPorNome(String nome) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		Root<Permissao> root = criteria.from(Permissao.class);

		criteria.select(root);
		Expression nomeEX = (Expression) root.get("nomePermissao");

		criteria.select(root).where(builder.like(nomeEX, nome + "%"));
		Query query = session.createQuery(criteria);
		return (Permissao) query.getSingleResult();
	}
}
