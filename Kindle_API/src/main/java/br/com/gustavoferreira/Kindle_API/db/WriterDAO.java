package br.com.gustavoferreira.Kindle_API.db;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.gustavoferreira.Kindle_API.entities.Writer;

public class WriterDAO implements InterfaceDAO<Writer> {

	@Override
	public void persist(Writer t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(Writer t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public Writer get(Object pk) {
		return UtilDB.getEntityManager().find(Writer.class, pk);
	}

	@Override
	public List<Writer> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT w FROM Writer w", Writer.class).getResultList();
	}

}
