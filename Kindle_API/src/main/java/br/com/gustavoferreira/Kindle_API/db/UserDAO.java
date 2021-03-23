package br.com.gustavoferreira.Kindle_API.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.gustavoferreira.Kindle_API.entities.User;

public class UserDAO implements InterfaceDAO<User> {

	@Override
	public void persist(User t) {
		System.out.println("Persistindo usuário...");
		
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		}catch(EntityExistsException e) {
			em.getTransaction().rollback();
			User original = get(t.getUsername());
			
			em.getTransaction().begin();
			original.setBooks(t.getBooks());
			original.setPassword(t.getPassword());
			System.out.println("Senha: " + original.getPassword());
			em.persist(original);
			em.getTransaction().commit();
		}
	}	

	@Override
	public void remove(User t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public User get(Object pk) {
		return UtilDB.getEntityManager().find(User.class, pk);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
