package kostiv.testingdb.dao;

import java.util.List;

public interface BaseDao<T> {

	public List<T> findAll();

	public T findById(Long id);

	public T save(T t);

	public T update(T t);
	
	public void delete (T t);
}