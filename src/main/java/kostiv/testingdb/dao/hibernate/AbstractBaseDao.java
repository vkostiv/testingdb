package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kostiv.testingdb.dao.BaseDao;

public abstract class AbstractBaseDao<E> implements BaseDao<E> {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public abstract List<E> findAll();

	@Override
	public abstract E findById(Long id);

	@Override
	public E save(E t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public E update(E t) {
		E result = entityManager.merge(t);
		return result;
	}

	@Override
	public void delete (E t) {
		entityManager.remove(t);
	}
}
