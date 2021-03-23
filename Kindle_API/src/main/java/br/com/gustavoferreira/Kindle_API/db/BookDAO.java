package br.com.gustavoferreira.Kindle_API.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.gustavoferreira.Kindle_API.entities.Book;
import br.com.gustavoferreira.Kindle_API.entities.Genre;
import br.com.gustavoferreira.Kindle_API.entities.Writer;

public class BookDAO implements InterfaceDAO<Book> {

	@Override
	public void persist(Book t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();

			Book original = get(t.getId());

			em.getTransaction().begin();

			original.setTitle(t.getTitle());
			original.setPages(t.getPages());
			original.setPublisher(t.getPublisher());

			original.getWriters().clear();
			for (Writer w : t.getWriters()) {
				original.getWriters().add(w);
			}

			original.getGenres().clear();
			for (Genre g : t.getGenres()) {
				original.getGenres().add(g);
			}

			em.persist(original);
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(Book t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public Book get(Object pk) {
		return UtilDB.getEntityManager().find(Book.class, pk);
	}

	@Override
	public List<Book> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT b FROM Book b", Book.class).getResultList();
	}

}
