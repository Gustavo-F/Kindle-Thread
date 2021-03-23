package br.com.gustavoferreira.Kindle_API.db;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.gustavoferreira.Kindle_API.entities.Genre;

public class GenreDAO implements InterfaceDAO<Genre> {

	@Override
	public void persist(Genre t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	@Override
	public void remove(Genre t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public Genre get(Object pk) {
		return UtilDB.getEntityManager().find(Genre.class, pk);
	}

	@Override
	public List<Genre> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
	}

	public static void updateGenre(String newGenre, String oldGenre) {
		String sql = "UPDATE Genre SET genre = '" + newGenre + "' WHERE genre = '" + oldGenre + "'";
		
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.createQuery(sql).executeUpdate();
		em.getTransaction().commit();
	}
}
