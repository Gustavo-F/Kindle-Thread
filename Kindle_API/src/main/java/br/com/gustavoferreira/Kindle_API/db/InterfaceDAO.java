package br.com.gustavoferreira.Kindle_API.db;

import java.util.List;

public interface InterfaceDAO<T> {
	
	public void persist(T t);
	
	public void remove(T t);
	
	public T get(Object pk);
	
	public List<T> getAll();
}
