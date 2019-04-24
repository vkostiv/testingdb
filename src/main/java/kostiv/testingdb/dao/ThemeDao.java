package kostiv.testingdb.dao;

import java.util.List;

import kostiv.testingdb.data.Theme;

public interface ThemeDao extends BaseDao<Theme> {

	Theme findByCode(String code);

	List<Theme> findByOrder();

}
