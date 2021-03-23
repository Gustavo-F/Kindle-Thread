package br.com.gustavoferreira.Kindle_API.db;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.gustavoferreira.Kindle_API.entities.Publisher;

public class PublisherDAO implements InterfaceDAO<Publisher> {

	@Override
	public void persist(Publisher t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			Publisher original = get(t.getCnpj());

			em.getTransaction().begin();

			original.setName(t.getName());
			;
			original.setEmail(t.getEmail());
			original.setPhone(t.getPhone());

			em.persist(original);
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(Publisher t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public Publisher get(Object pk) {
		return UtilDB.getEntityManager().find(Publisher.class, pk);
	}

	@Override
	public List<Publisher> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
	}

}
