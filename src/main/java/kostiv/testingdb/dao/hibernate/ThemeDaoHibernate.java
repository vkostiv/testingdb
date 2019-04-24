package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import kostiv.testingdb.dao.ThemeDao;
import kostiv.testingdb.data.Theme;

@Repository("themeDao")
public class ThemeDaoHibernate extends AbstractBaseDao<Theme> implements ThemeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Theme> findAll() {
		// TypedQuery<Theme> query = entityManager.createQuery("select t from Theme t", Theme.class);
		Query query = entityManager.createNativeQuery("select * from themes order by theme_id", Theme.class);
		return (List<Theme>)query.getResultList();
	}

	@Override
	public Theme findById(Long id) {
		return entityManager.find(Theme.class, id);
	}

	@Override
	public Theme findByCode(String code) {
		try {
			TypedQuery<Theme> query = entityManager.createQuery("select * from themes where code = :code", Theme.class);
			query.setParameter("code", code);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Theme> findByOrder() {
		try {
			TypedQuery<Theme> query = entityManager.createQuery("select t from Theme t order by orderId", Theme.class);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
}
